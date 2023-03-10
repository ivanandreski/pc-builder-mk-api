package mk.ukim.finki.pcbuildermkapi.utils.entityFactory.product

import mk.ukim.finki.pcbuildermkapi.domain.dto.`in`.ScrapedProduct
import mk.ukim.finki.pcbuildermkapi.domain.model.Product

class PowerSupplyFactory(scrapedProduct: ScrapedProduct) : AbstractProductFactory(scrapedProduct) {
    override fun prepareAnhochProduct(): Product {
        return product
    }

    override fun prepareSetecProduct(): Product {
        return product
    }

    override fun prepareDDStoreProduct(): Product {
        return product
    }
}