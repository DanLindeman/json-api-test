package com.example.jsonapitest

import com.github.jasminb.jsonapi.*
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

import com.github.jasminb.jsonapi.annotations.Id
import com.github.jasminb.jsonapi.annotations.Type
import com.github.jasminb.jsonapi.annotations.Links as aLinks
import com.github.jasminb.jsonapi.Links
import com.github.jasminb.jsonapi.annotations.Relationship
import com.github.jasminb.jsonapi.annotations.RelationshipLinks


@SpringBootApplication
class JsonApiTestApplication

@RestController
class RootController {
	@GetMapping("/", produces=["text/plain", "application/json"])
	fun index(): Any {
			return "[[[["
	}
	@GetMapping("/okay")
	fun okay() : ByteArray {
		val converter = ResourceConverter(Book::class.java, Author::class.java)
		val myBook = Book("asdasdas")
		myBook.author = Author("mcgee magoo")
		myBook.book = myBook
		myBook.authorLinks = Links()
		myBook.links = Links()
		return converter.writeDocument(JSONAPIDocument(myBook))
	}
}

@Type("book")
class Book(@Id val isbn: String) {

	@Relationship("author", relType = RelType.RELATED)
	lateinit var author: Author

	@Relationship("book", relType = RelType.SELF)
	lateinit var book: Book

	@RelationshipLinks("author")
	lateinit var authorLinks: Links

	@aLinks
	lateinit var links: Links
}

@Type("author")
data class Author(
		@Id val name: String
)




fun main(args: Array<String>) {
	runApplication<JsonApiTestApplication>(*args)
}
