//import com.sun.tools.jdeprscan.scan.Scan;

//import com.sun.org.apache.xalan.internal.xsltc.runtime.Parameter;

import java.sql.SQLException;
import java.util.Scanner;

public class Main{
    private static Scanner input = new Scanner(System.in);
    public static void main(String[] args) throws Exception {
        DatabaseManipulator dbm = createDatabaseManipulator();

//        String[][] parameters = new String[3][3];
//        for (int i = 0; i < 3; parameters[i][1] = "VARCHAR2", parameters[i][2] = "10", i++) ;
//        parameters[0][0] = "VARONE";
//        parameters[1][0] = "VARTWO";
//        parameters[2][0] = "VARTHREE";
//        String command = input.nextLine();
//        Parameter[] params = new Parameter[3];
//        for (int i = 0; i < params.length; i++) {
//            params[i] = new Parameter("column_" + ('0' + i), "varchar2", 10 * (i + 1));
//        }
//        dbm.createTable("table_creator", params);
//        DataContainer row = new DataContainer("someval1", "someval2", "someval3");
//        dbm.insertRow("table_creator", row);

    }

    private static DatabaseManipulator createDatabaseManipulator() throws SQLException {

        // REMOVE THE COMMENTS

//        System.out.print("Enter the database username:");
//        String username = input.nextLine();
//
//        System.out.print("Enter the database password:");
//        String password = input.nextLine();
//
//        String dbURL = "jdbc:oracle:thin:" + username + '/' + password + "@//localhost:1521/xe";

        String dbURL = "jdbc:oracle:thin:system/12345@//localhost:1521/xe";

        return new DatabaseManipulator(dbURL);
    }
}
