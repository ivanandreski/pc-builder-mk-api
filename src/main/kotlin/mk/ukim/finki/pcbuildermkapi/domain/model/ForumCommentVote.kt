package mk.ukim.finki.pcbuildermkapi.domain.model

import jakarta.persistence.*
import mk.ukim.finki.pcbuildermkapi.domain.model.security.User

@Entity
@Table(name = "forum_comment_vote")
data class ForumCommentVote(
        var isPositive: Boolean
) : BaseEntity() {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "forum_comment_id", nullable = false)
    var forumComment: ForumComment? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User? = null

    fun getScore(): Int {
        return if (isPositive) 1 else -1
    }
}
