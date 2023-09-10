package mk.ukim.finki.pcbuildermkapi.domain.dto.out

import java.time.LocalDateTime

data class ForumPostDto(
        val id: Long,
        val title: String,
        val username: String,
        val postedOn: LocalDateTime,
)
