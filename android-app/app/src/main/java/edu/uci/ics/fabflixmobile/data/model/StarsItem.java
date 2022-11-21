package edu.uci.ics.fabflixmobile.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StarsItem implements Serializable {

	@SerializedName("birthYear")
	private int birthYear;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private String id;

	public int getBirthYear(){
		return birthYear;
	}

	public String getName(){
		return name;
	}

	public String getId(){
		return id;
	}
}