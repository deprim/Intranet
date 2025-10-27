package com.dandek.Intranet.Intranet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Table(name = "news")
@Entity
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull
    private Long id;

    @Column(name = "title")
    @NotBlank
    private String title;

    @Column(name = "content")
    @NotBlank
    private String content;

    @Column(name = "preview")
    @NotBlank
    private String preview;


    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @Column(name = "published_date")
    @NotNull
    private LocalDate publishedDate;

    @Column(name = "created_at")
    @NotNull
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @NotNull
    private LocalDateTime updatedAt;


    public News() {
    }

    public News(String title,
                String content,
                String preview,
                User author,
                LocalDate publishedDate,
                LocalDateTime createdAt,
                LocalDateTime updatedAt) {
        this.title = title;
        this.content = content;
        this.preview = preview;
        this.author = author;
        this.publishedDate = publishedDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public @NotNull Long getId() {
        return id;
    }

    public void setId(@NotNull Long id) {
        this.id = id;
    }

    public @NotBlank String getTitle() {
        return title;
    }

    public void setTitle(@NotBlank String title) {
        this.title = title;
    }

    public @NotBlank String getContent() {
        return content;
    }

    public void setContent(@NotBlank String content) {
        this.content = content;
    }

    public @NotBlank String getPreview() {
        return preview;
    }

    public void setPreview(@NotBlank String preview) {
        this.preview = preview;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public @NotNull LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(@NotNull LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public @NotNull LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(@NotNull LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public @NotNull LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(@NotNull LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
