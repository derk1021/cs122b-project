import java.io.IOException;
import java.sql.SQLException;

public class Main {
  public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
    Parser spe = new Parser();
    spe.run();
    starParser starpe = new starParser();
    starpe.run();
  }
}
