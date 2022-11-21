package edu.uci.ics.fabflixmobile.ui.movielist;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import edu.uci.ics.fabflixmobile.data.model.MovieResponse;
import edu.uci.ics.fabflixmobile.data.model.MovieResponseItem;
import edu.uci.ics.fabflixmobile.databinding.ActivityMovielistBinding;

public class MovieListActivity extends AppCompatActivity {
    private ActivityMovielistBinding binding;
    private final int NUM_OF_ITEMS_PER_PAGE = 20;
    private int TOTAL_LIST_ITEMS = 241;
    private int pageCount, increment = 0;
    private List<MovieResponseItem> movies, currentPageMovies;
    private String movieResponse;
    private String sampleMovieResponse = "{ \"MovieResponse\" : [\n    {\n        \"id\": \"tt0381957\",\n        \"title\": \"Cloud Cuckoo Land\",\n        \"year\": 2012,\n        \"director\": \"Matt Dickinson\",\n        \"rating\": 7.6,\n        \"genres\": [\n            {\n                \"id\": 9,\n                \"name\": \"Drama\"\n            }\n        ],\n        \"stars\": [\n            {\n                \"id\": \"nm1458050\",\n                \"name\": \"Chris Bradley\",\n                \"birthYear\": 0\n            },\n            {\n                \"id\": \"nm1465507\",\n                \"name\": \"Ed Poole\",\n                \"birthYear\": 0\n            },\n            {\n                \"id\": \"nm1458371\",\n                \"name\": \"Matt Dickinson\",\n                \"birthYear\": 0\n            },\n            {\n                \"id\": \"nm1462425\",\n                \"name\": \"Sarah Beauvoisin\",\n                \"birthYear\": 0\n            },\n            {\n                \"id\": \"nm0001394\",\n                \"name\": \"Derek Jacobi\",\n                \"birthYear\": 1938\n            },\n            {\n                \"id\": \"nm1462526\",\n                \"name\": \"Rosalind Blessed\",\n                \"birthYear\": 0\n            },\n            {\n                \"id\": \"nm1108038\",\n                \"name\": \"Andy White\",\n                \"birthYear\": 0\n            },\n            {\n                \"id\": \"nm1464923\",\n                \"name\": \"Artic Melvin\",\n                \"birthYear\": 0\n            },\n            {\n                \"id\": \"nm1464527\",\n                \"name\": \"Andrew Lagowski\",\n                \"birthYear\": 0\n            },\n            {\n                \"id\": \"nm1461088\",\n                \"name\": \"Steve Varden\",\n                \"birthYear\": 0\n            }\n        ]\n    },\n    {\n        \"id\": \"tt0431021\",\n        \"title\": \"The Possession\",\n        \"year\": 2012,\n        \"director\": \"Ole Bornedal\",\n        \"rating\": 5.9,\n        \"genres\": [\n            {\n                \"id\": 21,\n                \"name\": \"Thriller\"\n            },\n            {\n                \"id\": 13,\n                \"name\": \"Horror\"\n            }\n        ],\n        \"stars\": [\n            {\n                \"id\": \"nm0925482\",\n                \"name\": \"Stiles White\",\n                \"birthYear\": 0\n            },\n            {\n                \"id\": \"nm0001718\",\n                \"name\": \"Kyra Sedgwick\",\n                \"birthYear\": 1965\n            },\n            {\n                \"id\": \"nm2828435\",\n                \"name\": \"Natasha Calis\",\n                \"birthYear\": 1999\n            },\n            {\n                \"id\": \"nm1340000\",\n                \"name\": \"Juliet Snowden\",\n                \"birthYear\": 0\n            },\n            {\n                \"id\": \"nm0849964\",\n                \"name\": \"Rob Tapert\",\n                \"birthYear\": 1955\n            },\n            {\n                \"id\": \"nm0097079\",\n                \"name\": \"Ole Bornedal\",\n                \"birthYear\": 1959\n            },\n            {\n                \"id\": \"nm1757236\",\n                \"name\": \"Leslie Gornstein\",\n                \"birthYear\": 0\n            },\n            {\n                \"id\": \"nm1956478\",\n                \"name\": \"Madison Davenport\",\n                \"birthYear\": 1996\n            },\n            {\n                \"id\": \"nm0604742\",\n                \"name\": \"Jeffrey Dean Morgan\",\n                \"birthYear\": 1966\n            },\n            {\n                \"id\": \"nm0000600\",\n                \"name\": \"Sam Raimi\",\n                \"birthYear\": 1959\n            }\n        ]\n    }\n]\n}";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovielistBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        binding.prevMb.setEnabled(false);

        movieResponse = "{ \"MovieResponse\" : " + getIntent().getExtras().getString("movieResponse") + "\n}";

        MovieResponse movieResponse = new Gson().fromJson(this.movieResponse, MovieResponse.class);

        movies = movieResponse.getMovieResponse();

        TOTAL_LIST_ITEMS = movies.size();

        System.out.println(movies.size());

        /**
         * this block is for checking the number of pages
         * ====================================================
         */

        int val = TOTAL_LIST_ITEMS%NUM_OF_ITEMS_PER_PAGE;
        val = val==0?0:1;
        pageCount = TOTAL_LIST_ITEMS/NUM_OF_ITEMS_PER_PAGE+val;
        /**
         * =====================================================
         */

        /**
         * The ArrayList data contains all the list items
        for(int i=0;i<TOTAL_LIST_ITEMS;i++)
        {
            movies.add(new Movie("The Terminal", (short) 2004));
        }
         */

        if(pageCount==1){
            binding.nextMb.setEnabled(false);
            binding.prevMb.setEnabled(false);
        }

        loadList(0);

        binding.nextMb.setOnClickListener(v -> {
            increment++;
            loadList(increment);
            CheckEnable();
        });

        binding.prevMb.setOnClickListener(v -> {
            increment--;
            loadList(increment);
            CheckEnable();
        });

        binding.list.setOnItemClickListener((parent, view, position, id) -> {
            MovieResponseItem movie = currentPageMovies.get(position);
//            @SuppressLint("DefaultLocale") String message = String.format("Clicked on position: %d, name: %s, %d", position, movie.getTitle(), movie.getYear());
//            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            Intent moviePageIntent = new Intent(MovieListActivity.this, MoviePageActivity.class);
            moviePageIntent.putExtra("movie", movie);
            startActivity(moviePageIntent);
        });
    }

    /**
     * Method for enabling and disabling Buttons
     */
    private void CheckEnable()
    {
        if(increment+1 == pageCount)
        {
            binding.nextMb.setEnabled(false);
            binding.prevMb.setEnabled(true);
        }
        else if(increment == 0)
        {
            binding.prevMb.setEnabled(false);
            binding.nextMb.setEnabled(true);
        }
        else
        {
            binding.prevMb.setEnabled(true);
            binding.nextMb.setEnabled(true);
        }
    }

    /**
     * Method for loading data in listview
     * @param number
     */
    private void loadList(int number)
    {
        currentPageMovies = new ArrayList<MovieResponseItem>();
        binding.pageTv.setText("Page "+(number+1)+" of "+pageCount);

        int start = number * NUM_OF_ITEMS_PER_PAGE;
        for(int i=start;i<(start)+NUM_OF_ITEMS_PER_PAGE;i++)
        {
            if(i<movies.size())
            {
                currentPageMovies.add(movies.get(i));
            }
            else
            {
                break;
            }
        }
        MovieListViewAdapter adapter = new MovieListViewAdapter(this, currentPageMovies);
        binding.list.setAdapter(adapter);
    }
}