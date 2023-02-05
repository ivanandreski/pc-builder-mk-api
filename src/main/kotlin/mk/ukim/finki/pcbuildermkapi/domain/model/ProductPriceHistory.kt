package mk.ukim.finki.pcbuildermkapi.domain.model

import jakarta.persistence.*

@Entity
@Table(name = "product_price_history")
data class ProductPriceHistory(
    val price: Double,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    val product: Product,
) : BaseEntity()
