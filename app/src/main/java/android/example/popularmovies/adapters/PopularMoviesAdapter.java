package android.example.popularmovies.adapters;

import android.content.Context;
import android.example.popularmovies.R;
import android.example.popularmovies.models.Movie;
import android.example.popularmovies.utils.Constants;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

/*
https://www.reddit.com/r/androiddev/comments/8aabvb/picasso_vs_glide/ for why I chose to develop using Glide instead of Picasso.
 */

public class PopularMoviesAdapter extends RecyclerView.Adapter<PopularMoviesAdapter.PopularMoviesViewHolder> {

    private List<Movie> mMovies;
    private Context context;
    private OnMoviePosterListener mListener;
    private static final String TAG = "PopularMoviesAdapter";

    public PopularMoviesAdapter(OnMoviePosterListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public PopularMoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutFromListItem = R.layout.layout_movie_posters_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        /*We need to provide the ViewGroup as item layout is associated with a particular view group,
         as described in the stack overflow post below:
         https://stackoverflow.com/questions/41781636/why-we-need-viewgroup-parameter-on-recyclerview: */

        View view = inflater.inflate(layoutFromListItem, parent, shouldAttachToParentImmediately);
        PopularMoviesViewHolder viewHolder = new PopularMoviesViewHolder(view, mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PopularMoviesViewHolder holder, int position) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);
        Glide.with(holder.itemView.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(Constants.BASE_URL_F0R_IMG + mMovies.get(position).getPosterPath())
                .into(holder.poster);

    }

    @Override
    public int getItemCount() {
        if (mMovies != null) {
            return mMovies.size();
        }
        return 0;
    }

    public void setMovies(List<Movie> movies) {
        mMovies = movies;
        notifyDataSetChanged();
    }

    public Movie getSelectedMovie(int position) {
        if (mMovies != null) {
            if (mMovies.size() > 0) {
                return mMovies.get(position);
            }
        }
        return null;
    }

    //The logic of the class is to cash references to the views that will be then modified in the adapter
    // as calling findViewById() for every new item in the view would get too expensive:

    class PopularMoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView poster;
        private OnMoviePosterListener listener;

        public PopularMoviesViewHolder(@NonNull View itemView, OnMoviePosterListener listener) {
            super(itemView);
            poster = (AppCompatImageView) itemView.findViewById(R.id.iv_item_poster);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "The item " + getAdapterPosition() + "was clicked");
            listener.onMoviePosterClick(getAdapterPosition());
        }
    }
}

