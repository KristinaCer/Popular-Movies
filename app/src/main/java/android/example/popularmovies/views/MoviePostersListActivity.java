package android.example.popularmovies.views;

import android.content.Context;
import android.example.popularmovies.R;
import android.example.popularmovies.adapters.PopularMoviesAdapter;
import android.example.popularmovies.models.Movie;
import android.example.popularmovies.viewmodels.MoviePostersListViewModel;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MoviePostersListActivity extends AppCompatActivity {

    private PopularMoviesAdapter mAdapter;
    private RecyclerView mMoviesList;
    private static final String TAG = "MoviePostersListActivity";
    private MoviePostersListViewModel mPostersListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMoviesList = (RecyclerView) findViewById(R.id.rv_movie_posters);
        initRecyclerView();
        mPostersListViewModel = ViewModelProviders.of(this).get(MoviePostersListViewModel.class);
        addObservers();
        testRetroFitRequest();
    }

    private void initRecyclerView(){
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mMoviesList.setLayoutManager(layoutManager);
        mAdapter = new PopularMoviesAdapter();
        mMoviesList.setAdapter(mAdapter);
        mMoviesList.setHasFixedSize(true);
    }

    private void addObservers() {
        mPostersListViewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                if(movies != null) {
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
              testRetroFitRequest();
        }
        return true;
    }

    private void getMoviesApi( String query, int pageNumber){
        mPostersListViewModel.retrieveMoviesApi(query, pageNumber);
    }

    private void testRetroFitRequest() {
        this.getMoviesApi("popular", 1);
    }
}
