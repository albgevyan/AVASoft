import java.sql.*;

public class Bank{

  public static void main(String[] args) throws Exception, java.lang.ClassNotFoundException {

    System.out.println("hello");
    String dbURL = "jdbc:oracle:thin:system/12345@//localhost:1521/xe";
    String username = "system";
    String password = "0130032023@O%d*B";
    Connection conn = DriverManager.getConnection(dbURL);

    if (conn != null) {
      System.out.println("Connected");
    }

    String sql = "INSERT INTO USERS (id, name, surname) VALUES (3, 'Vardan', 'Mkrtchyan')";
    Statement st = conn.createStatement();

    st.executeQuery(sql);
  }
}
