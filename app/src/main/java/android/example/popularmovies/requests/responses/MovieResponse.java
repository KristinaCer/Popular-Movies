package android.example.popularmovies.requests.responses;

import android.example.popularmovies.models.Movie;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {

    @SerializedName("page")
    private int page;

    @SerializedName("results")
    @Expose
    private List<Movie> movies;

    public List<Movie> getMovies() {
        return movies;
    }

    public int getPage() {
        return page;
    }

    @Override
    public String toString() {
        return "MovieResponse{" +
                "movies=" + movies +
                '}';
    }
}
