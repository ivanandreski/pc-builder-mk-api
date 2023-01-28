package mk.ukim.finki.pcbuildermkapi.domain.model

import jakarta.persistence.*
import mk.ukim.finki.pcbuildermkapi.domain.dto.ScrapedProduct
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
data class Product(
    var name: String,
    var priceMkd: Double,
    var originalId: String,
    var originalUrl: String,
    var imageUrl: String?,

    @Column(columnDefinition="TEXT")
    var description: String,

    var manufacturer: String,

    @Column(columnDefinition = "boolean default false")
    var isDiscontinued: Boolean = false,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "compatibility_attributes_id", referencedColumnName = "id")
    var compatibilityAttributes: ProductCompatibilityAttributes? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    var category: Category? = null,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @field:CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP")
    lateinit var createdAt: LocalDateTime

    @field:UpdateTimestamp
    @Column(columnDefinition = "TIMESTAMP")
    lateinit var modifiedAt: LocalDateTime

    @OneToMany(mappedBy = "product", cascade = [CascadeType.ALL], orphanRemoval = true)
    val productsInStoreLocation: List<ProductInStoreLocation> = ArrayList()

    @OneToMany(mappedBy = "product", cascade = [CascadeType.ALL], orphanRemoval = true)
    val priceHistory: List<ProductPriceHistory> = ArrayList()
}
