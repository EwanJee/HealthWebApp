@file:Suppress("ktlint:standard:package-name")

package org.prize.healthapp.adapter.`in`.view

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class ControllerGroup {
    @GetMapping("/user")
    fun user(): String = "user"

    @GetMapping("/test")
    fun test(): String = "test"

    @GetMapping("/videos")
    fun videos(): String = "videos"

    @GetMapping("/result")
    fun getResult(
        @RequestParam("id") id: String,
    ): String = "result"
}
