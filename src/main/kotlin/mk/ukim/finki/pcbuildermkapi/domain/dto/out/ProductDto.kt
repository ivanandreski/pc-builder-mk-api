package mk.ukim.finki.pcbuildermkapi.domain.dto.out

import mk.ukim.finki.pcbuildermkapi.domain.model.ProductCompatibilityAttributes

data class ProductDto(
    val name: String,
    val slug: String,
    val price: Double,
    val imageUrl: String,
    val categorySlug: String,
    val storeName: String,
    val storeImageUrl: String,
    val compatibilityTags: ProductCompatibilityAttributes?,
    val storeLocations: List<StoreLocationDto>,
    val isAvailable: Boolean = storeLocations.isNotEmpty()
)