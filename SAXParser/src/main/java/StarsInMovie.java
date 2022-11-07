
public class StarsInMovie {
	private String starId;
	private String movieId;

	public String getStarId() {
		return starId;
	}

	public void setStarId(String starId) {
		this.starId = starId;
	}

	public String getMovieId() {
		return movieId;
	}

	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}

	@Override
	public String toString() {
		return "StarsInMovie [starId=" + starId + ", movieId=" + movieId + "]";
	}

}
