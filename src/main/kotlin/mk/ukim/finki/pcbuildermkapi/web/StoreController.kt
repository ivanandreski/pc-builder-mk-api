package mk.ukim.finki.pcbuildermkapi.web

import mk.ukim.finki.pcbuildermkapi.domain.dto.`in`.ProductRequest
import mk.ukim.finki.pcbuildermkapi.domain.dto.out.StoreLocationDto
import mk.ukim.finki.pcbuildermkapi.repository.StoreLocationRepository
import mk.ukim.finki.pcbuildermkapi.service.ProductService
import org.apache.coyote.Response
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.jvm.optionals.getOrNull

@RestController
@CrossOrigin
@RequestMapping("/api/store")
class StoreController(
        private val storeLocationRepository: StoreLocationRepository
) {
    @GetMapping
    fun getAll(): ResponseEntity<Any> {
        return ResponseEntity.ok(storeLocationRepository.findAll().map { storeLocation -> StoreLocationDto(storeLocation.name, storeLocation.slug, storeLocation.longitude, storeLocation.latitude) })
    }

    @PostMapping
    fun setStoreLocation(@RequestBody storeLocationDto: StoreLocationDto): Any {
        val storeLocation = storeLocationRepository.findByName(storeLocationDto.slug)

        if(storeLocation.isEmpty)
                return ResponseEntity.notFound()

        storeLocation.get().longitude = storeLocationDto.longitude
        storeLocation.get().latitude = storeLocationDto.latitude
        storeLocationRepository.save(storeLocation.get())

        return ResponseEntity.ok("Saved Succesfully")
    }
}