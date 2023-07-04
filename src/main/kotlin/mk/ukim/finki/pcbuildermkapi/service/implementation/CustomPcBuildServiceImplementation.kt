package mk.ukim.finki.pcbuildermkapi.service.implementation

import mk.ukim.finki.pcbuildermkapi.domain.dto.out.CustomPcBuildDto
import mk.ukim.finki.pcbuildermkapi.domain.model.Category
import mk.ukim.finki.pcbuildermkapi.domain.model.CustomPcBuild
import mk.ukim.finki.pcbuildermkapi.domain.model.Product
import mk.ukim.finki.pcbuildermkapi.domain.model.security.User
import mk.ukim.finki.pcbuildermkapi.repository.CategoryRepository
import mk.ukim.finki.pcbuildermkapi.repository.CustomPcBuildRepository
import mk.ukim.finki.pcbuildermkapi.repository.ProductRepository
import mk.ukim.finki.pcbuildermkapi.repository.UserRepository
import mk.ukim.finki.pcbuildermkapi.service.CustomPcBuildService
import mk.ukim.finki.pcbuildermkapi.service.ProductService
import org.springframework.stereotype.Service

@Service
class CustomPcBuildServiceImplementation(
    private val userRepository: UserRepository,
    private val categoryRepository: CategoryRepository,
    private val productRepository: ProductRepository,
    private val customPcBuildRepository: CustomPcBuildRepository,
    private val productService: ProductService
) : CustomPcBuildService {
    override fun addProductToCustomPcBuild(productSlug: String, user: User): CustomPcBuildDto {
        val (product, category, customPcBuild) = getProductCategoryAndCustomPcBuild(productSlug, user)

        when (category.name) {
            "CASE" -> customPcBuild.caseId = product.id
            "GPU" -> customPcBuild.graphicsCardId = product.id
            "MB" -> customPcBuild.motherboardId = product.id
            "PSU" -> customPcBuild.powerSupplyId = product.id
            "CPU" -> customPcBuild.processorId = product.id
            "RAM" -> customPcBuild.ramIds = customPcBuild.ramIds + "," + product.id
            "STORAGE" -> customPcBuild.storageIds = customPcBuild.storageIds + "," + product.id
        }

        return createCustomPcBuildDto(customPcBuildRepository.save(customPcBuild))
    }

    override fun removeProductFromCustomPcBuild(productSlug: String, user: User): CustomPcBuildDto {
        val (product, category, customPcBuild) = getProductCategoryAndCustomPcBuild(productSlug, user)

        var flag = false
        var newRamIds = ""
        val ramIdsSplit = customPcBuild.ramIds.split(",").stream().skip(1).toList()
        for (ramId in ramIdsSplit) {
            if (!flag && ramId.toLong() == product.id) {
                flag = true
                continue
            }

            if (ramId.toLong() != product.id || (ramId.toLong() == product.id && flag)) {
                newRamIds += ",$ramId"
            }
        }
        flag = false
        var newStorageIds = ""
        val storageIdsSplit = customPcBuild.storageIds.split(",").stream().skip(1).toList()
        for (storageId in storageIdsSplit) {
            if (!flag && storageId.toLong() == product.id) {
                flag = true
                continue
            }

            if (storageId.toLong() != product.id || (storageId.toLong() == product.id && flag)) {
                newStorageIds += "," + product.id
            }
        }

        when (category.name) {
            "CASE" -> customPcBuild.caseId = null
            "GPU" -> customPcBuild.graphicsCardId = null
            "MB" -> customPcBuild.motherboardId = null
            "PSU" -> customPcBuild.powerSupplyId = null
            "CPU" -> customPcBuild.processorId = null
            "RAM" -> customPcBuild.ramIds = newRamIds
            "STORAGE" -> customPcBuild.storageIds = newStorageIds
        }

        return createCustomPcBuildDto(customPcBuildRepository.save(customPcBuild))
    }

    override fun createCustomPcBuildDto(customPcBuild: CustomPcBuild): CustomPcBuildDto {
        val processor = if (customPcBuild.processorId != null)
            productRepository.findById(customPcBuild.processorId!!)
                .orElse(null)
        else null

        val case = if (customPcBuild.caseId != null)
            productRepository.findById(customPcBuild.caseId!!)
                .orElse(null)
        else null

        val motherboard = if (customPcBuild.motherboardId != null)
            productRepository.findById(customPcBuild.motherboardId!!)
                .orElse(null)
        else null

        val graphicsCard = if (customPcBuild.graphicsCardId != null)
            productRepository.findById(customPcBuild.graphicsCardId!!)
                .orElse(null)
        else null

        val powerSupply = if (customPcBuild.powerSupplyId != null)
            productRepository.findById(customPcBuild.powerSupplyId!!)
                .orElse(null)
        else null

        val ramSticks = customPcBuild.ramIds
            .split(",")
            .stream()
            .skip(1)
            .map {
                productRepository.findById(it.toLong()).orElse(null)
            }.filter {
                it != null
            }
            .toList()

        val storageDevices = customPcBuild.storageIds
            .split(",")
            .stream()
            .skip(1)
            .map {
                productRepository.findById(it.toLong()).orElse(null)
            }.filter {
                it != null
            }
            .toList()

        return CustomPcBuildDto(
            modifiedAt = customPcBuild.modifiedAt,
            processor = productService.createCustomPcBuildProductDto(processor),
            case = productService.createCustomPcBuildProductDto(case),
            motherboard = productService.createCustomPcBuildProductDto(motherboard),
            graphicsCard = productService.createCustomPcBuildProductDto(graphicsCard),
            powerSupply = productService.createCustomPcBuildProductDto(powerSupply),
            storageDevices = storageDevices.map {
                productService.createCustomPcBuildProductDto(it)
            }.toList(),
            ramSticks = ramSticks.map {
                productService.createCustomPcBuildProductDto(it)
            }.toList()
        )
    }

    private fun getProductCategoryAndCustomPcBuild(
        productSlug: String,
        user: User
    ): Triple<Product, Category, CustomPcBuild> {
        val product = productRepository.findBySlug(productSlug)
            .orElseThrow { Exception("Product with slug $productSlug not found") }
        val customPcBuild = customPcBuildRepository.findByUser(user)
            .orElseThrow { Exception("Custom pc build for user was not found") }

        return Triple(product, product.category!!, customPcBuild)
    }
}