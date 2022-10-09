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
                    "SELECT m.id, m.title, m.year, m.director, r.rating, g.name, s.name, s.id from stars as s, stars_in_movies as sim, movies as m, genres as g, genres_in_movies as gim, ratings as r " +
                            "where m.id = sim.movieId and sim.starId = s.id and m.id = gim.movieId and g.id = gim.genreId and r.movieId = m.id and m.id = 'tt0395642'");
            while (result.next()) {
                System.out.println("Title = " + result.getString("title"));
                System.out.println("Year = " + result.getString("year"));
                System.out.println("Director = " + result.getString("director"));
                System.out.println("Genre = " + result.getString("g.name"));
                System.out.println("Star Name = " + result.getString("s.name"));
                System.out.println("Name = " + result.getString("rating"));
                System.out.println();
            }
        }
        else {
            System.out.println("no connection");
        }
    }
}
