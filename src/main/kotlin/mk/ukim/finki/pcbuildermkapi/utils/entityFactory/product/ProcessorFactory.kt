package mk.ukim.finki.pcbuildermkapi.utils.entityFactory.product

import mk.ukim.finki.pcbuildermkapi.domain.dto.`in`.ScrapedProduct
import mk.ukim.finki.pcbuildermkapi.domain.enumeation.*
import mk.ukim.finki.pcbuildermkapi.domain.model.Product
import mk.ukim.finki.pcbuildermkapi.domain.model.ProductCompatibilityAttributes

class ProcessorFactory(scrapedProduct: ScrapedProduct) : AbstractProductFactory(scrapedProduct) {
    private var processorCodeName: ProcessorCodeName? = null
    private var socket: Socket? = null
    private var processorManufacturer: ProcessorManufacturer? = null

    override fun prepareAnhochProduct(): Product {
        processorManufacturer = when (scrapedProduct.brand.uppercase()) {
            ProcessorManufacturer.AMD.toString() -> ProcessorManufacturer.AMD
            ProcessorManufacturer.INTEL.toString() -> ProcessorManufacturer.INTEL
            else -> null
        }

        if (processorManufacturer == ProcessorManufacturer.INTEL) {
            for(processorCodeNameEnum in ProcessorCodeName.values()) {
                val parsedName = processorCodeNameEnum.toString()
                    .replace("_", " ")
                    .uppercase()
                if (scrapedProduct.name.uppercase().contains(parsedName)) {
                    processorCodeName = processorCodeNameEnum
                    break
                }
            }
        }

        for (socketEnum in Socket.values()) {
            val parsedSocketName = socketEnum.toString()
                .uppercase()
                .replace("_", " ")
            if (scrapedProduct.name.uppercase().contains(parsedSocketName)) {
                socket = socketEnum
                break
            }
        }

        return setCompatibilityAttributes()
    }

    override fun prepareSetecProduct(): Product {
        processorManufacturer = when (scrapedProduct.brand.uppercase()) {
            ProcessorManufacturer.AMD.toString() -> ProcessorManufacturer.AMD
            ProcessorManufacturer.INTEL.toString() -> ProcessorManufacturer.INTEL
            else -> null
        }

        if (processorManufacturer == ProcessorManufacturer.INTEL) {
            for(processorCodeNameEnum in ProcessorCodeName.values()) {
                val parsedName = processorCodeNameEnum.toString()
                    .replace("_", " ")
                    .uppercase()
                if (scrapedProduct.description.uppercase().contains(parsedName)) {
                    processorCodeName = processorCodeNameEnum
                    break
                }
            }
        }

        for (socketEnum in Socket.values()) {
            val parsedSocketName = socketEnum.toString()
                .uppercase()
                .replace("_", "")
            if (scrapedProduct.description.uppercase().contains(parsedSocketName) ||
                scrapedProduct.description.uppercase().contains("Socket " + socketEnum.shortVal)
            ) {
                socket = socketEnum
                break
            }
        }

        return setCompatibilityAttributes()
    }

    override fun prepareDDStoreProduct(): Product {
        processorManufacturer = when (scrapedProduct.brand.uppercase()) {
            ProcessorManufacturer.AMD.toString() -> ProcessorManufacturer.AMD
            ProcessorManufacturer.INTEL.toString() -> ProcessorManufacturer.INTEL
            else -> null
        }

        if (processorManufacturer == ProcessorManufacturer.INTEL) {
            for(processorCodeNameEnum in ProcessorCodeName.values()) {
                val parsedName = processorCodeNameEnum.toString()
                    .replace("_", " ")
                    .uppercase()
                if (scrapedProduct.description.uppercase().contains(parsedName)) {
                    processorCodeName = processorCodeNameEnum
                    break
                }
            }
        }

        for (socketEnum in Socket.values()) {
            val parsedSocketName = socketEnum.toString()
                .uppercase()
                .replace("_", "")
            if (scrapedProduct.description.uppercase().contains(parsedSocketName) ||
                scrapedProduct.description.uppercase().contains("Socket " + socketEnum.shortVal)
            ) {
                socket = socketEnum
                break
            }
        }

        return setCompatibilityAttributes()
    }

    private fun setCompatibilityAttributes(): Product {
        product.compatibilityAttributes = ProductCompatibilityAttributes(
            product,
            processorCodeName = processorCodeName,
            socket = socket,
            processorManufacturer = processorManufacturer
        )

        return product
    }
}