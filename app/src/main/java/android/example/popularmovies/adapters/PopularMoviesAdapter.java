package android.example.popularmovies.adapters;

import android.content.Context;
import android.example.popularmovies.R;
import android.example.popularmovies.models.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.util.List;

/*
https://www.reddit.com/r/androiddev/comments/8aabvb/picasso_vs_glide/ for why I chose to develop using Glide instead of Picasso.
 */

public class PopularMoviesAdapter extends RecyclerView.Adapter<PopularMoviesAdapter.PopularMoviesViewHolder> {

    private List<Movie> mMovies;

    @NonNull
    @Override
    public PopularMoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutFromListItem = R.layout.movie_posters_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        /*We need to provide the ViewGroup as item layout is associated with a particular view group,
         as described in the stack overflow post below:
         https://stackoverflow.com/questions/41781636/why-we-need-viewgroup-parameter-on-recyclerview: */

        View view = inflater.inflate(layoutFromListItem, parent, shouldAttachToParentImmediately);
        PopularMoviesViewHolder viewHolder = new PopularMoviesViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PopularMoviesViewHolder holder, int position) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);
        Glide.with(holder.itemView.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(new File(mMovies.get(position).getPosterPath()))
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

    //The logic of the class is to cash references to the views that will be then modified in the adapter
    // as calling findViewById() for every new item in the view would get too expensive:

    class PopularMoviesViewHolder extends RecyclerView.ViewHolder {
        ImageView poster;
        private Context context;

        public PopularMoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            poster = (AppCompatImageView) itemView.findViewById(R.id.iv_item_poster);
            itemView.setOnContextClickListener(new View.OnContextClickListener() {
                @Override
                public boolean onContextClick(View v) {
                    getAdapterPosition();
                    return true;
                }
            });
        }
    }
}
