package mk.ukim.finki.pcbuildermkapi.repository

import mk.ukim.finki.pcbuildermkapi.domain.model.Store
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface StoreRepository : JpaRepository<Store, Long> {
    fun findByName(name: String): Optional<Store>
    fun findBySlug(slug: String): Optional<Store>
}