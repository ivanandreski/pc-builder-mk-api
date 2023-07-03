package mk.ukim.finki.pcbuildermkapi.service.implementation

import jakarta.servlet.http.HttpServletRequest
import mk.ukim.finki.pcbuildermkapi.config.JwtService
import mk.ukim.finki.pcbuildermkapi.domain.dto.`in`.LoginRequest
import mk.ukim.finki.pcbuildermkapi.domain.dto.`in`.RegisterRequest
import mk.ukim.finki.pcbuildermkapi.domain.model.CustomPcBuild
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
    private val authenticationManager: AuthenticationManager,
) : AuthenticationService {

    override fun register(registerRequest: RegisterRequest): ResponseEntity<Any> {
        if(userRepository.existsByEmail(registerRequest.email)) {
            return ResponseEntity("User with this email already exists", HttpStatus.CONFLICT)
        }

        val user = User(
            email = registerRequest.email,
            password = passwordEncoder.encode(registerRequest.password),
            role = Role.USER
        )
        user.customPcBuild = CustomPcBuild(user = user)
        userRepository.save(user)

        return ResponseEntity.ok(object {
            val token = jwtService.generateToken(user)
            val email = user.username
        })
    }

    override fun login(loginRequest: LoginRequest): ResponseEntity<Any> {

        return try {
            val authInputToken = UsernamePasswordAuthenticationToken(
                loginRequest.email,
                loginRequest.password
            )
            authenticationManager.authenticate(authInputToken)
            val user = userRepository.findByEmail(loginRequest.email)
                .orElseThrow { Exception("User not found") }
            ResponseEntity.ok(object {
                val token = jwtService.generateToken(user)
                val email = user.username
            })
        } catch (authExc: AuthenticationException) {
            ResponseEntity("Invalid login", HttpStatus.UNAUTHORIZED)
        }
    }

    override fun extractEmailFromJwt(request: HttpServletRequest): String {
        val authHeader = request.getHeader("Authorization")
        val jwt: String = authHeader.substring(7)
        return jwtService.extractUsername(jwt)
    }

    override fun findUserByEmail(email: String): User {
        return userRepository.findByEmail(email)
            .orElseThrow{ Exception("User with email: $email was not found") }
    }
}