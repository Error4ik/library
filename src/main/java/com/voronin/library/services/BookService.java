package com.voronin.library.services;

import com.google.common.collect.Lists;
import com.voronin.library.domain.Author;
import com.voronin.library.domain.Book;
import com.voronin.library.domain.Genre;
import com.voronin.library.domain.Image;
import com.voronin.library.repository.BookRepository;
import com.voronin.library.util.CropTheFile;
import com.voronin.library.util.WriteFileToDisk;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
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

    @Value("${default.image.path}")
    private String pathToDefaultImage;

    @Value("${default.image.name}")
    private String imageName;


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
        return this.bookRepository.getBooksByGenres(new ArrayList<Genre>(Lists.newArrayList(genre)));
    }

    public Book prepareBook(final String name, final String author, final String genre, final MultipartFile cover,
                            final MultipartFile someBook, final String description, final Date date) {
        Book book = new Book(name, description.trim(), new Timestamp(date.getTime()));
        book.setGenres(this.getGenres(genre));
        File file = this.writeFileToDisk.writeBook(someBook, book);
        book.setAuthors(this.getAuthors(author));
        book.setDateAdded(new Timestamp(System.currentTimeMillis()));
        book.setCover(this.imageService.save(prepareImage(cover)));
        book.setUrl(file.getAbsolutePath());
        book.setPage(this.countPage(file));
        return book;
    }

    public List<Book> getBooksByAuthor(final Author author) {
        return this.bookRepository.getBooksByAuthors(new ArrayList<Author>(Lists.newArrayList(author)));
    }

    private Image prepareImage(final MultipartFile file) {
        MultipartFile tmp = file;
        if (!(tmp.getOriginalFilename().length() > 0)) {
            tmp = this.getFile();
        }
        File f = this.writeFileToDisk.writeImage(tmp.getOriginalFilename(), this.cropTheFile.crop(tmp));
        Image img = new Image();
        img.setName(f.getName());
        img.setUrl(f.getAbsolutePath());
        return img;
    }

    private MultipartFile getFile() {
        MultipartFile file = null;
        try (FileInputStream input = new FileInputStream(new File(pathToDefaultImage))) {
            file = new MockMultipartFile("file", imageName, "text/plain",
                    IOUtils.toByteArray(input));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    private int countPage(final File file) {
        int countPage = 0;
        try {
            PDDocument pdDocument = PDDocument.load(file);
            countPage = pdDocument.getNumberOfPages();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return countPage;
    }

    private List<Genre> getGenres(final String genre) {
        List<Genre> list = this.genreService.findGenresInName(Arrays.asList(genre.trim().split(",")));
        for (Genre g : list) {
            g.setCountBooks(g.getCountBooks() + 1);
        }
        return list;
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
}
