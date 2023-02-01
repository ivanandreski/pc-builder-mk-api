package mk.ukim.finki.pcbuildermkapi.domain.model.security

import jakarta.persistence.*
import mk.ukim.finki.pcbuildermkapi.domain.model.BaseEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "users")
class User(
    private var email: String,
    private var password: String,

    @Enumerated(EnumType.STRING)
    var role: Role
) : UserDetails, BaseEntity() {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority("ROLE_" + role.name))
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}