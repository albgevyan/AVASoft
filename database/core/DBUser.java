package core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exceptions.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Albert Gevorgyam
 * @version 1.0
 */

// PREVENT USERNAME REPETITIONS
// IMPLEMENT METHOD TESTS
// CHECK THE TABLE NAMES
// MODIFY THE USER INPUT
// PREVENT UNAUTHORIZED ACCESS!!!!!!
// MAKE THE USERNAME&PASSWORD PRIVATE

public class DBUser implements java.io.Serializable{
    private static final MessageDigest digest;

    static {
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Warning there may ne security issues. Check your java.security package.");
            throw new RuntimeException(e);
        }
    }

    public enum Privilege {RETRIEVE, CREATE, INSERT, ALTER, GRANT}
//    private static final Path DB_PATH = Paths.get("/AVASoft/database/SCHEMAS").toAbsolutePath();
    private static final String DB_PATH = "C:/Users/alber/Documents/GitHub/AVASoft/database/SCHEMAS/";
    public final String username;
    public final String password;
    private Privilege[] privileges;
    private BufferedWriter databaseWriter;
    private BufferedReader databaseRetriever;
    private Gson jsonConverter;
    private static final int MIN_USERNAME_LENGTH = 5;
    private static final int MAX_USERNAME_LENGTH = 10;
    private static final int MIN_PASSWORD_LENGTH = 9;
    private static final int MAX_PASSWORD_LENGTH = 16;

    public DBUser(String username, String password, Privilege... privileges) throws InvalidInputFormatException {
        username = username.toUpperCase();
        this.validateUsername(username);
        this.validatePassword(password);
        this.username = username;
//        this.password = getSHA256Hash(password);
        this.password = password;
        this.privileges = privileges;
//        this.databaseWriter = new BufferedWriter(new FileWriter("C:/Users/alber/Documents/GitHub/AVASoft/database/SCHEMAS"));
//        this.databaseRetriever = new FileReader("C:/Users/alber/Documents/GitHub/AVASoft/database/SCHEMAS");
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeHierarchyAdapter(DBUser.class, new DBUserAdapter());
        this.jsonConverter = builder.create();
    }

    private void validateUsername(String username) throws InvalidInputFormatException{
        if (username.length() < MIN_USERNAME_LENGTH || username.length() > MAX_USERNAME_LENGTH)
            throw new InvalidInputFormatException("Your username must include at least " + MIN_USERNAME_LENGTH + " and at most " + MAX_USERNAME_LENGTH + " symbols.");

        for (int i = 0; i < username.length(); i++) {
            if (username.charAt(i) < 65 || username.charAt(i) > 90)
                throw new InvalidInputFormatException("Your username can only include uppercase latin letters.");
        }
    }

    private void validatePassword(String password) throws InvalidInputFormatException{

        if (password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH)
            throw new InvalidInputFormatException("Your password must include at least " + MIN_PASSWORD_LENGTH + " and at most " + MAX_PASSWORD_LENGTH + " symbols.");

        int symbolCount = 0, uppercaseCount = 0, lowercaseCount = 0, numberCount = 0;
        for (int i = 0; i < password.length(); i++) {
            char symbol = password.charAt(i);
            if (symbol >= 35 && symbol <= 38)
                symbolCount++;
            else if (symbol >= 48 && symbol <= 57)
                numberCount++;
            else if (symbol >= 65 && symbol <= 90)
                uppercaseCount++;
            else if (symbol >= 97 && symbol <= 122)
                lowercaseCount++;
            else
                throw new InvalidInputFormatException("Your password must include only numbers, uppercase and lowercase latin letter, and the symbols # $ % &");
        }

        if (symbolCount == 0 || lowercaseCount == 0 || uppercaseCount == 0 || numberCount == 0)
            throw new InvalidInputFormatException("Your password must include at least one uppercase letter, lowercase letter, number and symbol(# $ % &).");
    }

    private Privilege[] validatePrivileges(Privilege[] privileges) throws InvalidInputFormatException{
        int privilegeCount = 0;
        for (int i = 0; i < privileges.length; i++) {
            if (privileges[i] != null)
                privilegeCount++;
        }
        if (privilegeCount == 0)
            throw new InvalidInputFormatException("A database user must have at least one privilege.");

        Privilege[] filteredPrivileges = new Privilege[privilegeCount];
        for (int i = 0, j = 0; i < privileges.length; i++) {
            if (privileges[i] != null){
                filteredPrivileges[j] = privileges[i];
                j++;
            }
        }

        return filteredPrivileges;
    }

    public static String getSHA256Hash(String password){
        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

        // Convert byte array to hexadecimal string
        BigInteger hashInt = new BigInteger(1, hash);
        return hashInt.toString(16);
    }

    public static Privilege[] privilegesFromString(String privilegesString){
        DBUser.Privilege[] privileges = new DBUser.Privilege[5];
        for (int j = 0; j < privilegesString.length(); j++) {
            switch (privilegesString.charAt(j)) {
                case '0':
                    privileges[0] = Privilege.GRANT;
                    return privileges;
                case '1':
                    privileges[0] = DBUser.Privilege.CREATE;
                    break;
                case '2':
                    privileges[1] = DBUser.Privilege.INSERT;
                    break;
                case '3':
                    privileges[2] = DBUser.Privilege.RETRIEVE;
                    break;
                case '4':
                    privileges[3] = DBUser.Privilege.ALTER;
                    break;
            }
        }

        return privileges;
    }

    public String privilegesToString(){
        StringBuilder privilegesString = new StringBuilder(4);
        for (Privilege privilege : this.privileges) {
            switch (privilege) {
                case Privilege.GRANT:
                    privilegesString.append('0');
                    return privilegesString.toString();
                case Privilege.CREATE:
                    privilegesString.append('1');
                    break;
                case Privilege.INSERT:
                    privilegesString.append('2');
                    break;
                case Privilege.RETRIEVE:
                    privilegesString.append('3');
                    break;
                case Privilege.ALTER:
                    privilegesString.append('4');
                    break;
            }
        }

        return privilegesString.toString();
    }

    public boolean identifyUser(String username, String password) throws IdentificationFailed {
        return this.identifyUsername(username) && this.identifyPassword(password);
    }

    private boolean identifyPassword(String password) throws IncorrectPassword{
        if (password.length() != this.password.length())
            throw new IncorrectPassword();

//        password = getSHA256Hash(password);

        for (int i = 0; i < this.password.length(); i++) {
            if (this.password.charAt(i) != password.charAt(i))
                throw new IncorrectPassword();
        }

        return true;
    }

    private boolean identifyUsername(String username) throws IncorrectUsername{
        if (username.length() != this.username.length())
            throw new IncorrectUsername();

        for (int i = 0; i < this.username.length(); i++) {
            if (this.username.charAt(i) != username.charAt(i))
                throw new IncorrectUsername();
        }

        return true;
    }

    private boolean hasPrivilege(Privilege p) throws UnprivilegedActionException{
        for (Privilege privilege : this.privileges) {
            if (privilege == p)
                return true;
        }
        throw new UnprivilegedActionException(this.username, p);
    }

    public void createUser(String username, String password, Privilege[] privileges) throws UnprivilegedActionException, InvalidInputFormatException, IOException {
        hasPrivilege(Privilege.GRANT);
        privileges = validatePrivileges(privileges);
        DBUser newUser = new DBUser(username, password, privileges);
        this.databaseWriter = new BufferedWriter(new FileWriter(DB_PATH + "USERS/" + username + ".json"));
        databaseWriter.write(this.jsonConverter.toJson(newUser));
    }

//    public void createTable(String tableName) throws UnprivilegedActionException, IOException{
//        hasPrivilege(Privilege.CREATE);
//        Files.createDirectories(DB_PATH.resolve("TABLES" + tableName));
//    }

    public String toString(){
        return this.username + ',' + this.password + ',' + privilegesToString();
    }

    /*
    public void insertRow(Object row, String tableName) throws UnprivilegedActionException, IOException, TypeMismatchException{
        hasPrivilege(Privilege.INSERT);
        this.databaseRetriever = Files.newBufferedReader(DB_PATH.resolve("TABLES").resolve(tableName));
        this.databaseWriter = Files.newBufferedWriter(DB_PATH.resolve("TABLES").resolve(tableName));
        ArrayList<Object> TABLE = jsonConverter.fromJson(this.databaseRetriever, ArrayList.class);
        if (row.getClass() != TABLE.get(0).getClass())
            throw new TypeMismatchException(row.getClass(), TABLE.get(0).getClass());
        TABLE.add(row);
        databaseWriter.write(jsonConverter.toJson(TABLE));
    }

    public Object getRow(String tableName, int rowIndex) throws UnprivilegedActionException, IOException{
        hasPrivilege(Privilege.RETRIEVE);
        this.databaseRetriever = Files.newBufferedReader(DB_PATH.resolve(tableName));
        ArrayList<Object> TABLE = jsonConverter.fromJson(this.databaseRetriever, ArrayList.class);
        return TABLE.get(rowIndex);
    }
    */
}
