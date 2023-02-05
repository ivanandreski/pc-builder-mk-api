package mk.ukim.finki.pcbuildermkapi.domain.dto.`in`

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.RequestParam

class ProductRequest(

    // FILTERS

    @RequestParam
    val category: String? = null,

    @RequestParam
    val page: Int? = null,

    @RequestParam
    val pageSize: Int? = null,

    @RequestParam
    val store: String? = null,

    @RequestParam
    val storeLocations: String? = null,

    // SORTING

    @RequestParam
    val sortParameter: String? = null,
) {
    fun getPageable(): Pageable {
        return PageRequest.of(page ?: 1, pageSize ?: 2)
    }

    fun getStoreLocationSlugs(): List<String> {
        return storeLocations?.split(",") ?: listOf()
    }
}
