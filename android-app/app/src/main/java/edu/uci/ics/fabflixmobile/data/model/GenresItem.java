package edu.uci.ics.fabflixmobile.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GenresItem implements Serializable {

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}
}