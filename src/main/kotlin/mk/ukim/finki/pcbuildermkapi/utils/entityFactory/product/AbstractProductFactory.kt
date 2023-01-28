package mk.ukim.finki.pcbuildermkapi.utils.entityFactory.product

import mk.ukim.finki.pcbuildermkapi.domain.dto.ScrapedProduct
import mk.ukim.finki.pcbuildermkapi.domain.model.Product

abstract class AbstractProductFactory(
    protected val scrapedProduct: ScrapedProduct
) {
    protected lateinit var product: Product

    fun prepareProduct(): Product? {
        product = Product(
            name = scrapedProduct.name,
            priceMkd = scrapedProduct.priceMkd.filter { it.isDigit() }.toDouble(),
            originalId = scrapedProduct.originalId,
            imageUrl = scrapedProduct.imageUrl,
            description = scrapedProduct.description,
            originalUrl = scrapedProduct.productUrl,
            manufacturer = scrapedProduct.brand
        )

        when(scrapedProduct.storeName) {
            "Anhoch" -> return prepareAnhochProduct()
            "Setec" -> return prepareSetecProduct()
            "DDStore" -> return prepareDDStoreProduct()
        }

        return null
    }

    abstract fun prepareAnhochProduct(): Product
    abstract fun prepareSetecProduct(): Product
    abstract fun prepareDDStoreProduct(): Product
}