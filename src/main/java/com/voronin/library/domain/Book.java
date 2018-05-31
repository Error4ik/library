package com.voronin.library.domain;


import javax.persistence.*;
import java.sql.Timestamp;
import java.util.*;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 17.05.2018.
 */
@Entity(name = "books")
public class Book {

    @Id
    @org.hibernate.annotations.Type(type = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "authors_books", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors;


    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "books_genre", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> genres;

    private int page;

    private String url;

    @Column(name = "create_date")
    private Timestamp createDate;

    @Column(name = "date_added")
    private Timestamp dateAdded;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "cover")
    private Image cover;

    private String description;

    public Book() {
    }

    public Book(String name, String description,Timestamp createDate) {
        this.name = name;
        this.description = description;
        this.createDate = createDate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Image getCover() {
        return cover;
    }

    public void setCover(Image cover) {
        this.cover = cover;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return getPage() == book.getPage() &&
                Objects.equals(getId(), book.getId()) &&
                Objects.equals(getName(), book.getName()) &&
                Objects.equals(getAuthors(), book.getAuthors()) &&
                Objects.equals(getGenres(), book.getGenres()) &&
                Objects.equals(getUrl(), book.getUrl()) &&
                Objects.equals(getCreateDate(), book.getCreateDate()) &&
                Objects.equals(getDateAdded(), book.getDateAdded()) &&
                Objects.equals(getCover(), book.getCover()) &&
                Objects.equals(getDescription(), book.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getAuthors(),
                getGenres(), getPage(), getUrl(), getCreateDate(), getDateAdded(), getCover(), getDescription());
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", authors=" + authors +
                ", genres=" + genres +
                ", page=" + page +
                ", url='" + url + '\'' +
                ", createDate=" + createDate +
                ", dateAdded=" + dateAdded +
                ", cover=" + cover +
                '}';
    }
}
