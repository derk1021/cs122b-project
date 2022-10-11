package backend.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


// Declaring a WebServlet called StarsServlet, which maps to url "/api/stars"
@WebServlet(name = "MoviesServlet", urlPatterns = "/api/movies")
public class MoviesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Create a dataSource which registered in web.
    private DataSource dataSource;

    public void init(ServletConfig config) {
        try {
            dataSource = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/moviedb");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("application/json"); // Response mime type

        // Output stream to STDOUT
        PrintWriter out = response.getWriter();
        // Get a connection from dataSource and let resource manager close the connection after usage.
        try (Connection conn = dataSource.getConnection()) {

            // Declare our statement
            Statement statement = conn.createStatement();

            String query = "select movies.id, movies.title, movies.year, movies.director, group_concat(genres.name), group_concat(stars.name), group_concat(stars.id), ratings.rating\n" +
                    "from movies, genres, stars, ratings, stars_in_movies, genres_in_movies\n" +
                    "where movies.id in (SELECT * FROM (select movies.id as mid\n" +
                    "from movies, ratings\n" +
                    "where movies.id = ratings.movieId\n" +
                    "order by rating DESC\n" +
                    "LIMIT 20) as t)\n" +
                    "and movies.id = genres_in_movies.movieId and genres_in_movies.genreId = genres.id and stars_in_movies.starId = stars.id and stars_in_movies.movieId = movies.id and movies.id = ratings.movieId\n" +
                    "group by movies.id\n" +
                    "ORDER BY rating desc, movies.id";

            // Perform the query
            ResultSet rs = statement.executeQuery(query);

            JsonArray jsonArray = new JsonArray();

            // Iterate through each row of rs
            while (rs.next()) {
                String movie_id = rs.getString("movies.id");
                String movie_title = rs.getString("title");
                String movie_year = rs.getString("year");
                String movie_dir = rs.getString("director");

                String star_id = rs.getString("group_concat(stars.id)");
                String star_name = rs.getString("group_concat(stars.name)");

                String genres_name = rs.getString("group_concat(genres.name)");

                String movie_rating = rs.getString("rating");

                // Create a JsonObject based on the data we retrieve from rs
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("movie_id", movie_id);
                jsonObject.addProperty("movie_title", movie_title);
                jsonObject.addProperty("movie_year", movie_year);
                jsonObject.addProperty("movie_dir", movie_dir);
                jsonObject.addProperty("movie_rating", movie_rating);
                jsonObject.addProperty("genres_name", genres_name);
                jsonObject.addProperty("star_id", star_id);
                jsonObject.addProperty("star_name", star_name);

                jsonArray.add(jsonObject);
            }
            rs.close();
            statement.close();

            // Log to localhost log
            request.getServletContext().log("getting " + jsonArray.size() + " results");

            // Write JSON string to output
            out.write(jsonArray.toString());
            // Set response status to 200 (OK)
            response.setStatus(200);

        } catch (Exception e) {

            // Write error message JSON object to output
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("errorMessage", e.getMessage());
            out.write(jsonObject.toString());

            // Set response status to 500 (Internal Server Error)
            response.setStatus(500);
        } finally {
            out.close();
        }

        // Always remember to close db connection after usage. Here it's done by try-with-resources

    }
}