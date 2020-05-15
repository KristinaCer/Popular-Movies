package android.example.popularmovies.requests;

import android.example.popularmovies.requests.responses.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesDBApi {
    @GET("movie/{category}")
    Call<MovieResponse> getMoviesByCategory(@Path("category") String movieCategory,
                                            @Query("api_key") String apiKey,
                                            @Query("page") int number);
}
