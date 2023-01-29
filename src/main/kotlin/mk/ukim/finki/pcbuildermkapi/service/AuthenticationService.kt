package mk.ukim.finki.pcbuildermkapi.service

import mk.ukim.finki.pcbuildermkapi.domain.dto.LoginCredentials
import mk.ukim.finki.pcbuildermkapi.domain.dto.RegisterCredentials
import org.springframework.http.ResponseEntity

interface AuthenticationService {
    fun register(registerCredentials: RegisterCredentials): ResponseEntity<Any>
    fun login(loginCredentials: LoginCredentials): ResponseEntity<Any>
}