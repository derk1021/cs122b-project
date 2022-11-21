package edu.uci.ics.fabflixmobile.data.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MovieResponseItem implements Serializable {

	@SerializedName("year")
	private int year;

	@SerializedName("director")
	private String director;

	@SerializedName("genres")
	private List<GenresItem> genres;

	@SerializedName("rating")
	private double rating;

	@SerializedName("id")
	private String id;

	@SerializedName("stars")
	private List<StarsItem> stars;

	@SerializedName("title")
	private String title;

	public int getYear(){
		return year;
	}

	public String getDirector(){
		return director;
	}

	public List<GenresItem> getGenres(){
		return genres;
	}

	public double getRating(){
		return rating;
	}

	public String getId(){
		return id;
	}

	public List<StarsItem> getStars(){
		return stars;
	}

	public String getTitle(){
		return title;
	}
}