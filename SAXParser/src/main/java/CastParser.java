

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class CastParser extends DefaultHandler {

	private HashMap<String, String> movieIdMap;
	private HashMap<String, String> starIdMap;

	private String tempVal;

	private Integer initStarId;

	private List<StarsInMovie> starInMovies;
	private StarsInMovie tempstarInMovies;
	String loginUser = "root";
	String loginPasswd = "Utbjea%3";
	String loginUrl = "jdbc:mysql://localhost:3306/moviedb";


	public CastParser() throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {

		Class.forName("com.mysql.jdbc.Driver").newInstance();
		Connection connection = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);

		PreparedStatement statement = connection.prepareStatement("select max(substring(id, 3)) as id from stars");
		ResultSet r = statement.executeQuery();
		r.next();

		initStarId = Integer.parseInt(r.getString("id"));

		starInMovies = new ArrayList<>();
	}

	public void run(HashMap<String, String> movieMap, HashMap<String, String> starMap) throws IOException {
		this.movieIdMap = movieMap;
		this.starIdMap = starMap;
		// THE ORDER OF THIS EXECUTION MATTERS!
		parseDocument();
		writeStarInMoviesFile();
		printData();
	}

	private void writeStarInMoviesFile() throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("stars_in_movies.txt", "UTF-8");

		Iterator<StarsInMovie> it = starInMovies.iterator();
		while (it.hasNext()) {
			StarsInMovie s = it.next();
			writer.printf("%s,%s\n", s.getStarId(), s.getMovieId());
		}
		writer.close();
	}

	private void parseDocument() {
		// get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {

			// get a new instance of parser
			SAXParser sp = spf.newSAXParser();

			// parse the file and also register this class for call backs
			sp.parse(new File("casts124.xml"), this);

		} catch (SAXException se) {
			se.printStackTrace();
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}

	/**
	 * Iterate through the list and print the contents
	 */
	private void printData() {
		System.out.println("No of Stars In Movies '" + starInMovies.size() + "'.");
	}

	// Event Handlers
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		// reset
		tempVal = "";
		if (qName.equalsIgnoreCase("m")) { // Its a movie
			// create a new instance of employee
			tempstarInMovies = new StarsInMovie();
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		tempVal = new String(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equalsIgnoreCase("m")) {
			if (tempstarInMovies.getStarId() != null && tempstarInMovies.getMovieId() != null)
				starInMovies.add(tempstarInMovies);
		}
		if (qName.equalsIgnoreCase("f")) {
			// add it to the list
			 tempstarInMovies.setMovieId(movieIdMap.get(tempVal));
		}
		if (qName.equalsIgnoreCase("a")) {
			tempstarInMovies.setStarId(starIdMap.get(tempVal));
		}
		

	}

}