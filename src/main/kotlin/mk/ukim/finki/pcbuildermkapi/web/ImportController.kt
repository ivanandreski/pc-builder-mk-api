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
    fun importProductList(@RequestPart("files") files: Array<MultipartFile>): ResponseEntity<String?>? {
        importProductService.importProductList(files)

        return ResponseEntity("Import successful", HttpStatus.OK)
    }
}