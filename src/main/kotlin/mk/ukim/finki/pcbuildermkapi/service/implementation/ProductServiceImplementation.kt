package mk.ukim.finki.pcbuildermkapi.service.implementation

import mk.ukim.finki.pcbuildermkapi.domain.dto.out.ProductDto
import mk.ukim.finki.pcbuildermkapi.domain.dto.`in`.ProductRequest
import mk.ukim.finki.pcbuildermkapi.domain.dto.out.CustomPcBuildProductDto
import mk.ukim.finki.pcbuildermkapi.domain.model.Product
import mk.ukim.finki.pcbuildermkapi.repository.CategoryRepository
import mk.ukim.finki.pcbuildermkapi.repository.ProductRepository
import mk.ukim.finki.pcbuildermkapi.service.ProductService
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service

@Service
class ProductServiceImplementation(
    private val categoryRepository: CategoryRepository,
    private val productRepository: ProductRepository
) : ProductService {
    override fun getAll(productRequest: ProductRequest): Page<ProductDto> {
        val category = productRequest.category?.let {
            categoryRepository.findBySlug(it).orElse(null)
        }

        // todo with optional params and native queries
        return (if (category == null)
            productRepository.findAll(productRequest.getPageable())
        else
            productRepository.findByCategory(category, productRequest.getPageable()))
            .map {
                createProductDto(it)
            }
    }

    override fun createProductDto(product: Product): ProductDto {
        return ProductDto(
            name = product.displayName,
            slug = product.slug,
            price = product.priceMkd,
            imageUrl = product.imageUrl,
            categorySlug = product.category?.slug ?: "Unknown",
            storeName = product.store.displayName,
            storeImageUrl = product.store.imageUrl ?: "",
            storeLocationSlugs = product.productsInStoreLocation.associate {
                it.storeLocation.slug to it.storeLocation.name
            }
        )
    }

    override fun createCustomPcBuildProductDto(product: Product?): CustomPcBuildProductDto? {
        return if(product == null)
            return null
        else {
            CustomPcBuildProductDto(
                name = product.displayName,
                slug = product.slug,
                price = product.priceMkd,
                imageUrl = product.imageUrl,
                categorySlug = product.category!!.slug
            )
        }
    }
}