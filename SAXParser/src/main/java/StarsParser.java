
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class StarsParser extends DefaultHandler {

    Set<Star> stars;

    private String tempVal;

    //to maintain context
    private Star tempStar;

    private Integer initStarId;

	private HashMap<String, String> starIdMap;

	String loginUser = "root";
	String loginPasswd = "Utbjea%3";
    String loginUrl = "jdbc:mysql://localhost:3306/moviedb";



    public StarsParser() throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection connection = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);

        PreparedStatement statement = connection.prepareStatement("select max(substring(id, 3)) as id from stars");
        ResultSet r = statement.executeQuery();
        r.next();

        initStarId = Integer.parseInt(r.getString("id"));
		starIdMap = new HashMap<>();
		r = connection.prepareStatement("SELECT id,name FROM stars").executeQuery();
		while (r.next()) {
			starIdMap.put(r.getString("name"), r.getString("id"));
		}

        stars = new HashSet<>();
    }

	public HashMap<String, String> run()
			throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        // THE ORDER OF THIS EXECUTION MATTERS!
        parseDocument();
        writeStarFile();
        printData();
		return starIdMap;
    }

	private void writeStarFile() throws FileNotFoundException, UnsupportedEncodingException, SQLException,
			InstantiationException, IllegalAccessException, ClassNotFoundException {

		Class.forName("com.mysql.jdbc.Driver").newInstance();
		Connection connection = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);

		PreparedStatement statement = connection.prepareStatement("insert into star values(?,?,?)");
		PreparedStatement statement2 = connection
				.prepareStatement("select count(*) from stars where name=? and birth_year=? ");
        PrintWriter writer = new PrintWriter("stars.txt", "UTF-8");

        Iterator<Star> it = stars.iterator();
        while(it.hasNext()){
            Star s = it.next();
			if (!starIdMap.containsKey(s.getName())) {
				++initStarId;
				s.setId("nm" + String.format("%07d", initStarId));
				starIdMap.put(s.getName(), s.getId());

				statement2.setString(1, s.getName());
				statement2.setInt(2, s.getYear());

				ResultSet r = statement2.executeQuery();
				r.next();
				if (Integer.parseInt(r.getString(1)) == 0) {
					writer.printf("%s,%s%s\n", s.getId(), s.getName(), (s.getYear() == 0 ? "" : "," + s.getYear()));
					statement.setString(1, s.getId());
					statement.setString(2, s.getName());
					statement.setInt(3, s.getYear());
					statement.executeUpdate();
				}
			}
        }
        writer.close();
    }

    private void parseDocument() {
        //get a factory
        SAXParserFactory spf = SAXParserFactory.newInstance();
        try {

            //get a new instance of parser
            SAXParser sp = spf.newSAXParser();

            //parse the file and also register this class for call backs
			sp.parse(new File("actors63.xml"), this);

        } catch (SAXException se) {
            se.printStackTrace();
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    /**
     * Iterate through the list and print
     * the contents
     */
    private void printData() {
		System.out.println("No of Stars '" + stars.size() + "'.");
    }

    //Event Handlers
    @Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        //reset
        tempVal = "";
        if (qName.equalsIgnoreCase("actor")) { // Its a movie
            //create a new instance of employee
            tempStar = new Star();
        }
    }

    @Override
	public void characters(char[] ch, int start, int length) throws SAXException {
        tempVal = new String(ch, start, length);
    }

    @Override
	public void endElement(String uri, String localName, String qName) throws SAXException {

        if (qName.equalsIgnoreCase("actor")) {
            //add it to the list
            stars.add(tempStar);
        }
        if (qName.equalsIgnoreCase("stagename")) {
            tempStar.setName(tempVal);
        }
        if (qName.equalsIgnoreCase("dob")) {
            //add it to the list
            try{
                tempStar.setYear(Integer.parseInt(tempVal));
            }
            catch (Exception e){
                tempStar.setYear(0);
            }
        }


    }

}
