package mk.ukim.finki.pcbuildermkapi.utils.entityFactory.product

import mk.ukim.finki.pcbuildermkapi.domain.dto.ScrapedProduct
import mk.ukim.finki.pcbuildermkapi.domain.enumeation.StorageType
import mk.ukim.finki.pcbuildermkapi.domain.model.Product

class StorageFactory(scrapedProduct: ScrapedProduct) : AbstractProductFactory(scrapedProduct) {
    private var storageType: StorageType? = null

    override fun prepareAnhochProduct(): Product {
        if (scrapedProduct.name.uppercase().contains("SSD 2.5")) {
            storageType = StorageType.SSD_SATA
        } else if (scrapedProduct.name.uppercase().contains("SSD M.2")) {
            storageType = StorageType.SSD_NVME
        } else if (scrapedProduct.name.uppercase().contains("HDD ")) {
            storageType = StorageType.HDD
        }

        return product
    }

    override fun prepareSetecProduct(): Product {
        storageType = if (scrapedProduct.description.uppercase().contains("HDD")) {
            StorageType.HDD
        } else if (
            scrapedProduct.description.uppercase().contains("NVME") ||
            scrapedProduct.description.uppercase().contains("M.2") ||
            scrapedProduct.description.uppercase().contains("PCI-E") ||
            scrapedProduct.description.uppercase().contains("PCIE")
        ) {
            StorageType.SSD_NVME
        } else if (scrapedProduct.description.uppercase().contains("SATA")) {
            StorageType.SSD_SATA
        } else {
            StorageType.SSD_SATA
        }

        return product
    }

    override fun prepareDDStoreProduct(): Product {
        if (scrapedProduct.name.uppercase().contains("HDD ")) {
            storageType = StorageType.HDD
        } else if (scrapedProduct.name.uppercase().contains("SATA")) {
            storageType = StorageType.SSD_SATA
        } else if (
            scrapedProduct.name.uppercase().contains("NVME") ||
            scrapedProduct.name.uppercase().contains("M.2")
        ) {
            storageType = StorageType.SSD_NVME
        }

        return product
    }
}