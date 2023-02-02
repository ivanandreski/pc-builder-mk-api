package mk.ukim.finki.pcbuildermkapi.web

import mk.ukim.finki.pcbuildermkapi.domain.dto.`in`.LoginRequest
import mk.ukim.finki.pcbuildermkapi.domain.dto.`in`.RegisterRequest
import mk.ukim.finki.pcbuildermkapi.service.AuthenticationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
class AuthenticationController(
    private val authenticationService: AuthenticationService
) {

    @PostMapping("/register")
    fun register(
        @RequestBody registerRequest: RegisterRequest
    ): ResponseEntity<Any> {
        return authenticationService.register(registerRequest)
    }

    @PostMapping("/login")
    fun login(
        @RequestBody loginRequest: LoginRequest
    ): ResponseEntity<Any> {
        return authenticationService.login(loginRequest)
    }
}