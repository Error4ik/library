package com.voronin.library.services;

import com.voronin.library.domain.Author;
import com.voronin.library.domain.Book;
import com.voronin.library.domain.Genre;
import com.voronin.library.domain.Image;
import com.voronin.library.repository.BookRepository;
import com.voronin.library.util.CropTheFile;
import com.voronin.library.util.WriteFileToDisk;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 18.05.2018.
 */
@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private GenreService genreService;

    @Autowired
    private CropTheFile cropTheFile;

    @Autowired
    private WriteFileToDisk writeFileToDisk;

    @Autowired
    private ImageService imageService;

    @Autowired
    private AuthorService authorService;

    public Book save(final Book book) {
        return this.bookRepository.save(book);
    }

    public List<Book> getBooks() {
        return this.bookRepository.findAllByOrderByDateAddedAsc();
    }

    public Book getBookById(final UUID id) {
        return this.bookRepository.getBookById(id);
    }

    public List<Book> getBooksByGenre(final Genre genre) {
        Set<Genre> genres = new HashSet<>();
        genres.add(genre);
        return this.bookRepository.getBooksByGenres(genres);
    }

    public Book prepareAndSave(final String name, final String author, final String genre, final MultipartFile cover,
                               final MultipartFile someBook, final String description, final Date date) {
        Book book = new Book(name, description.trim(), new Timestamp(date.getTime()));
        book.setAuthors(this.getAuthors(author));
        book.setGenres(this.getGenres(genre));
        book.setDateAdded(new Timestamp(System.currentTimeMillis()));
        book.setCover(this.imageService.save(prepareImage(cover)));
        File file = this.writeFileToDisk.writeBook(someBook, book);
        book.setUrl(file.getAbsolutePath());
        book.setPage(this.countPage(file));
        return this.save(book);
    }

    private Image prepareImage(final MultipartFile file) {
        File f = this.writeFileToDisk.writeImage(this.cropTheFile.crop(file));
        Image image = new Image();
        image.setName(f.getName());
        image.setUrl(f.getAbsolutePath());
        return image;
    }

    private int countPage(final File file) {
        int countPage = 0;
        try {
            PDDocument pdDocument = PDDocument.load(file);
            countPage = pdDocument.getNumberOfPages();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return countPage;
    }

    private List<Genre> getGenres(final String genre) {
        return this.genreService.findGenresInName(Arrays.asList(genre.trim().split(",")));
    }

    private List<Author> getAuthors(final String author) {
        String[] authors = author.trim().split(",");
        List<Author> list = new ArrayList<>();
        for (String s : authors) {
            Author newAuthor = this.authorService.findAuthorByName(s.trim());
            if (newAuthor == null) {
                Author saveAuthor = new Author();
                saveAuthor.setName(s.trim());
                newAuthor = this.authorService.save(saveAuthor);
            }
            list.add(newAuthor);
        }
        return list;
    }

    public List<Book> getBooksByAuthor(final Author author) {
        List<Author> authors = new ArrayList<>();
        authors.add(author);
        return this.bookRepository.getBooksByAuthors(authors);
    }
}
