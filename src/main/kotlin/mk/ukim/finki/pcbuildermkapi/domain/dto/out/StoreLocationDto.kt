package mk.ukim.finki.pcbuildermkapi.domain.dto.out

data class StoreLocationDto(
    val slug: String,
    val name: String,
    val longitude: Double?,
    val latitude: Double?,
)