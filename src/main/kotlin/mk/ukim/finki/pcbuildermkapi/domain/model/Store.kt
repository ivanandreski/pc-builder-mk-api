package mk.ukim.finki.pcbuildermkapi.domain.model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
class Store(
    var name: String,
    var imageUrl: String? = null
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

    @OneToMany(mappedBy = "store", cascade = [CascadeType.ALL], orphanRemoval = true)
    val storeLocations: List<StoreLocation> = ArrayList()
}