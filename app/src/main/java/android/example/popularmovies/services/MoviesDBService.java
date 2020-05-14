package android.example.popularmovies.services;

import android.example.popularmovies.utils.Constants;
import android.util.Log;;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesDBService {

    private MoviesDBApi moviesDBApi;
    private static MoviesDBService moviesDBService;
    private static final String TAG = "MoviesDBService";

    /*The object is initiated only once - if it was not yet created.
    If the object was already created, the instance of the created object is returned.
    Since we create the movies DB API by using Retrofit instance withing the private MovieDBService constructor,
    it is also created only once.*/

    public static MoviesDBService getMoviesDBService() {
        if (moviesDBService == null) {
            return moviesDBService = new MoviesDBService();
        } else {
            return moviesDBService;
        }
    }

    public MoviesDBApi getMoviesDBApi() {
        return moviesDBApi;
    }

    //Keeping Service constructor private, allowing object initiation through getter method only once.
    private MoviesDBService() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        moviesDBApi = retrofit.create(MoviesDBApi.class);

        Log.d(TAG, "MovieDBApi created using " + Constants.BASE_URL);
    }
}

