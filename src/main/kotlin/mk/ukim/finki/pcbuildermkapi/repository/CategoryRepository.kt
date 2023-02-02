package mk.ukim.finki.pcbuildermkapi.repository

import mk.ukim.finki.pcbuildermkapi.domain.model.Category
import mk.ukim.finki.pcbuildermkapi.domain.model.Store
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CategoryRepository : JpaRepository<Category, Long> {
    fun findByName(name: String): Optional<Category>
    fun findBySlug(slug: String): Optional<Category>
}