package com.voronin.library.repository;

import com.voronin.library.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 19.06.2018.
 */
@Repository
public interface RatingRepository extends JpaRepository<Rating, UUID> {

    Rating getByUserIdAndBookId(final UUID userId, final UUID bookId);
}
