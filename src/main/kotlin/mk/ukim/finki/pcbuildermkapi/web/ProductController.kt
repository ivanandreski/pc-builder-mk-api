package mk.ukim.finki.pcbuildermkapi.web

import mk.ukim.finki.pcbuildermkapi.domain.dto.out.ProductDto
import mk.ukim.finki.pcbuildermkapi.domain.dto.`in`.ProductRequest
import mk.ukim.finki.pcbuildermkapi.service.ProductService
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/product")
class ProductController(
    private val productService: ProductService
) {
    @GetMapping
    fun getAll(productRequest: ProductRequest): Page<ProductDto> {
        return productService.getAll(productRequest)
    }
}