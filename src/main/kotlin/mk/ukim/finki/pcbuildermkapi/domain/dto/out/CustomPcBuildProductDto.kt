package mk.ukim.finki.pcbuildermkapi.domain.dto.out

data class CustomPcBuildProductDto(
        val name: String,
        val slug: String,
        val price: Double,
        val imageUrl: String,
        val categorySlug: String,
        val isAvailable: Boolean
)
