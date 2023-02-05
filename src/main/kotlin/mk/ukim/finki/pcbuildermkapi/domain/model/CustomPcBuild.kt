package mk.ukim.finki.pcbuildermkapi.domain.model

import jakarta.persistence.Entity
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import mk.ukim.finki.pcbuildermkapi.domain.model.security.User

@Entity
@Table(name = "custom_pc_builds")
data class CustomPcBuild(
    var caseId: Long? = null,
    var graphicsCardId: Long? = null,
    var motherboardId: Long? = null,
    var powerSupplyId: Long? = null,
    var processorId: Long? = null,
    var storageIds: String = "",
    var ramIds: String = "",

    @OneToOne(mappedBy = "customPcBuild")
    val user: User,
) : BaseEntity()
