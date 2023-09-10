package mk.ukim.finki.pcbuildermkapi.repository

import mk.ukim.finki.pcbuildermkapi.domain.model.ForumComment
import mk.ukim.finki.pcbuildermkapi.domain.model.ForumCommentVote
import mk.ukim.finki.pcbuildermkapi.domain.model.security.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ForumCommentVoteRepository : JpaRepository<ForumCommentVote, Long> {
    fun findByUserAndForumComment(user: User, comment: ForumComment): Optional<ForumCommentVote>
}