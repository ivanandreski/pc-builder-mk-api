package mk.ukim.finki.pcbuildermkapi.web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/test")
internal class TestController {

    @GetMapping
    fun test(): String {
        return "Hello"
    }
}