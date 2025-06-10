package com.app.catalogo_filmes.movies.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.catalogo_filmes.R;
import com.app.catalogo_filmes.movies.viewmodel.MoviesViewModel;

public class MoviesListActivity extends AppCompatActivity {

    private MoviesViewModel moviesViewModel;
    private RecyclerView recyclerView;
    private MoviesAdapter moviesAdapter;
    private ProgressBar progressBar;
    private TextView textError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_movies_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout_movies_list), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerViewMovies);
        progressBar = findViewById(R.id.progressBar);
        textError = findViewById(R.id.textError);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        moviesAdapter = new MoviesAdapter();
        recyclerView.setAdapter(moviesAdapter);

        moviesViewModel = new ViewModelProvider(this).get(MoviesViewModel.class);

        observeViewModel();

        progressBar.setVisibility(View.VISIBLE);
        moviesViewModel.fetchMovies();

        Button buttonAddMovie = findViewById(R.id.buttonAddMovie);
        buttonAddMovie.setOnClickListener(v -> {
            Intent intent = new Intent(MoviesListActivity.this, AddEditMoviesActivity.class);
            startActivity(intent);
        });

    }

    private void observeViewModel() {
        moviesViewModel.getMoviesLiveData().observe(this, movies -> {
            progressBar.setVisibility(View.GONE);
            if (movies != null && !movies.isEmpty()) {
                moviesAdapter.setMovies(movies);
                textError.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            } else {
                textError.setText("Nenhum filme encontrado.");
                textError.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        });

        moviesViewModel.getErrorLiveData().observe(this, errorMsg -> {
            progressBar.setVisibility(View.GONE);
            textError.setText("Erro: " + errorMsg);
            textError.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        moviesViewModel.fetchMovies();
    }
}