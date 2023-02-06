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
        "select p.* " +
                "from products as p " +
                "left join stores as s on s.id = p.store_id " +
                "left join categories as c on c.id = p.category_id " +
                "where discontinued = false " +
                "and (:search is null or (UPPER(p.name) like %:search% or UPPER(p.description) like %:search%)) " +
                "and (:category_id is null or p.category_id = :category_id) " +
                "and (:store_id is null or p.store_id = :store_id) " +
                "and (:is_available is null or p.available = :is_available) " +
                "and (:manufacturer is null or UPPER(p.manufacturer) = :manufacturer) " +
                "and (p.price_mkd >= :start_price)" +
                "and (:end_price is null or p.price_mkd <= :end_price) ",
        nativeQuery = true
    )
    fun findAll(
        pageable: Pageable,
        @Param("search") search: String?,
        @Param("category_id") categoryId: Long?,
        @Param("store_id") storeId: Long?,
        @Param("is_available") isAvailable: Boolean?,
        @Param("manufacturer") manufacturer: String?,
        @Param("start_price") startPrice: Double,
        @Param("end_price") endPrice: Double?,
    ): Page<Product>

    fun findByOriginalId(originalId: String): Optional<Product>
    fun existsByOriginalId(originalId: String): Boolean
    fun findByCategory(category: Category, pageable: Pageable): Page<Product>
    fun findBySlug(slug: String): Optional<Product>

    @Query(
        "select min(p.price_mkd) " +
                "from products as p", nativeQuery = true
    )
    fun getMinProductPrice(): Double

    @Query(
        "select max(p.price_mkd) " +
                "from products as p", nativeQuery = true
    )
    fun getMaxProductPrice(): Double
}