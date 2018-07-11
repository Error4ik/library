package com.voronin.library.services;

import com.voronin.library.domain.Author;
import com.voronin.library.domain.Book;
import com.voronin.library.domain.Genre;
import com.voronin.library.repository.BookRepository;
import com.voronin.library.util.CropTheFile;
import com.voronin.library.util.WriteFileToDisk;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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

    @MockBean
    private BufferedImage bufferedImage;

    @MockBean
    private PDDocument pdDocument;

    @Autowired
    private BookService bookService;

    private final Book book = new Book("name", "description", new Timestamp(new Date().getTime()));

    @Before
    public void init() throws IOException {
        final Genre genre = new Genre();
        genre.setGenre("aaa");
        genre.setCountBooks(1);
        this.book.setGenres(new ArrayList<>(Lists.newArrayList(genre)));
    }

    @Test
    public void whenSaveBookShouldReturnBook() {
        when(bookRepository.save(book)).thenReturn(book);

        assertThat(book, is(bookService.save(book)));
    }

    @Test
    public void whenGetBooksShouldReturnBooks() {
        final List<Book> books = new ArrayList<>(Lists.newArrayList(book));
        when(bookRepository.findAllByOrderByDateAddedAsc()).thenReturn(books);

        assertThat(bookService.getBooks(), is(books));
    }

    @Test
    public void whenGetBookByIdShouldReturnBookById() {
        final UUID uuid = UUID.randomUUID();
        book.setId(uuid);
        when(bookRepository.getBookById(uuid)).thenReturn(book);

        assertThat(bookService.getBookById(uuid), is(book));
    }

    @Test
    public void whenGetBooksByGenreShouldReturnBooksByGenre() {
        final Genre genre = new Genre();
        genre.setGenre("test");
        List<Genre> genres = new ArrayList<>(Lists.newArrayList(genre));
        List<Book> books = new ArrayList<>(Lists.newArrayList(book));
        when(bookRepository.getBooksByGenres(genres)).thenReturn(books);

        assertThat(bookService.getBooksByGenre(genre), is(books));
    }

    @Test
    public void whenGetBooksByAuthorShouldReturnBooksByAuthor() {
        final Author author = new Author();
        author.setName("test");
        List<Author> authors = new ArrayList<>(Lists.newArrayList(author));
        List<Book> books = new ArrayList<>(Lists.newArrayList(book));
        when(bookRepository.getBooksByAuthors(authors)).thenReturn(books);

        assertThat(bookService.getBooksByAuthor(author), is(books));
    }

    @Test
    public void whenThePrepareBookCallsTheBookShouldReturn() throws Exception {
        when(multipartFile.getOriginalFilename()).thenReturn("file");
        when(cropTheFile.crop(multipartFile)).thenReturn(bufferedImage);
        when(writeFileToDisk.writeImage("file", bufferedImage)).thenReturn(file);
        when(genreService.findGenresInName(Lists.newArrayList("genre"))).thenReturn(book.getGenres());
        when(writeFileToDisk.writeBook(multipartFile, book))
                .thenReturn(Files.createTempFile("file", "png").toFile());

        assertThat(bookService.prepareBook(
                book.getName(), "author", "genre", multipartFile, multipartFile,
                book.getDescription(), book.getCreateDate()).getName(), is(book.getName()));
    }
}
