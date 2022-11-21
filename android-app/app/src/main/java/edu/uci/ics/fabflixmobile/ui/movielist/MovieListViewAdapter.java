package edu.uci.ics.fabflixmobile.ui.movielist;

import edu.uci.ics.fabflixmobile.R;
import edu.uci.ics.fabflixmobile.data.model.GenresItem;
import edu.uci.ics.fabflixmobile.data.model.Movie;
import edu.uci.ics.fabflixmobile.data.model.MovieResponseItem;
import edu.uci.ics.fabflixmobile.data.model.StarsItem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MovieListViewAdapter extends ArrayAdapter<MovieResponseItem> {
    private final List<MovieResponseItem> movies;

    // View lookup cache
    private static class ViewHolder {
        TextView title, year, director, genres, stars;
    }

    public MovieListViewAdapter(Context context, List<MovieResponseItem> movies) {
        super(context, R.layout.movielist_row, movies);
        this.movies = movies;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the movie item for this position
        MovieResponseItem movie = movies.get(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.movielist_row, parent, false);
            viewHolder.title = convertView.findViewById(R.id.titleTv);
            viewHolder.year = convertView.findViewById(R.id.yearTv);
            viewHolder.director = convertView.findViewById(R.id.directorTv);
            viewHolder.genres = convertView.findViewById(R.id.genresTv);
            viewHolder.stars = convertView.findViewById(R.id.starsTv);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data from the data object via the viewHolder object
        // into the template view.
        viewHolder.title.setText((position+1) +". " +movie.getTitle());
        viewHolder.year.setText(movie.getYear() + "");
        viewHolder.director.setText(movie.getDirector());
        setGenres(movie,viewHolder);
        setStars(movie,viewHolder);
        // Return the completed view to render on screen
        return convertView;
    }

    private void setGenres(MovieResponseItem movie, ViewHolder viewHolder){
        List<GenresItem> genresItemList = movie.getGenres();
        int genreCount = genresItemList.size();
        if(genreCount>=3){
            String genres = genresItemList.get(0).getName() + ", " + genresItemList.get(1).getName() + ", " + genresItemList.get(2).getName();
            viewHolder.genres.setText(genres);
        }
        else if(genreCount==2){
            String genres = genresItemList.get(0).getName() + ", " + genresItemList.get(1).getName();
            viewHolder.genres.setText(genres);
        }
        else if(genreCount==1){
            String genres = genresItemList.get(0).getName();
            viewHolder.genres.setText(genres);
        }
    }

    private void setStars(MovieResponseItem movie, ViewHolder viewHolder){
        List<StarsItem> starsItemList = movie.getStars();
        int starCount = starsItemList.size();
        if(starCount>=3){
            String genres = starsItemList.get(0).getName() + ", " + starsItemList.get(1).getName() + ", " + starsItemList.get(2).getName();
            viewHolder.stars.setText(genres);
        }
        else if(starCount==2){
            String genres = starsItemList.get(0).getName() + ", " + starsItemList.get(1).getName();
            viewHolder.stars.setText(genres);
        }
        else if(starCount==1){
            String genres = starsItemList.get(0).getName();
            viewHolder.stars.setText(genres);
        }
    }
}