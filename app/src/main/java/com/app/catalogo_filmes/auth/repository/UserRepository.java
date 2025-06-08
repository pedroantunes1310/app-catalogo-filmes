package com.app.catalogo_filmes.auth.repository;

import com.app.catalogo_filmes.auth.model.User;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserRepository {

    private final FirebaseFirestore db;

    public UserRepository() {
        db = FirebaseFirestore.getInstance();
    }

    public void saveUser(User user, UserSaveCallback callback) {
        db.collection("users")
                .document(user.getId()) // UID do FirebaseAuth
                .set(user)
                .addOnSuccessListener(aVoid -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onFailure(e));
    }

    public interface UserSaveCallback {
        void onSuccess();
        void onFailure(Exception e);
    }
}
