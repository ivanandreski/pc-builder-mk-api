package mk.ukim.finki.pcbuildermkapi.domain.dto.`in`

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.RequestParam

class ProductRequest(

        // FILTERS

        @RequestParam
        val search: String? = null,

        @RequestParam
        val category: String? = null,

        @RequestParam
        val page: Int? = null,

        @RequestParam
        val pageSize: Int? = null,

        @RequestParam
        val store: String? = null,

        @RequestParam
        val isAvailable: Boolean? = null,

        @RequestParam
        val startPrice: Double? = null,

        @RequestParam
        val endPrice: Double? = null,

        @RequestParam
        val manufacturer: String? = null,

        @RequestParam
        val storeLocations: String? = null,

        // SORTING

        @RequestParam
        val sortParameter: String? = null,
) {
    fun getPageable(): Pageable {
        return PageRequest.of(
                page ?: 1,
                pageSize ?: 2,
                getSortColumn()
        )
    }

    private fun getSortColumn(): Sort {
        val sortParameterSplit = sortParameter?.split("_")
        if (sortParameterSplit.isNullOrEmpty()) {
            return Sort.by("slug").ascending()
        }
        val sortColumn = when (sortParameterSplit[0]) {
            "category" -> "c.slug"
            "store" -> "s.slug"
            "price" -> "price_mkd"
            "manufacturer" -> "manufacturer"
            else -> "slug"
        }

        return if(sortParameterSplit[1] == "desc") {
            Sort.by(sortColumn).descending()
        }
        else {
            Sort.by(sortColumn).ascending()
        }
    }

    fun getStoreLocationSlugs(): List<String> {
        return storeLocations?.split(",") ?: listOf()
    }
}
