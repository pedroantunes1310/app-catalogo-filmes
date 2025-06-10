package com.app.catalogo_filmes.movies.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.catalogo_filmes.movies.model.Movies;
import com.app.catalogo_filmes.movies.repository.MoviesRepository;

import java.util.List;

public class MoviesViewModel extends ViewModel {

    private final MoviesRepository moviesRepository;
    private final MutableLiveData<List<Movies>> moviesLiveData;
    private final MutableLiveData<String> errorLiveData;

    public MoviesViewModel() {
        moviesRepository = new MoviesRepository();
        moviesLiveData = new MutableLiveData<>();
        errorLiveData = new MutableLiveData<>();
    }

    public LiveData<List<Movies>> getMoviesLiveData() {
        return moviesLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public void fetchMovies() {
        moviesRepository.getMovies(new MoviesRepository.MoviesCallback() {
            @Override
            public void onSuccess(List<Movies> movies) {
                moviesLiveData.setValue(movies);
            }

            @Override
            public void onFailure(Exception e) {
                errorLiveData.setValue(e.getMessage());
            }
        });
    }

}
