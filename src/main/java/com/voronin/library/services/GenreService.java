package com.voronin.library.services;

import com.voronin.library.domain.Genre;
import com.voronin.library.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public Set<Genre> findAll() {
        Set<Genre> genres = new TreeSet<>(Comparator.comparing(Genre::getGenre));
        genres.addAll(this.genreRepository.findAll());
        return genres;
    }

    public List<Genre> findGenresInName(final List<String> names) {
        return this.genreRepository.findByGenreInOrderByGenreAsc(names);
    }

    public List<Genre> getRandomAuthorsLimit20() {
        return this.genreRepository.getRandomAuthorsLimit20();
    }
}
