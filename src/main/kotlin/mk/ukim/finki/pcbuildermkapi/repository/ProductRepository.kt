package mk.ukim.finki.pcbuildermkapi.repository

import mk.ukim.finki.pcbuildermkapi.domain.model.Category
import mk.ukim.finki.pcbuildermkapi.domain.model.Product
import mk.ukim.finki.pcbuildermkapi.domain.model.Store
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ProductRepository : JpaRepository<Product, Long> {

    @Query(
        "select * " +
                "from product as p " +
                "where (:category_id is null or p.category_id = :category_id) " +
                "and (:store_id is null or p.store_id = :store_id) "
    , nativeQuery = true)
    fun findAll(
        pageable: Pageable,
        @Param("category_id") categoryId: Long?,
        @Param("store_id") storeId: Long?,
    ): Page<Product>
    fun findByOriginalId(originalId: String): Optional<Product>
    fun existsByOriginalId(originalId: String): Boolean
    fun findByCategory(category: Category, pageable: Pageable): Page<Product>
    fun findBySlug(slug: String): Optional<Product>
}