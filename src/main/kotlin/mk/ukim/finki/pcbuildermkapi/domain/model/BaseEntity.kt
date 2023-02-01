package mk.ukim.finki.pcbuildermkapi.domain.model

import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@MappedSuperclass
open class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @field:CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP")
    lateinit var createdAt: LocalDateTime

    @field:UpdateTimestamp
    @Column(columnDefinition = "TIMESTAMP")
    lateinit var modifiedAt: LocalDateTime
}