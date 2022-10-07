package backend.servlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@WebServlet(name = "StarsServlet", urlPatterns = "/api/stars")
public class StarServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String user = "";
        String password = "";
        String url = "jdbc:mysql://localhost:3306/moviedb";

        response.setContentType("text/html");

        // Get the PrintWriter for writing response
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head><title>Fabflix</title></head>");

        try {
            Class.forName("com.mysql.jdbc.Driver");

            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();

            String query = "SELECT * from movies limit 10";
            ResultSet resultSet = statement.executeQuery(query);

            out.println("<body>");
            out.println("<h1>MovieDB Stars</h1>");

            out.println("<table border>");

            // Add table header row
            out.println("<tr>");
            out.println("<td>id</td>");
            out.println("<td>name</td>");
            out.println("<td>birth year</td>");
            out.println("</tr>");

            // Add a row for every star result
            while (resultSet.next()) {
                // get a star from result set
                String starID = resultSet.getString("id");
                String starName = resultSet.getString("name");
                String birthYear = resultSet.getString("birthyear");

                out.println("<tr>");
                out.println("<td>" + starID + "</td>");
                out.println("<td>" + starName + "</td>");
                out.println("<td>" + birthYear + "</td>");
                out.println("</tr>");
            }

            connection.close();
        } catch (Exception e) {
            request.getServletContext().log("Error: ", e);

        }
    }
}

