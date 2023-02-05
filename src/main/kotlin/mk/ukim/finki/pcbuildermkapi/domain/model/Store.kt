package mk.ukim.finki.pcbuildermkapi.domain.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import mk.ukim.finki.pcbuildermkapi.utils.toSlug

@Entity
@Table(name = "stores")
class Store(
    val name: String,
    var displayName: String = name,
    val slug: String = name.toSlug(),
    var imageUrl: String? = null
) : BaseEntity() {
    @OneToMany(mappedBy = "store", cascade = [CascadeType.ALL], orphanRemoval = true)
    val storeLocations: List<StoreLocation> = ArrayList()
}