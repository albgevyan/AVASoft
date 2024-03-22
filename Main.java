//import com.sun.tools.jdeprscan.scan.Scan;

//import com.sun.org.apache.xalan.internal.xsltc.runtime.Parameter;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;

public class Main{
    private static Scanner input = new Scanner(System.in);
    public static void main(String[] args) throws Exception {
        // DatabaseManipulator dbm = createDatabaseManipulator();
        DatabaseRetriever dbr = createDatabaseRetriever();

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
        // DataContainer row = new DataContainer("test1", "test2", "test3");
        // dbm.insertRow("table_creator", row);

        DataContainer selectedCol = new DataContainer("COLUMN_50");
        // DataContainer condVal = new DataContainer("test3");

        // ResultSet result = dbr.getTableData("table_creator", condCol, condVal);
        ResultSet result = dbr.getTableData("table_creator", selectedCol);
        printResaltSetData(result);
    }

    private static DatabaseManipulator createDatabaseManipulator() throws SQLException {

        // REMOVE THE COMMENTS

        System.out.print("Enter the database username:");
        String username = input.nextLine();

        System.out.print("Enter the database password:");
        String password = input.nextLine();

        String dbURL = "jdbc:oracle:thin:" + username + '/' + password + "@//localhost:1521/xe";

//        String dbURL = "jdbc:oracle:thin:system/12345@//localhost:1521/xe";

        return new DatabaseManipulator(dbURL);
    }
    
    private static DatabaseRetriever createDatabaseRetriever() throws SQLException{
        
        System.out.print("Enter the database username:");
        String username = input.nextLine();
        
        System.out.print("Enter the database password:");
        String password = input.nextLine();
        
        String dbURL = "jdbc:oracle:thin:" + username + '/' + password + "@//localhost:1521/xe";
        
        return new DatabaseRetriever(dbURL);
    }

    private static void printResaltSetData(ResultSet result) throws SQLException{
        ResultSetMetaData rsmd = result.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        while (result.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(",  ");
                String columnValue = result.getString(i);
                System.out.print(columnValue + " " + rsmd.getColumnName(i));
            }
            System.out.println("");
        }
    }
}
