package mk.ukim.finki.pcbuildermkapi.domain.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany

@Entity
data class Category(
    var name: String,
    var displayName: String? = null
) : BaseEntity() {
    @OneToMany(mappedBy = "category", cascade = [CascadeType.ALL], orphanRemoval = true)
    val products: List<Product> = ArrayList()
}