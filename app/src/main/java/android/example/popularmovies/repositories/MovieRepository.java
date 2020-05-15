package android.example.popularmovies.repositories;

import android.example.popularmovies.models.Movie;
import android.example.popularmovies.requests.MovieDBClient;

import androidx.lifecycle.LiveData;

import java.util.List;

// Movie repository is also implemented as Singleton:
public class MovieRepository {

    private static MovieRepository instance;
    private MovieDBClient dbClient;

    public static MovieRepository getInstance() {
        if (instance == null) {
            return instance = new MovieRepository();
        } else {
            return instance;
        }
    }

    private MovieRepository() {
        dbClient = MovieDBClient.getInstance();
    }

    public LiveData<List<Movie>> getMovies() {
        return dbClient.getMovies();
    }

    public void retrieveMoviesApi(String query, int pageNumber) {
        if (pageNumber == 0) {
            pageNumber = 1;
        }
        //retrieving the movie data using MovieDBClient
    }
}
