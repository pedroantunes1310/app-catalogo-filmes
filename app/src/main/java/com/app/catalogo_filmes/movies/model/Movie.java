package com.app.catalogo_filmes.movies.model;

import com.google.firebase.Timestamp;

import java.time.OffsetDateTime;
import java.util.Date;

public class Movie {

    private String id;
    private String title;
    private String description;
    private String status;
    private String comment;
    private float rating;
    private Timestamp lastModified;
    private Timestamp createdAt;

    public Movie(String id, String title, String description, String status, String comment, float rating, Timestamp lastModified, Timestamp createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.comment = comment;
        this.rating = rating;
        this.lastModified = lastModified;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Timestamp getLastModified() {
        return lastModified;
    }

    public void setLastModified(Timestamp lastModified) {
        this.lastModified = lastModified;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}