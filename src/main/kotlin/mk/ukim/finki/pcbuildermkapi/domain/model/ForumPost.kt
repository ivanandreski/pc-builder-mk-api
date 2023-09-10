package mk.ukim.finki.pcbuildermkapi.domain.model

import jakarta.persistence.*
import mk.ukim.finki.pcbuildermkapi.domain.model.security.User
import mk.ukim.finki.pcbuildermkapi.utils.toSlug

@Entity
@Table(name = "forum_post")
data class ForumPost(
        val title: String,

        @Column(columnDefinition="TEXT")
        var text: String,
) : BaseEntity() {
    @OneToMany(mappedBy = "forumPost", cascade = [CascadeType.ALL], orphanRemoval = true)
    val forumComments: List<ForumComment> = ArrayList()

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User? = null
}