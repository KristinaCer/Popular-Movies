package android.example.popularmovies.viewmodels;

import android.example.popularmovies.models.Movie;
import android.example.popularmovies.repositories.MovieRepository;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;

public class MoviePostersListViewModel extends ViewModel {

    MovieRepository movieRepository;

    public MoviePostersListViewModel() {
        movieRepository = MovieRepository.getInstance();
    }

    public LiveData<List<Movie>> getMovies() {
        return movieRepository.getMovies();
    }

    public void retrieveMoviesApi(String query, int pageNumber){
        movieRepository.retrieveMoviesApi(query, pageNumber);
    }
}
