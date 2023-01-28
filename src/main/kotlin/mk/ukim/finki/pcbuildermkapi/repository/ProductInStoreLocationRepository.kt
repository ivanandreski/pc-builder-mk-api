package mk.ukim.finki.pcbuildermkapi.repository

import mk.ukim.finki.pcbuildermkapi.domain.model.Product
import mk.ukim.finki.pcbuildermkapi.domain.model.ProductInStoreLocation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductInStoreLocationRepository : JpaRepository<ProductInStoreLocation, Long> {
    fun deleteByProduct(product: Product)
}