package mk.ukim.finki.pcbuildermkapi.service.implementation

import mk.ukim.finki.pcbuildermkapi.config.JwtService
import mk.ukim.finki.pcbuildermkapi.domain.dto.LoginCredentials
import mk.ukim.finki.pcbuildermkapi.domain.dto.RegisterCredentials
import mk.ukim.finki.pcbuildermkapi.domain.model.security.Role
import mk.ukim.finki.pcbuildermkapi.domain.model.security.User
import mk.ukim.finki.pcbuildermkapi.repository.UserRepository
import mk.ukim.finki.pcbuildermkapi.service.AuthenticationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthenticationServiceImplementation(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager
) : AuthenticationService {

    override fun register(registerCredentials: RegisterCredentials): ResponseEntity<Any> {
        if(userRepository.existsByEmail(registerCredentials.email)) {
            return ResponseEntity("User with this email already exists", HttpStatus.CONFLICT)
        }

        val user = User(
            registerCredentials.email,
            passwordEncoder.encode(registerCredentials.password),
            Role.USER
        )
        userRepository.save(user)

        return ResponseEntity.ok(object {
            val token = jwtService.generateToken(user)
        })
    }

    override fun login(loginCredentials: LoginCredentials): ResponseEntity<Any> {

        return try {
            val authInputToken = UsernamePasswordAuthenticationToken(
                loginCredentials.email,
                loginCredentials.password
            )
            authenticationManager.authenticate(authInputToken)
            val user = userRepository.findByEmail(loginCredentials.email)
                .orElseThrow { Exception("User not found") }
            ResponseEntity.ok(object {
                val token = jwtService.generateToken(user)
            })
        } catch (authExc: AuthenticationException) {
            ResponseEntity("Invalid login", HttpStatus.UNAUTHORIZED)
        }
    }
}