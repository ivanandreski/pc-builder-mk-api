package mk.ukim.finki.pcbuildermkapi.service

import mk.ukim.finki.pcbuildermkapi.domain.dto.out.ProductDto
import mk.ukim.finki.pcbuildermkapi.domain.dto.`in`.ProductRequest
import mk.ukim.finki.pcbuildermkapi.domain.model.Product
import org.springframework.data.domain.Page

interface ProductService {
    fun getAll(productRequest: ProductRequest): Page<ProductDto>

    fun createProductDto(product: Product): ProductDto
}