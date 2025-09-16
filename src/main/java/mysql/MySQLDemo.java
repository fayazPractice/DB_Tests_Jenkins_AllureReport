
package mysql;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.testng.annotations.Test;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

public class MySQLDemo {
	@Epic("Database Tests")
	@Feature("Contact Email Extraction")
	@Story("Extract Kim Baxter's Email")
	@Test(description = "Should print Kim Baxter's email from the database")
	public void testRunQuery() {
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "system";
		String password = "system";
		String query = "SELECT * FROM CONTACTS";
		MySQLDemo.runQuery(url, user, password, query);
	}

public static void runQuery(String url, String user, String password, String query) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(baos);
    PrintStream oldOut = System.out;
    System.setOut(ps);

    try (Connection conn = DriverManager.getConnection(url, user, password);
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(query)) {

        int columnCount = rs.getMetaData().getColumnCount();
        while (rs.next()) {
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(rs.getString(i));
                if (i < columnCount)
                    System.out.print(", ");
            }
            System.out.println();
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        System.out.flush();
        System.setOut(oldOut);
        String output = baos.toString();
        Allure.addAttachment("Query Output", output);
    }
}


}
