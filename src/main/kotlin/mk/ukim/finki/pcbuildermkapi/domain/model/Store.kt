package mk.ukim.finki.pcbuildermkapi.domain.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany

@Entity
class Store(
    var name: String,
    var imageUrl: String? = null
) : BaseEntity() {
    @OneToMany(mappedBy = "store", cascade = [CascadeType.ALL], orphanRemoval = true)
    val storeLocations: List<StoreLocation> = ArrayList()
}