package android.example.popularmovies.views;

import android.content.Context;
import android.content.Intent;
import android.example.popularmovies.R;
import android.example.popularmovies.adapters.OnMoviePosterListener;
import android.example.popularmovies.adapters.PopularMoviesAdapter;
import android.example.popularmovies.models.Movie;
import android.example.popularmovies.utils.Constants;
import android.example.popularmovies.viewmodels.MoviePostersListViewModel;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MoviePostersListActivity extends AppCompatActivity implements OnMoviePosterListener {

    private PopularMoviesAdapter mAdapter;
    private RecyclerView mMoviesList;
    private static final String TAG = "MoviePostersListActivity";
    private MoviePostersListViewModel mPostersListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_posters_list);
        mMoviesList = (RecyclerView) findViewById(R.id.rv_movie_posters);
        initRecyclerView();
        mPostersListViewModel = ViewModelProviders.of(this).get(MoviePostersListViewModel.class);
        addObservers();
        this.getMoviesApi("popular", 1);
    }


    private void initRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, numberOfColumns());
        mAdapter = new PopularMoviesAdapter(this);
        mMoviesList.setAdapter(mAdapter);
        mMoviesList.setLayoutManager(layoutManager);
        mMoviesList.setHasFixedSize(true);
    }

    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // You can change this divider to adjust the size of the item
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2; //to keep the grid aspect
        return nColumns;
    }

    private void addObservers() {
        mPostersListViewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                if (movies != null) {
                    for (Movie movie :
                            movies) {
                        Log.d(TAG, "onChanged" + movie.getTitle());
                    }
                    mAdapter.setMovies(movies);
                }
            }
        });
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
            Context context = MoviePostersListActivity.this;
            getMoviesApi(Constants.MOVIE_CATEGORY_TOP_RATED, 1);
        }
        return true;
    }

    private void getMoviesApi(String query, int pageNumber) {
        mPostersListViewModel.retrieveMoviesApi(query, pageNumber);
    }

    @Override
    public void onMoviePosterClick(int position) {
        Intent intent = new Intent(this, MovieActivity.class);
        intent.putExtra("movie", mAdapter.getSelectedMovie(position));
        startActivity(intent);
    }
}
