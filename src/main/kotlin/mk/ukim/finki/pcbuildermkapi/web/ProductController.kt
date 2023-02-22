package mk.ukim.finki.pcbuildermkapi.web

import mk.ukim.finki.pcbuildermkapi.domain.dto.out.ProductDto
import mk.ukim.finki.pcbuildermkapi.domain.dto.`in`.ProductRequest
import mk.ukim.finki.pcbuildermkapi.service.ProductService
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/product")
@CrossOrigin
class ProductController(
    private val productService: ProductService
) {
    @GetMapping
    fun getAll(productRequest: ProductRequest): ResponseEntity<Any> {
        return ResponseEntity.ok(productService.getAll(productRequest))
    }
}