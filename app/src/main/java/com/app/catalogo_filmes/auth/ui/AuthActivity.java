package com.app.catalogo_filmes.auth.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.app.catalogo_filmes.MainActivity;
import com.app.catalogo_filmes.R;
import com.app.catalogo_filmes.auth.viewmodel.AuthViewModel;
import com.app.catalogo_filmes.movies.ui.MoviesListActivity;

public class AuthActivity extends AppCompatActivity {

    private AuthViewModel authViewModel;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_auth);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout_auth), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.buttonLogin);
        signupButton = findViewById(R.id.buttonSignup);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        observeViewModel();

        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            if (validateForm(email, password)) {
                authViewModel.signIn(email, password);
            }
        });

        signupButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            if (validateForm(email, password)) {
                authViewModel.signUp(email, password);
            }
        });

    }

    private void observeViewModel() {
        authViewModel.getUserLiveData().observe(this, user -> {
            if (user != null) {
                Toast.makeText(AuthActivity.this, "Login efetuado com sucesso: " + user.getEmail(), Toast.LENGTH_SHORT).show();

            }
        });

        authViewModel.getErrorLiveData().observe(this, errorMsg -> {
            if (errorMsg != null) {
                Toast.makeText(AuthActivity.this, "Erro: " + errorMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateForm(String email, String password) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Por favor, informe o email e senha", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}