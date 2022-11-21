package edu.uci.ics.fabflixmobile.ui.movielist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.uci.ics.fabflixmobile.R;
import edu.uci.ics.fabflixmobile.data.NetworkManager;
import edu.uci.ics.fabflixmobile.data.PersistentCookieStore;
import edu.uci.ics.fabflixmobile.data.model.MovieResponse;
import edu.uci.ics.fabflixmobile.databinding.ActivityMainBinding;
import edu.uci.ics.fabflixmobile.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private final static String PREFS_NAME = PersistentCookieStore.class.getName();
    private final String host = "13.56.8.234";
    private final String port = "8080";
    private final String domain = "backend";
    private final String baseURL = "http://" + host + ":" + port + "/" + domain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        binding.mainSv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                binding.pgb.setVisibility(View.VISIBLE);
                getMovies(query);
                binding.mainSv.clearFocus();
                binding.mainSv.setQuery("",false);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        binding.logOutMb.setOnClickListener(view -> {
            SharedPreferences.Editor editor = getPrefs().edit();
            editor.clear();
            editor.apply();
            finish();
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        });
    }

    private void getMovies(String query) {
        final RequestQueue queue = NetworkManager.sharedManager(MainActivity.this).queue;
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, baseURL + "/api/movie?title=" + query + "&year=0&director=null&star=null", null, response -> {
            binding.pgb.setVisibility(View.GONE);
            Log.i("movie.response", response.toString());
            if(response.length()!=0) {
                Intent movieListIntent = new Intent(MainActivity.this, MovieListActivity.class);
                movieListIntent.putExtra("movieResponse", response.toString());
                startActivity(movieListIntent);
            }
            else Toast.makeText(this, "No movies found !", Toast.LENGTH_SHORT).show();
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("movie.error", error.getMessage());
                binding.pgb.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Unable to fetch data, please check your Internet", Toast.LENGTH_LONG).show();
            }
        });
        queue.add(jsonArrayRequest);
    }

    private SharedPreferences getPrefs() {
        return MainActivity.this.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
}