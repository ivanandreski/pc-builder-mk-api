package mk.ukim.finki.pcbuildermkapi.web

import jakarta.servlet.http.HttpServletRequest
import lombok.AllArgsConstructor
import mk.ukim.finki.pcbuildermkapi.domain.dto.`in`.CreateCommentRequest
import mk.ukim.finki.pcbuildermkapi.domain.dto.`in`.CreatePostRequest
import mk.ukim.finki.pcbuildermkapi.domain.dto.out.ForumCommentDto
import mk.ukim.finki.pcbuildermkapi.domain.dto.out.ForumPostDetailsDto
import mk.ukim.finki.pcbuildermkapi.domain.dto.out.ForumPostDto
import mk.ukim.finki.pcbuildermkapi.domain.model.ForumComment
import mk.ukim.finki.pcbuildermkapi.domain.model.ForumCommentVote
import mk.ukim.finki.pcbuildermkapi.domain.model.ForumPost
import mk.ukim.finki.pcbuildermkapi.repository.ForumCommentRepository
import mk.ukim.finki.pcbuildermkapi.repository.ForumCommentVoteRepository
import mk.ukim.finki.pcbuildermkapi.repository.ForumPostRepository
import mk.ukim.finki.pcbuildermkapi.repository.UserRepository
import mk.ukim.finki.pcbuildermkapi.service.AuthenticationService
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping("/api/forum")
class ForumController(
        private val forumPostRepository: ForumPostRepository,
        private val forumCommentRepository: ForumCommentRepository,
        private val forumCommentVoteRepository: ForumCommentVoteRepository,
        private val authenticationService: AuthenticationService
) {

    @GetMapping
    fun test() {
        while (true){
            println("hjghjgjhg")
        }
    }

    @GetMapping("/posts")
    fun getPosts(): ResponseEntity<List<ForumPostDto>> {
        return ResponseEntity.ok(forumPostRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))
                .map { ForumPostDto(it.id!!, it.title, it.user!!.username, it.createdAt) })
    }

    @GetMapping("/posts/{id}")
    fun getPosts(@PathVariable id: Long, request: HttpServletRequest): ResponseEntity<ForumPostDetailsDto> {
        val user = authenticationService.findUserByEmail(
                authenticationService.extractEmailFromJwt(request)
        )

        return ResponseEntity.ok(forumPostRepository.findById(id).map {
            ForumPostDetailsDto(
                    it.title,
                    it.user!!.username,
                    it.text,
                    it.createdAt,
                    it.forumComments.map { comment ->
                        ForumCommentDto(
                                comment.id!!,
                                comment.text,
                                comment.user!!.username,
                                comment.user!!.isVerified(),
                                comment.createdAt,
                                comment.forumCommentVotes.stream().mapToInt { vote -> vote.getScore() }.sum(),
                                comment.forumCommentVotes.filter { vote -> vote.user!!.id == user.id }.firstOrNull()?.isPositive
                        )
                    }
            )
        }.orElse(null))
    }

    @PostMapping("/posts")
    fun createPost(@RequestBody createForumPostRequest: CreatePostRequest, request: HttpServletRequest): ResponseEntity<Any> {
        val user = authenticationService.findUserByEmail(
                authenticationService.extractEmailFromJwt(request)
        )

        val post = ForumPost(
                createForumPostRequest.title,
                createForumPostRequest.text
        )
        post.user = user

        forumPostRepository.save(post)

        return ResponseEntity.ok("Post saved")
    }

    @PostMapping("/posts/{postId}/comments")
    fun createPost(@PathVariable postId: Long, @RequestBody createForumCommentRequest: CreateCommentRequest, request: HttpServletRequest): ResponseEntity<Any> {
        val user = authenticationService.findUserByEmail(
                authenticationService.extractEmailFromJwt(request)
        )

        val comment = ForumComment(
                createForumCommentRequest.text
        )
        comment.user = user
        comment.forumPost = forumPostRepository.findById(postId).get()

        forumCommentRepository.save(comment)

        return ResponseEntity.ok("Comment saved")
    }

    @PostMapping("/posts/{postId}/comments/{commentId}/vote/{newVoteValue}")
    fun voteComment(@PathVariable postId: Long, @PathVariable commentId: Long, @PathVariable newVoteValue: Int, request: HttpServletRequest): ResponseEntity<Any> {
        val user = authenticationService.findUserByEmail(
                authenticationService.extractEmailFromJwt(request)
        )

        val comment = forumCommentRepository.findById(commentId).get()
        val vote = forumCommentVoteRepository.findByUserAndForumComment(user, comment)
        if (vote.isEmpty) {
            val voteValue = ForumCommentVote(
                    isPositive = newVoteValue == 1
            )
            voteValue.forumComment = comment
            voteValue.user = user
            forumCommentVoteRepository.save(voteValue)
        } else {
            val voteValue = vote.get()
            if(newVoteValue == 0) {
                forumCommentVoteRepository.delete(voteValue)
                return ResponseEntity.ok("Vote removed")
            }

            voteValue.isPositive = newVoteValue == 1
            forumCommentVoteRepository.save(voteValue)
        }

        return ResponseEntity.ok("Vote saves")
    }
}