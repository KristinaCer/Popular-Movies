package android.example.popularmovies.adapters;

import android.content.Context;
import android.example.popularmovies.R;
import android.example.popularmovies.models.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PopularMoviesAdapter extends RecyclerView.Adapter<PopularMoviesAdapter.PopularMoviesViewHolder> {

    private final Movie[] movies;

    public PopularMoviesAdapter(Movie[] movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public PopularMoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutFromListItem = R.layout.movie_posters_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        //we also need to provide the ViewGroup since our item layout is associated with a particular view groups,
        // as described in the stack overflow post below:
        // https://stackoverflow.com/questions/41781636/why-we-need-viewgroup-parameter-on-recyclerview:
        View view = inflater.inflate(layoutFromListItem, parent, shouldAttachToParentImmediately);
        PopularMoviesViewHolder viewHolder = new PopularMoviesViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PopularMoviesViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return movies.length;
    }

    //The class cashes references to the views that will be modified in the adapter as findviewbyid() calls can get expensive:
    class PopularMoviesViewHolder extends RecyclerView.ViewHolder {
        private ImageView moviesImageView;
        private Context context;

        public PopularMoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            moviesImageView = (ImageView) itemView.findViewById(R.id.iv_item_poster);
        }

        public void bind(int listIndex) {
             moviesImageView.setImageResource(movies[listIndex].getResID());
        }
    }
}
