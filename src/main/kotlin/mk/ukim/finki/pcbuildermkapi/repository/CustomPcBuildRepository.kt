package mk.ukim.finki.pcbuildermkapi.repository

import mk.ukim.finki.pcbuildermkapi.domain.model.CustomPcBuild
import mk.ukim.finki.pcbuildermkapi.domain.model.security.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CustomPcBuildRepository : JpaRepository<CustomPcBuild, Long> {
    fun findByUser(user: User): Optional<CustomPcBuild>
}