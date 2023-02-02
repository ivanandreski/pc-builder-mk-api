package mk.ukim.finki.pcbuildermkapi.repository

import mk.ukim.finki.pcbuildermkapi.domain.model.Category
import mk.ukim.finki.pcbuildermkapi.domain.model.Product
import mk.ukim.finki.pcbuildermkapi.domain.model.Store
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ProductRepository : JpaRepository<Product, Long> {
    fun findByOriginalId(originalId: String): Optional<Product>
    fun existsByOriginalId(originalId: String): Boolean
    fun findByCategory(category: Category, pageable: Pageable): Page<Product>
    fun findBySlug(slug: String): Optional<Product>
}