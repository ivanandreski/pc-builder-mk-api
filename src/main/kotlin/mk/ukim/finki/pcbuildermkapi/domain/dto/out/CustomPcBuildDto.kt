package mk.ukim.finki.pcbuildermkapi.domain.dto.out

import mk.ukim.finki.pcbuildermkapi.domain.model.Product

data class CustomPcBuildDto(
    // TODO: replace product with customPcBuildProductDto
    val case: Product? = null,
    val graphicsCard: Product? = null,
    val motherboard: Product? = null,
    val powerSupply: Product? = null,
    val processor: Product? = null,
    val storageDevices: List<Product> = listOf(),
    val ramSticks: List<Product> = listOf(),
)
