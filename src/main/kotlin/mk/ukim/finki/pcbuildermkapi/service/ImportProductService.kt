package mk.ukim.finki.pcbuildermkapi.service

import mk.ukim.finki.pcbuildermkapi.domain.dto.ScrapedProduct
import mk.ukim.finki.pcbuildermkapi.domain.model.Product
import org.springframework.web.multipart.MultipartFile

interface ImportProductService {
    fun importProductList(files: Array<MultipartFile>)
    fun prepareProduct(scrapedProduct: ScrapedProduct): Product?
}