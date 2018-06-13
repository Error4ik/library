package com.voronin.library.repository;

import com.voronin.library.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 18.05.2018.
 */
@Repository
public interface GenreRepository extends JpaRepository<Genre, UUID>{

    List<Genre> findByGenreInOrderByGenreAsc(Collection<String> genre);

    Genre getById(final UUID id);

    @Query(nativeQuery=true, value = "SELECT * FROM genres ORDER BY RANDOM() LIMIT 20")
    List<Genre> getRandomAuthorsLimit20();
}
