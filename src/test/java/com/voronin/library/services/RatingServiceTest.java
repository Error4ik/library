package com.voronin.library.services;

import com.voronin.library.domain.Book;
import com.voronin.library.domain.Rating;
import com.voronin.library.domain.User;
import com.voronin.library.repository.RatingRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.Principal;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 11.07.2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RatingServiceTest {

    @MockBean
    private RatingRepository ratingRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private BookService bookService;

    @MockBean
    private Principal principal;

    @Autowired
    private RatingService ratingService;

    private final Rating rating = new Rating(UUID.randomUUID(), UUID.randomUUID(), 1);

    private final User user = new User("alex", "pass");

    private final Book book = new Book();

    @Before
    public void init() {
        user.setId(UUID.randomUUID());
        book.setId(UUID.randomUUID());
    }

    @Test
    public void whenSaveRatingShouldReturnRating() {
        when(ratingRepository.save(rating)).thenReturn(rating);

        assertThat(this.ratingService.save(rating), is(rating));
    }

    @Test
    public void whenGetRatingByUserIdAndBookIdShouldReturnRating() {
        when(ratingRepository.getByUserIdAndBookId(rating.getUserId(), rating.getBookId())).thenReturn(rating);

        assertThat(this.ratingService.getRatingByUserIdAndBookId(rating.getUserId(), rating.getBookId()), is(rating));
    }

    @Test
    public void whenPrepareAndSaveIfRatingNullShouldReturnBook() {
        UUID id = UUID.randomUUID();
        book.setAverageRating(2);

        when(bookService.getBookById(id)).thenReturn(book);
        when(principal.getName()).thenReturn(user.getEmail());
        when(userService.findUserByEmail(principal.getName())).thenReturn(user);
        when(ratingRepository.getByUserIdAndBookId(user.getId(), book.getId())).thenReturn(null);

        assertThat(this.ratingService.prepareAndSave(id.toString(), 5, principal), is(book));
    }

    @Test
    public void whenPrepareAndSaveIfRatingNotNullShouldReturnBook() {
        UUID id = UUID.randomUUID();
        book.setRating(3);
        book.setVotes(1);
        book.setAverageRating(2);

        when(bookService.getBookById(id)).thenReturn(book);
        when(principal.getName()).thenReturn(user.getEmail());
        when(userService.findUserByEmail(principal.getName())).thenReturn(user);
        when(ratingRepository.getByUserIdAndBookId(user.getId(), book.getId())).thenReturn(rating);

        assertThat(this.ratingService.prepareAndSave(id.toString(), 5, principal), is(book));
    }

}
