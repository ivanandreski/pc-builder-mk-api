package mk.ukim.finki.pcbuildermkapi.web

import mk.ukim.finki.pcbuildermkapi.domain.dto.LoginCredentials
import mk.ukim.finki.pcbuildermkapi.domain.dto.RegisterCredentials
import mk.ukim.finki.pcbuildermkapi.service.AuthenticationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
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
        @RequestBody registerCredentials: RegisterCredentials
    ): ResponseEntity<Any> {
        return authenticationService.register(registerCredentials)
    }

    @PostMapping("/login")
    fun login(
        @RequestBody loginCredentials: LoginCredentials
    ): ResponseEntity<Any> {
        return authenticationService.login(loginCredentials)
    }
}