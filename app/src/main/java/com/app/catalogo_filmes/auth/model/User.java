package com.app.catalogo_filmes.auth.model;


import com.google.firebase.Timestamp;

public class User {

    private String id;
    private String email;
    private Timestamp createdAt;

    public User() {}

    public User(String id, String email) {
        this.id = id;
        this.email = email;
        this.createdAt = Timestamp.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
