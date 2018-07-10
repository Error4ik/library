package com.voronin.library.services;

import com.voronin.library.domain.Author;
import com.voronin.library.domain.Book;
import com.voronin.library.domain.Genre;
import com.voronin.library.repository.BookRepository;
import com.voronin.library.util.CropTheFile;
import com.voronin.library.util.WriteFileToDisk;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 06.07.2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceTest {

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private GenreService genreService;

    @MockBean
    private CropTheFile cropTheFile;

    @MockBean
    private WriteFileToDisk writeFileToDisk;

    @MockBean
    private ImageService imageService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private File file;

    @MockBean
    private MultipartFile multipartFile;

    @Autowired
    private BookService bookService;

    private final Book book = new Book("name", "description", new Timestamp(new Date().getTime()));

    @Test
    public void whenSaveBookShouldReturnBook() {
        when(bookRepository.save(book)).thenReturn(book);

        assertThat(book, is(bookService.save(book)));
    }

    @Test
    public void whenGetBooksShouldReturnBooks() {
        final List<Book> books = new ArrayList<>(Lists.newArrayList(book));
        when(bookRepository.findAllByOrderByDateAddedAsc()).thenReturn(books);

        assertThat(books, is(bookService.getBooks()));
    }

    @Test
    public void whenGetBookByIdShouldReturnBookById() {
        final UUID uuid = UUID.randomUUID();
        book.setId(uuid);
        when(bookRepository.getBookById(uuid)).thenReturn(book);

        assertThat(book, is(bookService.getBookById(uuid)));
    }

    @Test
    public void whenGetBooksByGenreShouldReturnBooksByGenre() {
        final Genre genre = new Genre();
        genre.setGenre("test");
        List<Genre> genres = new ArrayList<>(Lists.newArrayList(genre));
        List<Book> books = new ArrayList<>(Lists.newArrayList(book));
        when(bookRepository.getBooksByGenres(genres)).thenReturn(books);

        assertThat(books, is(bookService.getBooksByGenre(genre)));
    }

    @Test
    public void whenGetBooksByAuthorShouldReturnBooksByAuthor() {
        final Author author = new Author();
        author.setName("test");
        List<Author> authors = new ArrayList<>(Lists.newArrayList(author));
        List<Book> books = new ArrayList<>(Lists.newArrayList(book));
        when(bookRepository.getBooksByAuthors(authors)).thenReturn(books);

        assertThat(books, is(bookService.getBooksByAuthor(author)));
    }

//    @Test
//    public void whenThePrepareBookCallsTheBookShouldReturn() {
//        final String name = "name";
//        final String author = "author";
//        final String genre = "genre";
//        final String description = "description";
//        final Date date = new Date();
//        final Image image = new Image();
//        image.setName("image");
//        byte[] b = new byte[10];
//
//        when(writeFileToDisk.writeBook(multipartFile, book)).thenReturn(file);
//        when(cropTheFile.crop(multipartFile)).thenReturn(new BufferedImage(10, 10 ,1));
//        when(multipartFile.getOriginalFilename()).thenReturn("file");
//        when(file.getName()).thenReturn("file");
//        when(file.getAbsolutePath()).thenReturn("file");
//        when(imageService.save(image)).thenReturn(image);
//        when(bookService.prepareBook(name, author, genre, multipartFile, multipartFile, description, date)).thenReturn(book);
//
//        assertThat(book, is(bookService.prepareBook(name, author, genre, multipartFile, multipartFile, description, date)));
//    }
}
