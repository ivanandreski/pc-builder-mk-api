package mk.ukim.finki.pcbuildermkapi.domain.model

import jakarta.persistence.*
import mk.ukim.finki.pcbuildermkapi.domain.enumeation.*

@Entity
data class ProductCompatibilityAttributes(
    @OneToOne(mappedBy = "compatibilityAttributes")
    val product: Product,

    @Enumerated(EnumType.STRING)
    var processorCodeName: ProcessorCodeName? = null,

    @Enumerated(EnumType.STRING)
    var socket: Socket? = null,

    @Enumerated(EnumType.STRING)
    var chipset: Chipset? = null,

    @Enumerated(EnumType.STRING)
    var processorManufacturer: ProcessorManufacturer? = null,

    @Enumerated(EnumType.STRING)
    var storageType: StorageType? = null,

    @Enumerated(EnumType.STRING)
    var formFactor: FormFactor? = null,

    @Enumerated(EnumType.STRING)
    var ramType: RamType? = null,

    var ramSpeed: String? = null,

    var numOfRamSlots: Int? = null,

    var numOfSataPorts: Int? = null,

    var numOf25InchBays: Int? = null,

    var numOf35InchBays: Int? = null,

    var numOfM2Slots: Int? = null
) : BaseEntity()
