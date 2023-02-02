package mk.ukim.finki.pcbuildermkapi.utils.entityFactory.product

import mk.ukim.finki.pcbuildermkapi.domain.dto.`in`.ScrapedProduct
import mk.ukim.finki.pcbuildermkapi.domain.enumeation.*
import mk.ukim.finki.pcbuildermkapi.domain.model.Product
import mk.ukim.finki.pcbuildermkapi.domain.model.ProductCompatibilityAttributes

class MotherboardFactory(scrapedProduct: ScrapedProduct) : AbstractProductFactory(scrapedProduct) {
    private var chipset: Chipset? = null
    private var formFactor: FormFactor? = null
    private var socket: Socket? = null
    private var ramType: RamType? = null
    private var processorManufacturer: ProcessorManufacturer? = null
    private var numOfRamSlots: Int? = null
    private var numOfSataPorts: Int? = null
    private var numOfM2Slots: Int? = null

    override fun prepareAnhochProduct(): Product {
        if (scrapedProduct.description.uppercase().contains(ProcessorManufacturer.INTEL.toString())) {
            processorManufacturer = ProcessorManufacturer.INTEL
        } else if (scrapedProduct.description.uppercase().contains(ProcessorManufacturer.AMD.toString())) {
            processorManufacturer = ProcessorManufacturer.AMD
        }

        for(ramTypeEnum in RamType.values()) {
            if(scrapedProduct.name.uppercase().contains(ramTypeEnum.toString())) {
                ramType = ramTypeEnum
                break
            }
        }

        for (socketEnum in Socket.values()) {
            val parsedSocketName = socketEnum.toString()
                .uppercase()
                .replace("_", "")
            if (scrapedProduct.name.uppercase().contains(parsedSocketName)) {
                socket = socketEnum
                break
            }
        }

        for (chipsetEnum in Chipset.values()) {
            if (scrapedProduct.description.uppercase().contains(chipsetEnum.toString())) {
                chipset = chipsetEnum
            }
        }

        return setCompatibilityAttributes()
    }

    override fun prepareSetecProduct(): Product {
        if (scrapedProduct.description.uppercase().contains(ProcessorManufacturer.INTEL.toString())) {
            processorManufacturer = ProcessorManufacturer.INTEL
        } else if (scrapedProduct.description.uppercase().contains(ProcessorManufacturer.AMD.toString())) {
            processorManufacturer = ProcessorManufacturer.AMD
        }

        for(ramTypeEnum in RamType.values()) {
            if(scrapedProduct.description.uppercase().contains(ramTypeEnum.toString())) {
                ramType = ramTypeEnum
                break
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

        for (chipsetEnum in Chipset.values()) {
            if (scrapedProduct.description.uppercase().contains(chipsetEnum.toString())) {
                chipset = chipsetEnum
            }
        }

        return setCompatibilityAttributes()
    }

    override fun prepareDDStoreProduct(): Product {
        if (scrapedProduct.description.uppercase().contains(ProcessorManufacturer.INTEL.toString())) {
            processorManufacturer = ProcessorManufacturer.INTEL
        } else if (scrapedProduct.description.uppercase().contains(ProcessorManufacturer.AMD.toString())) {
            processorManufacturer = ProcessorManufacturer.AMD
        }

        for(ramTypeEnum in RamType.values()) {
            if(scrapedProduct.description.uppercase().contains(ramTypeEnum.toString()) ||
                scrapedProduct.name.uppercase().contains(ramTypeEnum.toString())) {
                ramType = ramTypeEnum
                break
            }
        }

        for (socketEnum in Socket.values()) {
            val parsedSocketName = socketEnum.toString()
                .uppercase()
                .replace("_", "")
            if (scrapedProduct.name.uppercase().contains(parsedSocketName) ||
                scrapedProduct.description.uppercase().contains(parsedSocketName) ||
                scrapedProduct.description.uppercase().contains("Socket " + socketEnum.shortVal)
            ) {
                socket = socketEnum
                break
            }
        }

        for (chipsetEnum in Chipset.values()) {
            if (scrapedProduct.description.uppercase().contains(chipsetEnum.toString())) {
                chipset = chipsetEnum
            }
        }

        return setCompatibilityAttributes()
    }

    private fun setCompatibilityAttributes(): Product {
        product.compatibilityAttributes = ProductCompatibilityAttributes(
            product,
            chipset = chipset,
            formFactor = formFactor,
            socket = socket,
            processorManufacturer = processorManufacturer,
            numOfRamSlots = numOfRamSlots,
            numOfSataPorts = numOfSataPorts,
            numOfM2Slots = numOfM2Slots,
            ramType = ramType
        )

        return product
    }
}