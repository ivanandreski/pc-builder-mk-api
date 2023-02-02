package mk.ukim.finki.pcbuildermkapi.domain.dto.`in`

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.RequestParam

class ProductRequest(
    @RequestParam
    val category: String? = null,

    @RequestParam
    val page: Int? = null,

    @RequestParam
    val pageSize: Int? = null,

    // todo: add store filter, price range filter, isAvailable, list of selected store locations filter
) {
    fun getPageable(): Pageable {
        return PageRequest.of(page ?: 1, pageSize ?: 2)
    }
}
