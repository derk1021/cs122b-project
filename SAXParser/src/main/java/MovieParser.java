
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MovieParser extends DefaultHandler {

    List<Movie> movies;

    private String tempVal;

    //to maintain context
    private Movie tempMovie;

    private String tempDirector;

    private Genre tempGenre;

	private Integer initMovieId;

    private Integer initGenreId;

    private HashMap<String,Integer> genreIdMap;

	public static HashMap<String, String> movieToDbMovie;

	String loginUser = "root";
	String loginPasswd = "Utbjea%3";
	String loginUrl = "jdbc:mysql://localhost:3306/moviedb";

	public HashMap<String, String> run()
			throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		// THE ORDER OF THIS EXECUTION MATTERS!
		parseDocument();
		writeMovieFile();
		writeGenreFile();
		updateGenreIdInMovies();
		writeGenreInMoviesFile();
		printData();
		
		//return key map for casts
		return movieToDbMovie;
	}


    public MovieParser() throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {


        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection connection = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);

        PreparedStatement statement = connection.prepareStatement("select max(substring(id, 3)) as id from movies");
        ResultSet r = statement.executeQuery();
        r.next();

		initMovieId = Integer.parseInt(r.getString("id"));
        r = connection.prepareStatement("SELECT MAX(id) as id FROM genres").executeQuery();
        r.next();
        initGenreId = Integer.parseInt(r.getString("id"));

		// creating a Map for Genres
        genreIdMap = new HashMap<>();
        r = connection.prepareStatement("SELECT id,name FROM genres").executeQuery();
        while(r.next()){
            genreIdMap.put(r.getString("name"), Integer.parseInt(r.getString("id")));
        }

        movieToDbMovie = new HashMap<>();

        movies = new ArrayList<Movie>();
    }

	private void parseDocument() {
		// get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {

			// get a new instance of parser
			SAXParser sp = spf.newSAXParser();

			// parse the file and also register this class for call backs
			sp.parse(new File("mains243.xml"), this);

		} catch (SAXException se) {
			se.printStackTrace();
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}
	
	 //Event Handlers
    @Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        //reset
        tempVal = "";
        if (qName.equalsIgnoreCase("film")) { // Its a movie
            //create a new instance of employee
            tempMovie = new Movie();
        }
        if (qName.equalsIgnoreCase("cat")) { // Its a genre
            tempGenre = new Genre();
        }
    }

    @Override
	public void characters(char[] ch, int start, int length) throws SAXException {
        tempVal = new String(ch, start, length);
    }

    @Override
	public void endElement(String uri, String localName, String qName) throws SAXException {

        if (qName.equalsIgnoreCase("directorfilms")) { // Movies under this director is done looking at..
            tempDirector = null;
        }
        if (qName.equalsIgnoreCase("dirname")) { // Movies under this director is done looking at..
            tempDirector = tempVal;
        }
        if (qName.equalsIgnoreCase("cat")) {
            if(!tempVal.equalsIgnoreCase("ctxx")){
                String [] splits = tempVal.split("[, ?.@-]+");
                for(String s : splits){
                    tempGenre.setName(s.toLowerCase());
                    tempMovie.addGenre(tempGenre);
                    tempGenre = new Genre();
                }
            }
        }
        if (qName.equalsIgnoreCase("film")) {
            //add it to the list
            tempMovie.setDirector(tempDirector);
            movies.add(tempMovie);
        }
        if (qName.equalsIgnoreCase("fid")) {
            tempMovie.setId(tempVal);
        }
		if (qName.equalsIgnoreCase("dirn")) {
			if (tempMovie.getDirector() == null) {
				tempMovie.setDirector(tempVal);
			}
		}
		if (qName.equalsIgnoreCase("t")) {
            //add it to the list
			tempVal.replace("~", "");
			tempVal.replace("`", "");
			tempVal = tempVal.replace("'", "");
            tempMovie.setTitle(tempVal);
        }
        if (qName.equalsIgnoreCase("year")) {
            //add it to the list
            try{
                tempMovie.setYear(Integer.parseInt(tempVal));
            }
            catch (Exception e){
				System.out.println("Year is not in correct format : " + tempVal);
                tempMovie.setYear(0);
            }
        }
    }

	private void updateGenreIdInMovies() {
        Iterator<Movie> it = movies.iterator();
        while(it.hasNext()){
            Iterator<Genre> gt = it.next().getGenreList().iterator();
            while (gt.hasNext()){
                Genre g = gt.next();
                g.setId(genreIdMap.get(g.getName()));
            }
        }
    }

	private void writeMovieFile() throws IOException, SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
    	Class.forName("com.mysql.jdbc.Driver").newInstance();
		Connection connection = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);

		PreparedStatement statement = connection.prepareStatement("insert into movies values(?,?,?,?,0)");
		PrintWriter writer = new PrintWriter("movies.txt", "UTF-8");
        Iterator<Movie> it = movies.iterator();
        while (it.hasNext()) {
            Movie m =it.next();
            String xmlId = m.getId();
			++initMovieId;
			m.setId(String.format("tt%07d", initMovieId));
            m.setXmlId(xmlId);
			if (m.getDirector() == null) {
				m.setDirector("");
			}
			movieToDbMovie.put(m.getXmlId(), m.getId());
			writer.printf("%s,%s,%d,%s\n", m.getId(), m.getTitle(), m.getYear(),
					m.getDirector());
			
			statement.setString(1, m.getId());
			statement.setString(2, m.getTitle());
			statement.setInt(3, m.getYear());
			statement.setString(4, m.getDirector());
//			statement.executeUpdate();
        }
        writer.close();

    }

	private void writeGenreInMoviesFile() throws FileNotFoundException, UnsupportedEncodingException, SQLException,
			InstantiationException, IllegalAccessException, ClassNotFoundException {
        PrintWriter writer = new PrintWriter("genres_in_movies.txt", "UTF-8");
        PrintWriter badFile = new PrintWriter("inconsistentGenreInMovies.md", "UTF-8");

		Class.forName("com.mysql.jdbc.Driver").newInstance();
		Connection connection = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);

		PreparedStatement statement = connection.prepareStatement("insert into genres_in_movies values(?,?)");

        Iterator<Movie> it = movies.iterator();
        while(it.hasNext()){
            Movie m = it.next();
            Iterator<Genre> gt = m.getGenreList().iterator();
            while (gt.hasNext()){
                Genre g = gt.next();
				if (g.getId() != null) {
                	statement.setInt(1, g.getId());
                	statement.setString(2, m.getId());
//					statement.executeUpdate();
                    writer.printf("%d,%s\n",g.getId(),m.getId());
				}
                else{
                    badFile.printf("- Genre %s not found", g.getName());
                }
            }
        }
        writer.close();
        badFile.close();

    }

	private void writeGenreFile()
			throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        PrintWriter writer = new PrintWriter("genres.txt", "UTF-8");
        Set<Genre> hash_Set = new HashSet<Genre>();
        PrintWriter badFile = new PrintWriter("inconsistentGenres.md", "UTF-8");

        Iterator<Movie> it = movies.iterator();
        while (it.hasNext()) {
            Movie m =it.next();
            Iterator<Genre> gt = m.getGenreList().iterator();
            while(gt.hasNext()){
                Genre g = gt.next();
                if(!g.getWrong()) {
                        hash_Set.add(g); // Gives us all "unique" genres
                }
                else{
                    badFile.printf("- Genre Name: %s\n", g.getName());
                }
            }
        }
        Class.forName("com.mysql.jdbc.Driver").newInstance();
		Connection connection = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);

		PreparedStatement statement = connection.prepareStatement("insert into genres values(?,?)");
		
        Iterator<Genre> i = hash_Set.iterator();
        while (i.hasNext()) {
            // Add to output only if its not already in the database AKA only create a genre thats not already there.
            Genre g = i.next();
            if(!genreIdMap.containsKey(g.getName())){
                ++initGenreId;
                g.setId(initGenreId);
                genreIdMap.put(g.getName(), g.getId());
                writer.printf("%s,%s\n", g.getId(), g.getName());
				statement.setInt(1, g.getId());
				statement.setString(2, g.getName());
//				int r = statement.executeUpdate();
            }
        }
        writer.close();
        badFile.close();
    }


    /**
     * Iterate through the list and print
     * the contents
     */
    private void printData() {
        System.out.println("No of Movies '" + movies.size() + "'.");
    }

}
