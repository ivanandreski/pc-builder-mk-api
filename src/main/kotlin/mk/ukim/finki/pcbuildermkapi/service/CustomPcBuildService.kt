package mk.ukim.finki.pcbuildermkapi.service

import mk.ukim.finki.pcbuildermkapi.domain.dto.out.CustomPcBuildDto
import mk.ukim.finki.pcbuildermkapi.domain.model.CustomPcBuild
import mk.ukim.finki.pcbuildermkapi.domain.model.security.User

interface CustomPcBuildService {
    fun addProductToCustomPcBuild(productSlug: String,user: User): CustomPcBuildDto
    fun removeProductFromCustomPcBuild(productSlug: String, user: User): CustomPcBuildDto
    fun createCustomPcBuildDto(customPcBuild: CustomPcBuild): CustomPcBuildDto
}