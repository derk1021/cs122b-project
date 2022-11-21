package edu.uci.ics.fabflixmobile.data.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MovieResponse implements Serializable {

	@SerializedName("MovieResponse")
	private List<MovieResponseItem> movieResponse;

	public List<MovieResponseItem> getMovieResponse(){
		return movieResponse;
	}
}