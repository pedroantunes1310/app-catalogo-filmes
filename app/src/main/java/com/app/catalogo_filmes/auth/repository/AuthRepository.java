package com.app.catalogo_filmes.auth.repository;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthRepository {

    private final FirebaseAuth firebaseAuth;

    public AuthRepository() {
        firebaseAuth = FirebaseAuth.getInstance();
    }


    public FirebaseUser getCurrentUser() {
        return firebaseAuth.getCurrentUser();
    }

    public void signUp(String email, String password, AuthCallback callback) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess(firebaseAuth.getCurrentUser());

                    }
                    else {
                        callback.onFailure(task.getException());
                    }
                });
    }

    public void signIn(String email, String password, AuthCallback callback) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess(firebaseAuth.getCurrentUser());
                    }
                    else {
                        callback.onFailure(task.getException());
                    }
                });
    }

    public void signOut() {
        firebaseAuth.signOut();
    }

    public interface AuthCallback {
        void onSuccess(FirebaseUser firebaseUser);
        void onFailure(Exception e);
    }
}
