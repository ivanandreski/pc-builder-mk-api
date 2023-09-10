package mk.ukim.finki.pcbuildermkapi.repository

import mk.ukim.finki.pcbuildermkapi.domain.model.ForumCommentVote
import mk.ukim.finki.pcbuildermkapi.domain.model.ForumPost
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ForumPostRepository : JpaRepository<ForumPost, Long> {
}