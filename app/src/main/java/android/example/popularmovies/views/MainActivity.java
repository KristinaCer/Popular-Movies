package android.example.popularmovies.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.example.popularmovies.adapters.PopularMoviesAdapter;
import android.example.popularmovies.R;
import android.example.popularmovies.models.Movie;
import android.example.popularmovies.services.MovieResponse;
import android.example.popularmovies.services.MoviesDBApi;
import android.example.popularmovies.services.MoviesDBService;
import android.example.popularmovies.utils.Constants;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private PopularMoviesAdapter moviesAdapter;
    private RecyclerView mMoviesList;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMoviesList = (RecyclerView) findViewById(R.id.rv_movie_posters);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mMoviesList.setLayoutManager(layoutManager);
        mMoviesList.setHasFixedSize(true);

        Movie[] moviePosters = {
                new Movie(R.drawable.okja),
                new Movie(R.drawable.summer_of_songaile),
                new Movie(R.drawable.great_beauty),
                new Movie(R.drawable.summer_survivors)};
        moviesAdapter = new PopularMoviesAdapter(moviePosters);

        mMoviesList.setAdapter(moviesAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemThatWasSelected = item.getItemId();
        if (menuItemThatWasSelected == R.id.selection_menu) {
            Context context = MainActivity.this;
            testRetroFitRequest();
        }
        return true;
    }

    private void testRetroFitRequest() {

        MoviesDBApi moviesDBApi = MoviesDBService
                .getMoviesDBService()
                .getMoviesDBApi();

        Call<MovieResponse> responseCall = moviesDBApi.getMoviesByCategory("popular", Constants.API_KEY, 1);
        responseCall.enqueue(new Callback<MovieResponse>() {

            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                Log.d(TAG, "onResponse: server response:" + response.toString());
                //Response OK:
                if (response.code() == 200) {
                    Log.d(TAG, "onResponse:" + response.body().toString());
                    //Casting a response to list object type:
                    List<Movie> movies = new ArrayList<>(response.body().getMovies());
                    for (Movie movie : movies) {
                        Log.d(TAG, "onResponse" + movie.getTitle());
                    }
                } else {
                    try {
                        Log.d(TAG, "onResponse: error" + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }
}