package mk.ukim.finki.pcbuildermkapi.domain.model

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
data class ProductInStoreLocation(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_location_id", nullable = false)
    val storeLocation: StoreLocation,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    val product: Product
) : BaseEntity()
