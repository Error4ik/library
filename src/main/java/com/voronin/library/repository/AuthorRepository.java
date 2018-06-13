package com.voronin.library.repository;

import com.voronin.library.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 23.05.2018.
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, UUID> {

    Author findByName(final String name);

    Author getById(final UUID id);

    @Query(nativeQuery=true, value = "SELECT * FROM authors ORDER BY RANDOM() LIMIT 20")
    List<Author> getRandomAuthorsLimit20();
}
