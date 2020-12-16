package com.moa.coroutines.svc

import com.moa.coroutines.global.*
import com.moa.coroutines.mocks.*
import com.moa.coroutines.models.ServerError
import com.moa.coroutines.models.badRequest
import com.moa.coroutines.models.forbidden
import com.moa.coroutines.repos.BookRepo
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.coVerify
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.test.context.junit.jupiter.SpringExtension

/**
 * @author omar Dec 12, 2020
 */
@ExtendWith(SpringExtension::class)
@DataMongoTest
class BookServiceTests(@Autowired val bookRepo: BookRepo) {

    private val authorService = mockAuthorService()

    private val bookService = BookService(
        bookRepo,
        authorService
    )


    // #Summary method used to call/remove some methods/variables after each test
    @AfterEach
    fun destroy() = runBlocking {
        bookRepo.deleteAll()
    }

    // General function used to create a valid book in this test class
    suspend fun createValidBook(name : String = "valid book") = bookService.createBook(
        mockBookDto(
            validAuthorId,
            name,
            20
        )
    )

    @Nested
    inner class ValidateIdMethodTestCases {
        // #Test_Flow:
        // 1. call the validateId function with an id that does not exists in the database
        // 2. Method should throw a ServerError exception
        @Test
        fun `validateId should throw exception because of an invalid id`() = runBlocking {
            shouldThrow<ServerError> {
                // #Call validateId method with an invalid id
                bookService.validateId(invalid)
            }
                // #Validation stage
                .run {
                    this shouldBe invalid("book id", invalid).badRequest
                }
        }

        // #Test_Flow:
        // 1. save a book
        // 2. fetch the generated id
        // 3. validate the generated id
        @Test
        fun `validateId should be successful with a valid id`() =
            runBlocking {
                // #Create a book instance
                mockBook()
                    // #Saving stage
                    .run {
                        bookRepo.save(this)
                    }
                    // #Validation block
                    .run {
                        // #Validate the created book
                        bookService.validateId(id).id shouldBe id
                    }


            }


    }

    @Nested
    inner class ValidateNameMethodTestCases {
        // #Test_Flow:
        // 1. create a book
        // 2. call validateBookName with the book name
        // 3. no exception should be raised
        @Test
        fun `validateBookName call with a valid name`() =
            runBlocking {
                // #Create a book
                mockBook()
                    // #Successful Stage
                    .run {
                        // #First_Call -> No exception should be raised
                        shouldNotThrow<ServerError> {
                            bookService.validateBookName(name)
                        }
                    }
            }


        // #Test_Flow:
        // 1. create a book
        // 2. call validateBookName with the book name
        // 3. no exception should be raised
        // 4. save the created book
        // 5. re-call the validateBookName with the same book name
        // 6. Method should throw a ServerError exception because the name already exists
        @Test
        fun `validateBookName should throw exception because of invalid name`() =
            runBlocking {
                mockBook()
                    // #Initial_Check stage
                    .also {
                        // #Check that no exception will be raised with this book name
                        shouldNotThrow<ServerError> {
                            bookService.validateBookName(it.name)
                        }
                    }
                    // #Saving stage
                    .apply {
                        // #Save the book
                        bookRepo.save(this)

                    }
                    // #Validation stage
                    .run {
                        // #Check that method will thrown an exception
                        shouldThrow<ServerError> {
                            bookService.validateBookName(name)
                        }
                            // #Validation stage
                            .run {
                                this shouldBe exists("Book name", name).badRequest
                            }
                    }
            }

    }

    @Nested
    inner class CreateBookTestCases {

        // #Test_Flow:
        // 1. prepare a dto with a null name
        // 2. call create book method
        // 3. check that an exception with name cannot be null have been raised
        // 4. Same flow for blank name
        @Test
        fun `create book throw name not null and blank exception`() =
            runBlocking {
                // #Flow for null authorId
                coroutineScope {
                    shouldThrow<ServerError> {
                        bookService.createBook(mockBookDto(authorId = invalid, name = null))
                    }
                        // #Validation stage
                        .run {
                            this shouldBe cannotBeNull("name").badRequest
                        }
                }
                coroutineScope {
                    // #Flow for blank authorId
                    shouldThrow<ServerError> {
                        bookService.createBook(mockBookDto(authorId = invalid, name = ""))
                    }
                        // #Validation stage
                        .run {
                            this shouldBe cannotBeBlank("name").badRequest
                        }
                }

            }


        // #Test_Flow:
        // 1. prepare a dto with a null authorId
        // 2. call create book method
        // 3. check that an exception with authorId cannot be null have been raised
        // 4. Same flow for blank authorId
        @Test
        fun `create book throw authorId not null and bank exception`() =
            runBlocking {
                coroutineScope {

                    // #Flow for null authorId
                    shouldThrow<ServerError> {
                        bookService.createBook(mockBookDto(authorId = null))
                    }
                        // #Validation stage
                        .run {
                            this shouldBe cannotBeNull("author id").badRequest
                        }
                }
                coroutineScope {

                    // #Flow for blank authorId
                    shouldThrow<ServerError> {
                        bookService.createBook(mockBookDto(authorId = ""))
                    }
                        // #Validation stage
                        .run {
                            this shouldBe cannotBeBlank("author id").badRequest
                        }
                }

            }

        // #Test_Flow:
        // 1. prepare a dto with an invalid authorId = null
        // 2. call create book method
        // 3. check that an exception with invalid id have been raised
        @Test
        fun `create book throw authorId is not valid exception because there is not author with the given id in the db`() =
            runBlocking {
                shouldThrow<ServerError> {
                    bookService.createBook(mockBookDto(authorId = invalid, name = invalid))
                }
                    // #Validation stage
                    .run {
                        this shouldBe invalid("author id", invalid).badRequest
                    }
            }


        // #Test_Flow:
        // 0. Prepare a valid author
        // 1. prepare a valid dto
        // 2. call create book method
        // 3. check that the book have been successfully created
        @Test
        fun `create book with a valid data`() =
            runBlocking {

                // #Create a new book
                bookService.createBook(
                    mockBookDto(
                        authorId = validAuthorId,
                    )
                )
                    // #Validation stage
                    .run {
                        // #Check that only 1 book have been saved
                        bookRepo.count() shouldBe 1

                        // #Fetch the book and compare it with the book saved
                        bookRepo.findAll().single().id shouldBe id
                    }

                // verify that author authorization method have been called
                coVerify {
                    authorService.validateId(validAuthorId)
                }
            }


        // #Test_Flow:
        // 1. prepare a valid dto
        // 2. call create book method
        // 3. check that the book have been successfully created
        // 4. call create book method with the same dto
        // 5. check that an exception has been occurred
        @Test
        fun `user cannot create two books with the same name`() = runBlocking {

            // #Create a new book
            createValidBook()
                // #Validation stage
                .also {
                    // #Check that a new book have been saved
                    bookRepo.count() shouldBe 1
                }

            // #Create another book with the same name
            shouldThrow<ServerError> {
                createValidBook()
            }
                // #Validation stage
                .run {
                    this shouldBe exists("Book name", "valid book").badRequest
                }
        }

    }

    @Nested
    inner class GetBookByIdTestCases {

        // #Test_Flow:
        // 1. call get book by id method with an invalid id
        @Test
        fun `get book by id should throw an invalid id exception`() = runBlocking {

            // #Check that method will thrown an exception
            shouldThrow<ServerError> {
                bookService.getById(invalid)
            }
                // #Validation stage
                .run {
                    this shouldBe invalid("book id", invalid).forbidden
                }

        }


        // #Test_Flow:
        // 1. create a new book
        // 2. extract book id
        // 3. call get book by id method
        // 4. validate book returned by the method
        @Test
        fun `get book by id valid test`() = runBlocking {

            // #Create a new book
            createValidBook()
                // #Creation_Validation stage
                .run {
                    bookService.getById(id)
                        // #Validation stage
                        .let {

                            // #Check that book returned is the same as the book created
                            it.id shouldBe id
                        }
                }
        }
    }

    @Nested
    inner class UpdateBookTestCases {}

    @Nested
    inner class DeleteBookByIdTestCases {
        // #Test_Flow:
        // 1. call delete book by id method with an invalid id
        @Test
        fun `delete book by id should throw an invalid id exception`() = runBlocking {

            // #Check that method will thrown an exception
            shouldThrow<ServerError> {
                bookService.deleteById(invalid)
            }
                // #Validation stage
                .run {
                    this shouldBe invalid("book id", invalid).forbidden
                }

        }


        // #Test_Flow:
        // 1. create a new book
        // 2. extract book id
        // 3. call delete book by id method
        // 4. check that the book have been deleted
        @Test
        fun `get book by id valid test`() = runBlocking {

            // #Create a new book
            createValidBook()
                // #Creation_Validation stage
                .run {
                    bookService.deleteById(id)
                        // #Validation stage
                        .run {
                            // #Check response status
                            this shouldBe successful(value = id)
                        }
                        // #Check status
                        .run {
                            // #Check that the book have been deleted
                            bookRepo.count() shouldBe 0
                        }
                }
        }
    }

    @Nested
    inner class GetBooksTestCases {

        // #Test_Flow:
        // 1. call get books
        // 2. check that no book have been returned
        // 3. create new book
        // 4. call get books
        // 5. check that a book have been returned
        // 6. create new book
        // 7. call get books
        // 8. check that 2 books have been returned
        @Test
        fun `get policies valid test`() = runBlocking {

            // #Validate that no elements exists
            bookService.getAll().toList().size shouldBe 0

            // #Create a book
            createValidBook("book1")

            // #Validate that the new book is returned in getAll
            bookRepo.count() shouldBe 1
            bookService.getAll().toList().size shouldBe 1

            // #Create a book
            createValidBook("book2")

            // #Validate that the new book is returned in getAll
            bookRepo.count() shouldBe 2
            bookService.getAll().map{it.name}.toList() shouldBe listOf("book1","book2")
        }
    }

}