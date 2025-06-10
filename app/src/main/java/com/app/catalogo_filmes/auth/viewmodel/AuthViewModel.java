package com.app.catalogo_filmes.auth.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.catalogo_filmes.auth.model.User;
import com.app.catalogo_filmes.auth.repository.AuthRepository;
import com.app.catalogo_filmes.auth.repository.UserRepository;
import com.google.firebase.auth.FirebaseUser;

public class AuthViewModel extends ViewModel {

    private final AuthRepository authRepository;
    private final UserRepository userRepository;
    private final MutableLiveData<FirebaseUser> userLiveData;
    private final MutableLiveData<String> errorLiveData;

    public AuthViewModel() {
        authRepository = new AuthRepository();
        userRepository = new UserRepository();
        userLiveData = new MutableLiveData<>();
        errorLiveData = new MutableLiveData<>();
    }

    public LiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public void signIn(String email, String password) {
        authRepository.signIn(email, password, new AuthRepository.AuthCallback() {
            @Override
            public void onSuccess(FirebaseUser firebaseUser) {
                userLiveData.setValue(firebaseUser);
            }

            @Override
            public void onFailure(Exception e) {
                errorLiveData.setValue("Falha no login, credenciais incorretas ou inexistentes: " + e.getMessage());
            }
        });
    }

    public void signUp(String email, String password) {

            authRepository.signUp(email, password, new AuthRepository.AuthCallback() {
            @Override
            public void onSuccess(FirebaseUser firebaseUser) {
                User user = new User(firebaseUser.getUid(), firebaseUser.getEmail());

                userRepository.saveUser(user, new UserRepository.UserSaveCallback() {
                    @Override
                    public void onSuccess() {
                        userLiveData.setValue(firebaseUser);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        firebaseUser.delete()
                                        .addOnCompleteListener(task -> {
                                            if(task.isSuccessful()){
                                                errorLiveData.setValue("Falha ao salvar dados do usuário: " + e.getMessage());
                                            }
                                            else{
                                                errorLiveData.setValue("Falha ao autenticar e salvar dados: " + e.getMessage());
                                            }
                                        });
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                errorLiveData.setValue(e.getMessage());
            }
        });
    }

    public void signOut() {
        authRepository.signOut();
        userLiveData.setValue(null);
    }
}