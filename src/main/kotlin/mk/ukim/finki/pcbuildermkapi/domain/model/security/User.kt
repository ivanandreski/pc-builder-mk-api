package mk.ukim.finki.pcbuildermkapi.domain.model.security

import jakarta.persistence.*
import mk.ukim.finki.pcbuildermkapi.domain.model.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "users")
class User(
    private var email: String,
    private var password: String,

    @Enumerated(EnumType.STRING)
    var role: Role,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "custom_pc_build_id", referencedColumnName = "id")
    var customPcBuild: CustomPcBuild? = null,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val forumPosts: List<ForumPost> = ArrayList(),

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val forumComments: List<ForumComment> = ArrayList(),

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val forumCommentVotes: List<ForumCommentVote> = ArrayList()
) : UserDetails, BaseEntity() {
    fun isVerified(): Boolean {
        if(username == "ivan.andreski@gmail.com") return true

        return forumComments.stream()
                .flatMap{comment -> comment.forumCommentVotes.stream()}
                .mapToInt{vote -> vote.getScore() }
                .sum() > 1000
    }

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