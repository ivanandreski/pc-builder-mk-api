package mk.ukim.finki.pcbuildermkapi.service

import mk.ukim.finki.pcbuildermkapi.domain.dto.ScrapedProduct
import mk.ukim.finki.pcbuildermkapi.domain.dto.ScrapedProductList
import org.springframework.web.multipart.MultipartFile

interface ParseImportFileService {
    fun parseJsonFile(file: MultipartFile): Array<ScrapedProduct>
}