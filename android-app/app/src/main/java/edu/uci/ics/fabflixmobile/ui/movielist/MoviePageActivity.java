package edu.uci.ics.fabflixmobile.ui.movielist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import java.util.List;

import edu.uci.ics.fabflixmobile.R;
import edu.uci.ics.fabflixmobile.data.model.GenresItem;
import edu.uci.ics.fabflixmobile.data.model.MovieResponseItem;
import edu.uci.ics.fabflixmobile.data.model.StarsItem;
import edu.uci.ics.fabflixmobile.databinding.ActivityMoviePageBinding;

public class MoviePageActivity extends AppCompatActivity {
    private ActivityMoviePageBinding binding;
    private MovieResponseItem movie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMoviePageBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        movie = (MovieResponseItem) getIntent().getSerializableExtra("movie");
        binding.titleTv.setText(movie.getTitle());
        binding.directorTv.setText(movie.getDirector());
        binding.yearTv.setText(movie.getYear()+"");
        setGenres();
        setStars();
    }

    private void setGenres(){
        List<GenresItem> genresItemList = movie.getGenres();
        int genreCount = genresItemList.size();
        StringBuilder genreSb = new StringBuilder();
        for(int i=0;i<genreCount;i++) {
            if (i == genreCount - 1) genreSb.append(genresItemList.get(i).getName());
            else genreSb.append(genresItemList.get(i).getName()).append(", ");
        }
        binding.genresTv.setText(genreSb.toString().trim());
    }

    private void setStars(){
        List<StarsItem> starsItemList = movie.getStars();
        int starCount = starsItemList.size();
        StringBuilder starSb = new StringBuilder();
        for(int i=0;i<starCount;i++) {
            if (i == starCount - 1) starSb.append(starsItemList.get(i).getName());
            else starSb.append(starsItemList.get(i).getName()).append(", ");
        }
        binding.starsTv.setText(starSb.toString().trim());
    }
}