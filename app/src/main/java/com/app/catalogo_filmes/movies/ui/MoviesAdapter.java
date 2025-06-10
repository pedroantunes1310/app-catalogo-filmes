package com.app.catalogo_filmes.movies.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.catalogo_filmes.R;
import com.app.catalogo_filmes.movies.model.Movies;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private List<Movies> movies = new ArrayList<>();

    public void setMovies(List<Movies> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_item_movie, parent, false);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {
        Movies m = movies.get(position);
        holder.textTitle.setText(m.getTitle());
        holder.textRating.setText("Nota: " + m.getRating());
        holder.textStatus.setText("Status: " + m.getStatus());

        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, AddEditMoviesActivity.class);

            intent.putExtra(AddEditMoviesActivity.EXTRA_MOVIE_ID, m.getId());
            intent.putExtra(AddEditMoviesActivity.EXTRA_MOVIE_TITLE, m.getTitle());
            intent.putExtra(AddEditMoviesActivity.EXTRA_MOVIE_DESCRIPTION, m.getDescription());
            intent.putExtra(AddEditMoviesActivity.EXTRA_MOVIE_STATUS, m.getStatus());
            intent.putExtra(AddEditMoviesActivity.EXTRA_MOVIE_COMMENT, m.getComment());
            intent.putExtra(AddEditMoviesActivity.EXTRA_MOVIE_RATING, m.getRating());

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    static class MoviesViewHolder extends RecyclerView.ViewHolder {

        TextView textTitle, textRating, textStatus;

        public MoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textRating = itemView.findViewById(R.id.textRating);
            textStatus = itemView.findViewById(R.id.textStatus);
        }
    }
}
