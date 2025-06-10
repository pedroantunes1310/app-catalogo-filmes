package com.app.catalogo_filmes.movies.ui;

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

import com.app.catalogo_filmes.R;
import com.app.catalogo_filmes.movies.model.Movies;
import com.app.catalogo_filmes.movies.repository.MoviesRepository;
import com.google.firebase.Timestamp;

public class AddEditMoviesActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE_ID = "extra_movie_id";
    public static final String EXTRA_MOVIE_TITLE = "extra_movie_title";
    public static final String EXTRA_MOVIE_DESCRIPTION = "extra_movie_description";
    public static final String EXTRA_MOVIE_STATUS = "extra_movie_status";
    public static final String EXTRA_MOVIE_COMMENT = "extra_movie_comment";
    public static final String EXTRA_MOVIE_RATING = "extra_movie_rating";

    private EditText editTextTitle, editTextDescription, editTextStatus, editTextComment, editTextRating;
    private Button buttonSave;

    private MoviesRepository moviesRepository;
    private String movieIdToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_edit_movies);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout_add_edit_movies), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextStatus = findViewById(R.id.editTextStatus);
        editTextComment = findViewById(R.id.editTextComment);
        editTextRating = findViewById(R.id.editTextRating);
        buttonSave = findViewById(R.id.buttonSave);

        moviesRepository = new MoviesRepository();


        if (getIntent().hasExtra(EXTRA_MOVIE_ID)) {
            movieIdToEdit = getIntent().getStringExtra(EXTRA_MOVIE_ID);

            editTextTitle.setText(getIntent().getStringExtra(EXTRA_MOVIE_TITLE));
            editTextDescription.setText(getIntent().getStringExtra(EXTRA_MOVIE_DESCRIPTION));
            editTextStatus.setText(getIntent().getStringExtra(EXTRA_MOVIE_STATUS));
            editTextComment.setText(getIntent().getStringExtra(EXTRA_MOVIE_COMMENT));
            editTextRating.setText(String.valueOf(getIntent().getFloatExtra(EXTRA_MOVIE_RATING, 0f)));
        }

        buttonSave.setOnClickListener(v -> saveMovies());
    }

    private void saveMovies() {
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String status = editTextStatus.getText().toString().trim();
        String comment = editTextComment.getText().toString().trim();
        String ratingStr = editTextRating.getText().toString().trim();

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(status) ) {
            Toast.makeText(this, "Título e Status são obrigatórios", Toast.LENGTH_SHORT).show();
            return;
        }

        float rating;
        try {
            rating = Float.parseFloat(ratingStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Nota inválida.", Toast.LENGTH_SHORT).show();
            return;
        }

        Timestamp now = Timestamp.now();

        Movies movies = new Movies();
        movies.setTitle(title);
        movies.setDescription(description);
        movies.setStatus(status);
        movies.setComment(comment);
        movies.setRating(rating);
        movies.setLastModified(now);

        if (movieIdToEdit == null) {

            movies.setCreatedAt(Timestamp.now());
            moviesRepository.addMovies(movies, new MoviesRepository.MoviesActionCallback() {
                @Override
                public void onSuccess() {
                    Toast.makeText(AddEditMoviesActivity.this, "Filme cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(AddEditMoviesActivity.this, "Erro ao cadastrar filme: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            movies.setId(movieIdToEdit);
            movies.setCreatedAt(Timestamp.now());
            movies.setLastModified(Timestamp.now());
            moviesRepository.updateMovies(movies, new MoviesRepository.MoviesActionCallback() {
                @Override
                public void onSuccess() {
                    Toast.makeText(AddEditMoviesActivity.this, "Filme atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(AddEditMoviesActivity.this, "Erro ao atualizar filme: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}