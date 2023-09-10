package mk.ukim.finki.pcbuildermkapi.domain.dto.out

import java.time.LocalDateTime

data class ForumCommentDto(
        val id: Long,
        val text: String,
        val username: String,
        val userIsVerified: Boolean,
        val postedOn: LocalDateTime,
        val score: Int,
        val youVotedPositive: Boolean?
)
