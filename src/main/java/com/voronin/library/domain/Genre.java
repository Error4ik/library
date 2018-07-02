package com.voronin.library.domain;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 18.05.2018.
 */
@Entity(name = "genres")
public class Genre {

    @Id
    @org.hibernate.annotations.Type(type = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String genre;

    @Column(name = "count_books")
    private int countBooks;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getCountBooks() {
        return countBooks;
    }

    public void setCountBooks(int countBooks) {
        this.countBooks = countBooks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Genre)) return false;
        Genre genre1 = (Genre) o;
        return Objects.equals(getId(), genre1.getId()) &&
                Objects.equals(getGenre(), genre1.getGenre())
                && Objects.equals(getCountBooks(), genre1.getCountBooks());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getGenre(), getCountBooks());
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", genre='" + genre + '\'' +
                ", countBooks=" + countBooks +
                '}';
    }
}
