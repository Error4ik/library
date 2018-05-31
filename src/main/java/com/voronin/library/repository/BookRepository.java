package com.voronin.library.repository;

import com.voronin.library.domain.Author;
import com.voronin.library.domain.Book;
import com.voronin.library.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
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
public interface BookRepository extends JpaRepository<Book, UUID> {

    List<Book> getBooksByGenres(Collection<Genre> genres);

    List<Book> getBooksByAuthors(Collection<Author> authors);

    Book getBookById(final UUID id);

    List<Book> findAllByOrderByDateAddedAsc();
}
