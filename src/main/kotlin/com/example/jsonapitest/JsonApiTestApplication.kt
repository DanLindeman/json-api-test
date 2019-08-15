package com.example.jsonapitest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.RestController

@Configuration
@RestController
@SpringBootApplication
class JsonApiTestApplication

fun main(args: Array<String>) {
	runApplication<JsonApiTestApplication>(*args)
}
