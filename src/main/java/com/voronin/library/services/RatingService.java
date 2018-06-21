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
            currentRating = new Rating(user.getId(), book.getId(), rating);
            this.ratingRepository.save(currentRating);
            book.setRating(book.getRating() + rating);
            book.setVotes(book.getVotes() + 1);
            this.bookService.save(book);
        } else {
            if (currentRating.getRating() != rating) {
                int newRating = (book.getRating() - currentRating.getRating()) + rating;
                book.setRating(newRating);
                this.bookService.save(book);
                currentRating.setRating(rating);
                this.ratingRepository.save(currentRating);
            }
        }
        return book;
    }
}
