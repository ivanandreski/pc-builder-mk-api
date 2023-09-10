package mk.ukim.finki.pcbuildermkapi.domain.model

import jakarta.persistence.*
import mk.ukim.finki.pcbuildermkapi.domain.model.security.User
import mk.ukim.finki.pcbuildermkapi.utils.toSlug

@Entity
@Table(name = "forum_comment")
data class ForumComment(
        @Column(columnDefinition="TEXT")
        var text: String,
) : BaseEntity() {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "forum_post_id", nullable = false)
    var forumPost: ForumPost? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User? = null

    @OneToMany(mappedBy = "forumComment", cascade = [CascadeType.ALL], orphanRemoval = true)
    val forumCommentVotes: List<ForumCommentVote> = ArrayList()
}