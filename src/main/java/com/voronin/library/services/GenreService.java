package com.voronin.library.services;

import com.voronin.library.domain.Genre;
import com.voronin.library.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 18.05.2018.
 */
@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    public Genre save(final Genre genre) {
        return this.genreRepository.save(genre);
    }

    public Genre getGenreById(final UUID id) {
        return this.genreRepository.getById(id);
    }

    public List<Genre> findAll() {
        return this.genreRepository.findAllByOrderByGenreAsc();
    }

    public List<Genre> findGenresInName(final List<String> names) {
        return this.genreRepository.findByGenreInOrderByGenreAsc(names);
    }
}
