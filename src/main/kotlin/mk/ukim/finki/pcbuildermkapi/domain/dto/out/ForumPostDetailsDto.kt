package mk.ukim.finki.pcbuildermkapi.domain.dto.out

import java.time.LocalDateTime

data class ForumPostDetailsDto(
        val title: String,
        val username: String,
        val text: String,
        val postedOn: LocalDateTime,
        val comments: List<ForumCommentDto>
)
