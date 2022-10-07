package backend.jdbc;

import java.sql.*;

public class jdbc implements Parameters {

    public static void main(String[] arg) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");

        Connection connection = DriverManager.getConnection("jdbc:" + Parameters.dbtype + ":///" + Parameters.dbname + "?autoReconnect=true&useSSL=false",
                Parameters.username, Parameters.password);

        if (connection != null) {
            System.out.println("Connection established!");
            System.out.println();

            Statement select = connection.createStatement();
            ResultSet result = select.executeQuery(
                    // movies
                    "select movies.title, movies.year, movies.director, genres.name, stars.name, ratings.rating\n" +
                            "from movies, genres, stars, ratings, stars_in_movies, genres_in_movies\n" +
                            "where movies.id in (SELECT * FROM (select movies.id as mid\n" +
                            "from movies, ratings\n" +
                            "where movies.id = ratings.movieId\n" +
                            "order by rating DESC\n" +
                            "LIMIT 20) as t)\n" +
                            "and movies.id = genres_in_movies.movieId and genres_in_movies.genreId = genres.id and stars_in_movies.starId = stars.id and stars_in_movies.movieId = movies.id and movies.id = ratings.movieId\n" +
                            "ORDER BY rating desc, movies.id");
            while (result.next()) {
                System.out.println("Title = " + result.getString("title"));
                System.out.println("Year = " + result.getString("year"));
                System.out.println("Director = " + result.getString("director"));
                System.out.println("Genre = " + result.getString("genres.name"));
                System.out.println("Star = " + result.getString("stars.name"));
                System.out.println("Rating = " + result.getString("rating"));
                System.out.println();
            }
        }
        else {
            System.out.println("no connection");
        }
    }
}
