package mk.ukim.finki.pcbuildermkapi.utils.entityFactory.product

import mk.ukim.finki.pcbuildermkapi.domain.dto.`in`.ScrapedProduct
import mk.ukim.finki.pcbuildermkapi.domain.enumeation.FormFactor
import mk.ukim.finki.pcbuildermkapi.domain.model.Product
import mk.ukim.finki.pcbuildermkapi.domain.model.ProductCompatibilityAttributes

class CaseFactory(scrapedProduct: ScrapedProduct) : AbstractProductFactory(scrapedProduct) {

    // todo: add searchers for this
    private var numOf35InchBays: Int? = null
    private var numOfM2Slots: Int? = null

    private val formFactorAssociations: HashMap<FormFactor, Array<String>> = hashMapOf(
        FormFactor.EATX to arrayOf("ATX FULL", "E-ATX MIDI", "E-ATX MID", "ATX FULL"),
        FormFactor.ATX to arrayOf("ATX ", "ATX MID", "ATX MIDI", "ATX CASE", "MIDI-ATX", "MID TOWER"),
        FormFactor.MICRO_ATX to arrayOf("ATX MICRO"),
        FormFactor.MINI_ITX to arrayOf("ITX MINI", "Mini-ITX"),
    )

    override fun prepareAnhochProduct(): Product {
        return setFormFactorValue()
    }

    override fun prepareSetecProduct(): Product {
        for ((key, value) in formFactorAssociations) {
            for (formFactorValue in value) {
                if (scrapedProduct.name.uppercase().contains(formFactorValue)) {
                    return setCompatibilityAttributes(key)
                }
            }
        }

        for ((key, value) in formFactorAssociations) {
            for (formFactorValue in value) {
                if (scrapedProduct.description.uppercase().contains(formFactorValue)) {
                    return setCompatibilityAttributes(key)
                }
            }
        }

        return product
    }

    override fun prepareDDStoreProduct(): Product {
        return setFormFactorValue()
    }

    private fun setFormFactorValue(): Product {
        for ((key, value) in formFactorAssociations) {
            for (formFactorValue in value) {
                if (scrapedProduct.name.uppercase().contains(formFactorValue)) {
                    return setCompatibilityAttributes(key)
                }
            }
        }

        return product
    }

    private fun setCompatibilityAttributes(formFactor: FormFactor): Product {
        product.compatibilityAttributes = ProductCompatibilityAttributes(
            product,
            formFactor = formFactor
        )

        return product
    }
}