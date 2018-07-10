package com.voronin.library.services;

import com.voronin.library.domain.Genre;
import com.voronin.library.repository.GenreRepository;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 10.07.2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GenreServiceTest {

    @MockBean
    private GenreRepository genreRepository;

    @Autowired
    private GenreService genreService;

    private final Genre genre = new Genre();

    @Before
    public void init() {
        this.genre.setGenre("test");
        this.genre.setId(UUID.randomUUID());
        this.genre.setCountBooks(10);
    }


    @Test
    public void whenSaveGenreShouldReturnGenre() {
        when(genreRepository.save(genre)).thenReturn(genre);

        assertThat(genre, is(this.genreService.save(genre)));
    }

    @Test
    public void whenGetGenreByIdShouldReturnGenreById() {
        when(genreRepository.getById(genre.getId())).thenReturn(genre);

        assertThat(genre, is(this.genreService.getGenreById(genre.getId())));
    }

    @Test
    public void whenFindAllShouldReturnGenres() {
        List<Genre> genres = new ArrayList<>(Lists.newArrayList(genre));
        when(genreRepository.findAllByOrderByGenreAsc()).thenReturn(genres);

        assertThat(genres, is(this.genreService.findAll()));
    }

    @Test
    public void whenFindGenresInNameShouldReturnGenres() {
        List<String> names = new ArrayList<>(Lists.newArrayList("name1", "name2", "name3"));
        List<Genre> genres = new ArrayList<>(Lists.newArrayList(genre));
        when(genreRepository.findByGenreInOrderByGenreAsc(names)).thenReturn(genres);

        assertThat(genres, is(this.genreService.findGenresInName(names)));
    }

}
