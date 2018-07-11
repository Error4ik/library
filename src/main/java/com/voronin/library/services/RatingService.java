package com.voronin.library.services;

import com.voronin.library.domain.Book;
import com.voronin.library.domain.Rating;
import com.voronin.library.domain.User;
import com.voronin.library.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.UUID;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 19.06.2018.
 */
@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    public Rating save(final Rating userBookRating) {
        return this.ratingRepository.save(userBookRating);
    }

    public Rating getRatingByUserIdAndBookId(final UUID userId, final UUID bookId) {
        return this.ratingRepository.getByUserIdAndBookId(userId, bookId);
    }

    public Book prepareAndSave(final String id, final int rating, final Principal principal) {
        final Book book = this.bookService.getBookById(UUID.fromString(id));
        final User user = this.userService.findUserByEmail(principal.getName());
        Rating currentRating = this.ratingRepository.getByUserIdAndBookId(user.getId(), book.getId());
        if (currentRating == null) {
            createRating(rating, book, user);
        } else {
            if (currentRating.getRating() != rating) {
                updateRating(rating, book, currentRating);
            }
        }
        return book;
    }

    private void createRating(int rating, Book book, User user) {
        Rating currentRating;
        currentRating = new Rating(user.getId(), book.getId(), rating);
        this.ratingRepository.save(currentRating);
        book.setRating(book.getRating() + rating);
        book.setVotes(book.getVotes() + 1);
        book.setAverageRating(((float) book.getRating() / book.getVotes() / 5) * 100);
        this.bookService.save(book);
    }

    private void updateRating(int rating, Book book, Rating currentRating) {
        int newRating = (book.getRating() - currentRating.getRating()) + rating;
        book.setRating(newRating);
        book.setAverageRating(((float) book.getRating() / book.getVotes() / 5) * 100);
        this.bookService.save(book);
        currentRating.setRating(rating);
        this.ratingRepository.save(currentRating);
    }
}
