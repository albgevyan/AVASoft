import java.sql.SQLException;

public class Main{
    public static void main(String[] args) throws SQLException {
        String dbURL = "jdbc:oracle:thin:system/12345@//192.168.18.13:1521:xe";

        DatabaseManipulator dbm = new DatabaseManipulator(dbURL);

//        if (dbm != null){
//            System.out.println("Connected Successfully.");
//        }
    }
}