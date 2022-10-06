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
                    "SELECT * from movies");

        }
        else {
            System.out.println("no connection");
        }
    }
}
