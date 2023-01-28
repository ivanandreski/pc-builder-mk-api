package mk.ukim.finki.pcbuildermkapi.utils.entityFactory.product

import mk.ukim.finki.pcbuildermkapi.domain.dto.ScrapedProduct
import mk.ukim.finki.pcbuildermkapi.domain.enumeation.RamType
import mk.ukim.finki.pcbuildermkapi.domain.model.Product
import mk.ukim.finki.pcbuildermkapi.domain.model.ProductCompatibilityAttributes

class RamFactory(scrapedProduct: ScrapedProduct) : AbstractProductFactory(scrapedProduct) {

    private var ramType: RamType? = null
    private var ramSpeed: String? = null

    override fun prepareAnhochProduct(): Product {
        val nameSplit = scrapedProduct.name.split("\\s+")
        for(nameAttr in nameSplit) {
            for(ramTypeEnum in RamType.values()) {
                if(nameAttr.equals(ramTypeEnum.toString(), ignoreCase = true)) {
                    ramType = ramTypeEnum
                }
            }

            if(nameAttr.lowercase().endsWith("mhz")) {
                ramSpeed = nameAttr.uppercase()
            }
        }

        return setRamAttributes()
    }

    override fun prepareSetecProduct(): Product {
        val descriptionSplit = scrapedProduct.description
            .replace(",", "")
            .split("\\s+")
        var prevIndex = -1
        for(descriptionAttr in descriptionSplit) {
            for(ramTypeEnum in RamType.values()) {
                if(descriptionAttr.equals(ramTypeEnum.toString(), ignoreCase = true)) {
                    ramType = ramTypeEnum
                }
            }

            if(descriptionAttr.lowercase().endsWith("mhz")) {
                ramSpeed = descriptionAttr.uppercase()
            }
            else if(descriptionAttr.lowercase() == "mhz" && prevIndex != -1) {
                ramSpeed = descriptionSplit[prevIndex] + descriptionAttr.uppercase()
            }

            prevIndex++
        }

        return setRamAttributes()
    }

    override fun prepareDDStoreProduct(): Product {
        val nameSplit = scrapedProduct.name
            .split("\\s+")
        var prevIndex = -1
        for(nameAttr in nameSplit) {
            for(ramTypeEnum in RamType.values()) {
                if(nameAttr.equals(ramTypeEnum.toString(), ignoreCase = true)) {
                    ramType = ramTypeEnum
                }
            }

            if(nameAttr.lowercase().endsWith("mhz")) {
                ramSpeed = nameAttr.uppercase()
            }
            else if(nameAttr.lowercase() == "mhz" && prevIndex != -1) {
                ramSpeed = nameAttr[prevIndex] + nameAttr.uppercase()
            }

            prevIndex++
        }

        return setRamAttributes()
    }

    private fun setRamAttributes(): Product {
        product.compatibilityAttributes = ProductCompatibilityAttributes(
            product,
            ramType = ramType,
            ramSpeed = ramSpeed
        )

        return product
    }
}