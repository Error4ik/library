package com.voronin.library.controllers;

import com.voronin.library.domain.Book;
import com.voronin.library.domain.Genre;
import com.voronin.library.services.AuthorService;
import com.voronin.library.services.BookService;
import com.voronin.library.services.GenreService;
import com.voronin.library.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 18.05.2018.
 */
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private RatingService ratingService;

    @RequestMapping("/genres")
    private List<Genre> getGenres() {
        return this.genreService.findAll();
    }

    @RequestMapping("/save")
    public void saveBook(@RequestParam final String name,
                         @RequestParam final String author,
                         @RequestParam final String genre,
                         @RequestParam final MultipartFile cover,
                         @RequestParam final MultipartFile someBook,
                         @RequestParam final String description,
                         @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") final Date date) {

        this.bookService.save(this.bookService.prepareBook(name, author, genre, cover, someBook, description, date));
    }

    @RequestMapping("/books")
    public List<Book> getBooks() {
        return this.bookService.getBooks();
    }

    @RequestMapping("/genre/{id}")
    public List<Book> getBooksByGenre(@PathVariable final UUID id) {
        return this.bookService.getBooksByGenre(this.genreService.getGenreById(id));
    }

    @RequestMapping("/author/{id}")
    public List<Book> getBooksByAuthor(@PathVariable final UUID id) {
        return this.bookService.getBooksByAuthor(this.authorService.getAuthorById(id));
    }

    @RequestMapping("/book/{id}")
    public Book getBookById(@PathVariable final UUID id) {
        return this.bookService.getBookById(id);
    }

    @RequestMapping("/add-rating")
    public Book addBookRating(@RequestParam final String id, @RequestParam final int rating, final Principal principal) {
        return this.ratingService.prepareAndSave(id, rating, principal);
    }
}
