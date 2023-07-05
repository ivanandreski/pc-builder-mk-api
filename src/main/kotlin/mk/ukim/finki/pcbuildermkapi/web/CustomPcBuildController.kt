package mk.ukim.finki.pcbuildermkapi.web

import jakarta.servlet.http.HttpServletRequest
import mk.ukim.finki.pcbuildermkapi.domain.dto.`in`.CustomPcBuildRequest
import mk.ukim.finki.pcbuildermkapi.domain.dto.out.CustomPcBuildDto
import mk.ukim.finki.pcbuildermkapi.service.AuthenticationService
import mk.ukim.finki.pcbuildermkapi.service.CustomPcBuildService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/customPcBuild")
@CrossOrigin
class CustomPcBuildController(
    private val customPcBuildService: CustomPcBuildService,
    private val authenticationService: AuthenticationService
) {

    @GetMapping
    fun getCustomPcBuild(request: HttpServletRequest): ResponseEntity<Any> {
        val user = authenticationService.findUserByEmail(
            authenticationService.extractEmailFromJwt(request)
        )
        return ResponseEntity.ok(
            customPcBuildService.getCustomPcBuild(
                user
            )
        )
    }

    @PutMapping("/addProduct")
    fun addProductToCustomPcBuild(
        @RequestBody customPcBuildRequest: CustomPcBuildRequest,
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        val user = authenticationService.findUserByEmail(
            authenticationService.extractEmailFromJwt(request)
        )
        return ResponseEntity.ok(
            customPcBuildService.addProductToCustomPcBuild(
                customPcBuildRequest.productSlug,
                user
            )
        )
    }

    @PutMapping("/removeProduct")
    fun removeProductToCustomPcBuild(
        @RequestBody customPcBuildRequest: CustomPcBuildRequest,
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        val user = authenticationService.findUserByEmail(
            authenticationService.extractEmailFromJwt(request)
        )
        return ResponseEntity.ok(
            customPcBuildService.removeProductFromCustomPcBuild(
                customPcBuildRequest.productSlug,
                user
            )
        )
    }

    @GetMapping("/test")
    fun test(): Any {
        return "gfhkgjjfd"
    }
}