package android.example.popularmovies.views;

import android.example.popularmovies.R;
import android.example.popularmovies.models.Movie;
import android.example.popularmovies.utils.Constants;
import android.os.Bundle;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class MovieActivity extends AppCompatActivity {

    private AppCompatImageView mMovieImage;
    private TextView mMovieTitle, mReleaseDate, mVoteAverage, mPlotSynopsis;
    private static final String TAG = "MovieActivity";
    private ScrollView mActivityDetails;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        this.mMovieImage = findViewById(R.id.movie_image);
        this.mMovieTitle = findViewById(R.id.movie_title);
        this.mReleaseDate = findViewById(R.id.release_date);
        this.mVoteAverage = findViewById(R.id.vote_average);
        this.mPlotSynopsis = findViewById(R.id.plot_synopsis);
        this.mActivityDetails = findViewById(R.id.sw_movie_details);
        getIncomingIntent();
    }

    private void getIncomingIntent() {
        Log.d(TAG, " getIncomingIntent() method started");
        if (getIntent().hasExtra("movie")) {
            Movie movie = getIntent().getParcelableExtra("movie");
            Log.d(TAG, "Intent works: " + movie.getTitle());
            setMovieProperties(movie);

        }
    }

    private void setMovieProperties(Movie movie){
        if(movie != null){
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background);
            Glide.with(this)
                    .setDefaultRequestOptions(requestOptions)
                    .load(Constants.BASE_URL_F0R_IMG + movie.getPosterPath())
                    .into(mMovieImage);
                    mMovieTitle.setText(movie.getTitle());
                    mVoteAverage.setText(String.valueOf(movie.getVoteAverage()));
                    mReleaseDate.setText(String.valueOf(movie.getReleaseDate()));
                    mPlotSynopsis.setText(String.valueOf(movie.getOverview()));
        }
    }
}
