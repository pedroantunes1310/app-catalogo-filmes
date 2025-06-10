package com.app.catalogo_filmes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.catalogo_filmes.auth.repository.AuthRepository;
import com.app.catalogo_filmes.auth.ui.AuthActivity;
import com.app.catalogo_filmes.movies.ui.MoviesListActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private TextView textWelcome;
    private Button buttonLogout;
    private Button buttonMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textWelcome = findViewById(R.id.textWelcome);
        buttonLogout = findViewById(R.id.buttonLogout);
        buttonMovies = findViewById(R.id.buttonMovies);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            textWelcome.setText("Bem vindo, " + user.getEmail());
        }
        else {
            startActivity(new Intent(this, AuthActivity.class));
            finish();
        }

        buttonLogout.setOnClickListener(v -> {
            new AuthRepository().signOut();
            startActivity(new Intent(MainActivity.this, AuthActivity.class));
            finish();
        });

        buttonMovies.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, MoviesListActivity.class));
        });
    }
}