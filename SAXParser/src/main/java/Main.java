import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

public class Main {
  public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
		MovieParser movieParser = new MovieParser();
		HashMap<String, String> movieMap = movieParser.run();
		StarsParser starParser = new StarsParser();
		HashMap<String, String> starMap = starParser.run();
		CastParser castParser = new CastParser();
		castParser.run(movieMap, starMap);
  }
}
