package mk.ukim.finki.pcbuildermkapi.domain.dto.out

import mk.ukim.finki.pcbuildermkapi.domain.model.Product

data class CustomPcBuildDto(
    val case: CustomPcBuildProductDto? = null,
    val graphicsCard: CustomPcBuildProductDto? = null,
    val motherboard: CustomPcBuildProductDto? = null,
    val powerSupply: CustomPcBuildProductDto? = null,
    val processor: CustomPcBuildProductDto? = null,
    val storageDevices: List<CustomPcBuildProductDto?> = listOf(),
    val ramSticks: List<CustomPcBuildProductDto?> = listOf(),
)
