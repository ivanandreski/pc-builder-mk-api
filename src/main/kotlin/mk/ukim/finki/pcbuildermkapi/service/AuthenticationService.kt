package mk.ukim.finki.pcbuildermkapi.service

import jakarta.servlet.http.HttpServletRequest
import mk.ukim.finki.pcbuildermkapi.domain.dto.`in`.LoginRequest
import mk.ukim.finki.pcbuildermkapi.domain.dto.`in`.RegisterRequest
import mk.ukim.finki.pcbuildermkapi.domain.model.security.User
import org.springframework.http.ResponseEntity

interface AuthenticationService {
    fun register(registerRequest: RegisterRequest): ResponseEntity<Any>
    fun login(loginRequest: LoginRequest): ResponseEntity<Any>
    fun extractEmailFromJwt(request: HttpServletRequest): String
    fun findUserByEmail(email: String): User
}