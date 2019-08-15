package com.example.jsonapitest

import com.github.jasminb.jsonapi.*
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

import com.github.jasminb.jsonapi.annotations.Id
import com.github.jasminb.jsonapi.annotations.Type
import com.github.jasminb.jsonapi.Links
import com.github.jasminb.jsonapi.annotations.Relationship
import com.github.jasminb.jsonapi.annotations.RelationshipLinks
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

// When you annotate with @Type, always include a path. If you don't, links on the primary data won't show up
// Annotating with a Links -> the class field name doesn't matter (aka reflection isn't used)
// When instantiating a Resource Class, (eg a Book) and related resources (eg Book.author)
// 		set the book.author at an Author() instance...not some BookAuthor() object or some such nonsense




fun booksList(): Map<String,Book> {
	val b1 = Book()
	b1.id = "1b"
	val a1 = Author()
	a1.id = "1a"
	a1.name = "magoo mcgee"
	b1.author = a1
	// Beyond the self link -> this is how you add links like "next", "prev", etc
	b1.links.addLink(JSONAPISpecConstants.NEXT, Link("whatever"))
	b1.authorLinks.addLink(JSONAPISpecConstants.NEXT, Link("authorLinkMagoo"))


	val b2 = Book()
	b2.id = "2b"
	val a2 = Author()
	a2.id = "2a"
	a2.name = "arty aardvark"
//	b2.links.addLink("next", Link("something"))
	b2.author = a2

	// {"1b": FirstBook, "2b": OtherBook}
	return mapOf(b1.id!! to b1, b2.id!! to b2)
}


@RestController
@RequestMapping("books")
class RootController {

	@GetMapping
	fun getBooks() : ByteArray {
		val converter = ResourceConverter(Book::class.java, Author::class.java)
    	val books = booksList().map { it.value }
		return converter.writeDocumentCollection(JSONAPIDocument(books))
	}

	@GetMapping("/{id}")
	fun getBook(@PathVariable id: String) : ByteArray {
		val converter = ResourceConverter(Book::class.java, Author::class.java)
		val book = booksList()[id]
		return converter.writeDocument(JSONAPIDocument(book))
	}

	@GetMapping("/{id}/relationships/author")
	fun authorRelationship(@PathVariable id: String) : ByteArray {
		val converter = ResourceConverter(BookAuthor::class.java )
		val books = booksList()
		val bookAuthor = BookAuthor()
		bookAuthor.id = "$id-bookAuthor"
		bookAuthor.book = books[id]
		bookAuthor.author = books[id]?.author
		return converter.writeDocument(JSONAPIDocument(bookAuthor))
	}

}

@Type(value="book", path="books/{id}")
class Book {

	@Id
	var id: String? = null
	var name: String? = null

	@Relationship("author", path="/relationships/author")
	var author: Author? = null

	@RelationshipLinks("author")
	var authorLinks: Links = Links()

	@com.github.jasminb.jsonapi.annotations.Links
	var links: Links = Links()
}

@Type("author", path="authors/{id}")
class Author {
	@Id
	var id: String? = null
	var name: String? = null

//	@com.github.jasminb.jsonapi.annotations.Links
//	var links: Links? = null
}

@Type("bookAuthor", path="books/{id}/relationships/author")
class BookAuthor {
	@Id
	var id: String? = null

//	@Relationship("self", path="books/{id}/relationships/author", relType = RelType.SELF)
//	var self: BookAuthor? = null

	@Relationship("author", path="/authors/{id}")
	var author: Author? = null

	@Relationship("book", path="/books/{id}")
	var book: Book? = null
}


@SpringBootApplication
class JsonApiTestApplication

fun main(args: Array<String>) {
	runApplication<JsonApiTestApplication>(*args)
}
