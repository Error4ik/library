package com.voronin.library.controllers;

import com.voronin.library.domain.Author;
import com.voronin.library.domain.Book;
import com.voronin.library.domain.Genre;
import com.voronin.library.repository.*;
import com.voronin.library.services.AuthorService;
import com.voronin.library.services.BookService;
import com.voronin.library.services.GenreService;
import com.voronin.library.services.RatingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 11.07.2018.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
@WithMockUser(username = "user", roles = {"USER"})
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private GenreService genreService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private RatingService ratingService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private ImageRepository imageRepository;

    @MockBean
    private GenreRepository genreRepository;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private RatingRepository ratingRepository;

    private final UUID uuid = UUID.randomUUID();


    @Test
    public void whenMappingGenresShouldReturnGenres() throws Exception {
        this.mockMvc.perform(get("/book/genres"))
                .andExpect(status().isOk());

        verify(this.genreService, times(1)).findAll();
    }

    @Test
    public void whenMappingBooksShouldReturnBooks() throws Exception {
        this.mockMvc.perform(get("/book/books"))
                .andExpect(status().isOk());

        verify(this.bookService, times(1)).getBooks();
    }

    @Test
    public void whenMappingGenreByIdShouldReturnBooks() throws Exception {
        final Genre genre = new Genre();
        when(this.genreService.getGenreById(uuid)).thenReturn(genre);

        this.mockMvc.perform(get("/book/genre/{id}", uuid))
                .andExpect(status().isOk());

        verify(this.genreService, times(1)).getGenreById(uuid);
        verify(this.bookService, times(1)).getBooksByGenre(genre);
    }

    @Test
    public void whenMappingAuthorByIdShouldReturnBooks() throws Exception {
        final Author author = new Author();
        author.setName("author");
        when(this.authorService.getAuthorById(uuid)).thenReturn(author);

        this.mockMvc.perform(get("/book/author/{id}", uuid))
                .andExpect(status().isOk());

        verify(this.authorService, times(1)).getAuthorById(uuid);
        verify(this.bookService, times(1)).getBooksByAuthor(author);
    }

    @Test
    public void whenMappingBookByIdShouldReturnBook() throws Exception {
        this.mockMvc.perform(get("/book/book/{id}", uuid))
                .andExpect(status().isOk());

        verify(this.bookService, times(1)).getBookById(uuid);
    }

    @Test
    public void whenMappingAddRatingShouldReturnBook() throws Exception {
        final int currentRating = 1;

        org.springframework.security.core.userdetails.User u =
                new org.springframework.security.core.userdetails.User(
                        "user", "", AuthorityUtils.createAuthorityList("USER"));
        TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(u, null);
        testingAuthenticationToken.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(testingAuthenticationToken);

        this.mockMvc.perform(get("/book/add-rating")
                .param("id", uuid.toString())
                .param("rating", String.valueOf(currentRating))
                .principal(testingAuthenticationToken))
                .andExpect(status().isOk());

        verify(this.ratingService, times(1))
                .prepareAndSave(uuid.toString(), currentRating, testingAuthenticationToken);
    }

    @Test
    public void whenSaveBookShouldReturnBook() throws Exception {
        Date d = new Date();
        d = getStartOfDay(d);

        final String val = "test";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        when(this.bookService.prepareBook(val, val, val, null, null, val, d)).thenReturn(new Book());

        this.mockMvc.perform(get("/book/save")
                .param("name", val)
                .param("author", val)
                .param("genre", val)
                .param("description", val)
                .param("date", format.format(d))
        ).andExpect(status().isOk());

        verify(this.bookService, times(1))
                .prepareBook(val, val, val, null, null, val, d);
        verify(this.bookService, times(1)).save(new Book());
    }

    private Date getStartOfDay(final Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}
