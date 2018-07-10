package com.voronin.library.services;

import com.voronin.library.domain.Author;
import com.voronin.library.repository.AuthorRepository;
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
 * @since 06.07.2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorServiceTest {

    @MockBean
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorService authorService;

    private final Author author = new Author();

    @Before
    public void init() {
        this.author.setName("test");
        this.author.setId(UUID.randomUUID());
    }

    @Test
    public void whenSaveAuthorShouldReturnAuthor() {
        when(authorRepository.save(author)).thenReturn(author);

        assertThat(author, is(authorService.save(author)));
    }

    @Test
    public void whenFindAuthorByNameShouldReturnAuthorByName() {
        final String name = "test";
        when(this.authorRepository.findByName(name)).thenReturn(author);

        assertThat(name, is(this.authorService.findAuthorByName(name).getName()));
    }

    @Test
    public void whenGetAuthorByIdShouldReturnAuthorById() {
        final UUID uuid = UUID.randomUUID();
        author.setId(uuid);
        when(this.authorRepository.getById(uuid)).thenReturn(author);

        assertThat(author, is(this.authorService.getAuthorById(uuid)));
    }

    @Test
    public void whenGetAuthorsShouldReturnAuthors() {
        final List<Author> authors = new ArrayList<>();
        authors.add(author);
        when(this.authorRepository.findAll()).thenReturn(authors);

        assertThat(authors, is(this.authorService.getAuthors()));
    }
}
