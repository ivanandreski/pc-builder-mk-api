package mk.ukim.finki.pcbuildermkapi.domain.dto.`in`

import com.google.gson.annotations.SerializedName

data class ScrapedProduct(
    val name: String,
    @SerializedName("price_mkd") val priceMkd: String,
    @SerializedName("original_id") val originalId: String,
    @SerializedName("image_url") val imageUrl: String,
    val description: String,
    val brand: String,
    @SerializedName("is_available") val isAvailable: Boolean,
    @SerializedName("availability_array") val availabilityArray: String,
    @SerializedName("product_url") val productUrl: String,
    @SerializedName("store_name") val storeName: String,
    @SerializedName("product_type") val productType: String,
)
