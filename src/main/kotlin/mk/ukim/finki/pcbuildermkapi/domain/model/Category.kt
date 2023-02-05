package mk.ukim.finki.pcbuildermkapi.domain.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import mk.ukim.finki.pcbuildermkapi.utils.toSlug

@Entity
@Table(name = "categories")
data class Category(
    val name: String,
    var displayName: String = name,
    val slug: String = name.toSlug()
) : BaseEntity() {
    @OneToMany(mappedBy = "category", cascade = [CascadeType.ALL], orphanRemoval = true)
    val products: List<Product> = ArrayList()
}