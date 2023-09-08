package mk.ukim.finki.pcbuildermkapi.service.implementation

import mk.ukim.finki.pcbuildermkapi.domain.dto.out.ProductDto
import mk.ukim.finki.pcbuildermkapi.domain.dto.`in`.ProductRequest
import mk.ukim.finki.pcbuildermkapi.domain.dto.out.CustomPcBuildProductDto
import mk.ukim.finki.pcbuildermkapi.domain.dto.out.StoreLocationDto
import mk.ukim.finki.pcbuildermkapi.domain.model.Product
import mk.ukim.finki.pcbuildermkapi.repository.CategoryRepository
import mk.ukim.finki.pcbuildermkapi.repository.ProductRepository
import mk.ukim.finki.pcbuildermkapi.repository.StoreLocationRepository
import mk.ukim.finki.pcbuildermkapi.repository.StoreRepository
import mk.ukim.finki.pcbuildermkapi.service.ProductService
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service

@Service
class ProductServiceImplementation(
    private val categoryRepository: CategoryRepository,
    private val productRepository: ProductRepository,
    private val storeLocationRepository: StoreLocationRepository,
    private val storeRepository: StoreRepository
) : ProductService {
    override fun getAll(productRequest: ProductRequest): Any {
        val category = productRequest.category?.let {
            categoryRepository.findBySlug(it).orElse(null)
        }
        val store = productRequest.store?.let {
            storeRepository.findBySlug(productRequest.store).orElse(null)
        }
        val page = productRepository.findAll(
            productRequest.getPageable(),
            productRequest.search?.uppercase(),
            category?.id,
            store?.id,
            productRequest.isAvailable,
            productRequest.manufacturer?.uppercase(),
            productRequest.startPrice,
            productRequest.endPrice
        ).map {
            createProductDto(it)
        }

        return object {
            val content = page
            val minPrice = productRepository.getMinProductPrice() ?: 0
            val maxPrice = productRepository.getMaxProductPrice() ?: 0
        }
    }

    override fun createProductDto(product: Product): ProductDto {
        return ProductDto(
            name = product.displayName,
            slug = product.slug,
            description = product.description,
            price = product.priceMkd,
            originalUrl = product.originalUrl,
            imageUrl = product.imageUrl,
            categorySlug = product.category?.slug ?: "Unknown",
            storeName = product.store.displayName,
            storeImageUrl = product.store.imageUrl ?: "",
            isAvailable = product.available,
            compatibilityTags = product.compatibilityAttributes,
            storeLocations = product.productsInStoreLocation.map{
                StoreLocationDto(
                    slug = it.storeLocation.slug,
                    name = it.storeLocation.name,
                    longitude = it.storeLocation.longitude,
                    latitude = it.storeLocation.latitude,
                )
            }.toList()
        )
    }

    override fun createCustomPcBuildProductDto(product: Product?): CustomPcBuildProductDto? {
        return if (product == null)
            return null
        else {
            CustomPcBuildProductDto(
                name = product.displayName,
                slug = product.slug,
                price = product.priceMkd,
                imageUrl = product.imageUrl,
                categorySlug = product.category?.slug ?: "Unknown",
                storeName = product.store.displayName,
                storeImageUrl = product.store.imageUrl ?: "",
                isAvailable = product.available,
                compatibilityTags = product.compatibilityAttributes,
                storeLocations = product.productsInStoreLocation.map{
                    StoreLocationDto(
                        slug = it.storeLocation.slug,
                        name = it.storeLocation.name,
                        longitude = it.storeLocation.longitude,
                        latitude = it.storeLocation.latitude,
                    )
                }.toList()
            )
        }
    }
}