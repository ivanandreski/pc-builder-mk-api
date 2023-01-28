package mk.ukim.finki.pcbuildermkapi.service.implementation

import mk.ukim.finki.pcbuildermkapi.domain.dto.ScrapedProduct
import mk.ukim.finki.pcbuildermkapi.domain.model.*
import mk.ukim.finki.pcbuildermkapi.repository.*
import mk.ukim.finki.pcbuildermkapi.service.ImportProductService
import mk.ukim.finki.pcbuildermkapi.service.ParseImportFileService
import mk.ukim.finki.pcbuildermkapi.utils.entityFactory.product.*
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class ImportProductServiceImplementation(
    private val parseImportFileService: ParseImportFileService,
    private val categoryRepository: CategoryRepository,
    private val storeRepository: StoreRepository,
    private val storeLocationRepository: StoreLocationRepository,
    private val productRepository: ProductRepository,
    private val productInStoreLocationRepository: ProductInStoreLocationRepository
) : ImportProductService {
    override fun importProductList(files: Array<MultipartFile>) {
        for (file in files) {
            val scrapedProductList: Array<ScrapedProduct> = parseImportFileService.parseJsonFile(file)
            var categoryName: String
            if (scrapedProductList.isNotEmpty())
                categoryName = scrapedProductList[0].productType
            else
                continue
            var ddStoreExists = storeLocationRepository.existsByName("DDStore")
            for (scrapedProduct in scrapedProductList) {
                val category = categoryRepository.findByName(categoryName)
                    .orElseGet { categoryRepository.save(Category(categoryName)) }

                val storeName = scrapedProduct.storeName
                val store = storeRepository.findByName(storeName)
                    .orElseGet { storeRepository.save(Store(storeName)) }

                if (storeName == "DDStore" && !ddStoreExists) {
                    storeLocationRepository.save(StoreLocation("DDStore", store))
                    ddStoreExists = true
                }

                val storeLocations = HashSet<StoreLocation>()
                if (scrapedProduct.availabilityArray.isNotEmpty()) {
                    for (storeLocationName in scrapedProduct.availabilityArray.split(";")) {
                        if (storeLocationName.isNotEmpty()) {
                            if (!storeLocationRepository.existsByName(storeLocationName)) {
                                storeLocations.add(StoreLocation(storeLocationName, store))
                            } else {
                                storeLocations.add(storeLocationRepository.findByName(storeLocationName).get())
                            }
                        }
                    }
                }
                if (storeName == "DDStore" && scrapedProduct.isAvailable) {
                    if (!storeLocationRepository.existsByName("DDStore")) {
                        storeLocations.add(StoreLocation("DDStore", store))
                    } else {
                        storeLocations.add(storeLocationRepository.findByName("DDStore").get())
                    }
                }
                if (storeLocations.isNotEmpty())
                    storeLocationRepository.saveAll(storeLocations)

                var product: Product?
                if (productRepository.existsByOriginalId(scrapedProduct.originalId)) {
                    continue // da ne dava error pri  import temp fix
                    product = productRepository.findByOriginalId(scrapedProduct.originalId).get()
                    productInStoreLocationRepository.deleteByProduct(product)
                } else {
                    product = prepareProduct(scrapedProduct) ?: continue
                    product.category = category
                    product = productRepository.save(product)
                }

                val productInStoreLocations = ArrayList<ProductInStoreLocation>()
                for (storeLocation in storeLocations) {
                    productInStoreLocations.add(ProductInStoreLocation(storeLocation, product))
                }
                productInStoreLocationRepository.saveAll(productInStoreLocations)
            }
        }
    }

    override fun prepareProduct(scrapedProduct: ScrapedProduct): Product? {
        val productFactory: AbstractProductFactory = when (scrapedProduct.productType) {
            "CPU" -> ProcessorFactory(scrapedProduct)
            "CASE" -> CaseFactory(scrapedProduct)
            "GPU" -> GraphicsCardFactory(scrapedProduct)
            "STORAGE" -> StorageFactory(scrapedProduct)
            "HDD" -> StorageFactory(scrapedProduct)
            "SSD" -> StorageFactory(scrapedProduct)
            "RAM" -> RamFactory(scrapedProduct)
            "MB" -> MotherboardFactory(scrapedProduct)
            "PSU" -> PowerSupplyFactory(scrapedProduct)
            else -> return null
        }

        return productFactory.prepareProduct()
    }
}