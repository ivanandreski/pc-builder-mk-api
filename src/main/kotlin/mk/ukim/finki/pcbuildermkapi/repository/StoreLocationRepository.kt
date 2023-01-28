package mk.ukim.finki.pcbuildermkapi.repository

import mk.ukim.finki.pcbuildermkapi.domain.model.Store
import mk.ukim.finki.pcbuildermkapi.domain.model.StoreLocation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface StoreLocationRepository : JpaRepository<StoreLocation, Long> {
    fun findByName(name: String): Optional<StoreLocation>
    fun existsByName(name: String): Boolean
}