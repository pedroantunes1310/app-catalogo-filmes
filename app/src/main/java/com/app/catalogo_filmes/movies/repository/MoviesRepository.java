package com.app.catalogo_filmes.movies.repository;

import com.app.catalogo_filmes.movies.model.Movies;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.List;

public class MoviesRepository {

    private final FirebaseFirestore db;
    private final FirebaseAuth auth;

    public MoviesRepository() {
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    private CollectionReference getMoviesCollection() {
        String userId = auth.getCurrentUser().getUid();
        return db.collection("users").document(userId).collection("movies");
    }

    public void getMovies(MoviesCallback callback) {
        getMoviesCollection().get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Movies> movies = queryDocumentSnapshots.toObjects(Movies.class);
                    for (int i = 0; i < queryDocumentSnapshots.size(); i++) {
                        movies.get(i).setId(queryDocumentSnapshots.getDocuments().get(i).getId());
                    }
                    callback.onSuccess(movies);
                })
                .addOnFailureListener(callback::onFailure);
    }

    public void addMovies(Movies movies, MoviesActionCallback callback) {
        getMoviesCollection().add(movies)
                .addOnSuccessListener(documentReference -> callback.onSuccess())
                .addOnFailureListener(callback::onFailure);
    }

    public void updateMovies(Movies movies, MoviesActionCallback callback) {
        getMoviesCollection().document(movies.getId())
                .set(movies, SetOptions.merge())
                .addOnSuccessListener(aVoid -> callback.onSuccess())
                .addOnFailureListener(callback::onFailure);
    }

    public interface MoviesCallback {
        void onSuccess(List<Movies> movies);
        void onFailure(Exception e);
    }

    public interface MoviesActionCallback {
        void onSuccess();
        void onFailure(Exception e);
    }

}
