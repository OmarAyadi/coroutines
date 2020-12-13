package com.moa.coroutines.repos

import com.moa.coroutines.global.authorId
import com.moa.coroutines.mocks.generateId
import com.moa.coroutines.mocks.mockAuthor
import com.moa.coroutines.mocks.mockBook
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.longs.shouldBeExactly
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest

/**
 * @author omar Dec 12, 2020
 */

@DataMongoTest
class BookRepoTests @Autowired constructor(
    private val bookRepo: BookRepo,
    private val authorRepo: AuthorRepo
) {


    // #Summary method used to remove any data that have been submitted or saved
    @AfterEach
    fun destroy() = runBlocking {
        // #Remove all data from the collections
        bookRepo.deleteAll()
        authorRepo.deleteAll()
    }

    // #Test_Flow:
    // 1. check that the repository is empty
    // 2. create a book
    // 3. check that no other book with the same name exists
    // 4. save the book
    // 5. check that the book have been created successfully
    // 6. fetch the book with it's name
    // 7. check that both books have the same id
    @Test
    fun `testing find by name method`() {
        runBlocking {
            // #Validate empty repo
            bookRepo.count() shouldBeExactly 0

            // #Create a book
            mockBook()
                // #Initial_Check stage
                .also {
                    // #Check that no book with the same name already exists
                    bookRepo.findByName(it.name) shouldBe null
                }
                // Saving Stage
                .apply {
                    // #Apply an id to the book
                    id = generateId
                    // #Save the policy
                    bookRepo.save(this)
                }
                // Validation Stage
                .run {
                    // #Check that the repository is no longer empty
                    bookRepo.count() shouldNotBe 0
                    // #Fetch the book with the name
                    val book = bookRepo.findByName(name)
                    // #Check that we got a book as a result from the database
                    book shouldNotBe null
                    // #Validate book information
                    book?.let {
                        // #Check that both books have the same id
                        it.id shouldBe id
                    }
                }
        }
    }

    // #Test_Flow:
    // 1. check that the repository is empty
    // 2. save multiple books with different authors
    // 3. fetch books for a given
    // #Test_data:
    // |- two authors
    // |---- author1 will have 2 books
    // |---- author2 will have 1 books
    // |- three books
    @Test
    fun `testing find by author`() {
        runBlocking {

            // #Validate empty repos
            bookRepo.count() shouldBeExactly 0
            bookRepo.count() shouldBeExactly 0

            // #Prepare data
            // -> two authors
            // * author1 will have 2 books
            val author1 = authorRepo.save(mockAuthor())
            // * author2 will have 1 books
            val author2 = authorRepo.save(mockAuthor())

            // #Prepare books
            // * author1 will have 2 books
            // * author2 will have only 1 book
            bookRepo.saveAll(
                listOf(
                    mockBook(author1.id),
                    mockBook(author1.id),
                    mockBook(author2.id)
                )
            ).collect {
                print(it)
            }


            launch(Dispatchers.IO) {
                // #Check that we have 2 books for the first author
                bookRepo.findByAuthorId(author1.id).toList().size shouldBeExactly 2
            }

            launch(Dispatchers.IO) {
                // #Check that we have 1 books for the second author
                bookRepo.findByAuthorId(author2.id).toList().size shouldBeExactly 1
            }
        }
    }


}