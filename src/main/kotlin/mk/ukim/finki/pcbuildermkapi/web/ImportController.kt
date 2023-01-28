package mk.ukim.finki.pcbuildermkapi.web

import mk.ukim.finki.pcbuildermkapi.service.ImportProductService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile


@RestController
@RequestMapping("/import")
@CrossOrigin
class ImportController(
    private val importProductService: ImportProductService
) {
    @PostMapping
    fun importProductList(@RequestParam("file") file: MultipartFile, @RequestParam("category") category: String): ResponseEntity<String?>? {
        if(category !in arrayOf("CPU", "CASE", "GPU", "STORAGE", "RAM", "MB", "PSU"))
            return ResponseEntity("Invalid category", HttpStatus.BAD_REQUEST)

        importProductService.importProductList(file, category)

        return ResponseEntity("Import successful", HttpStatus.OK)
    }
}