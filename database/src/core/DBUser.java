package core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import exceptions.*;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

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

    private static class DBUserAdapter  extends TypeAdapter<DBUser> {

        public DBUser read(JsonReader reader) throws IOException {
            if (reader.peek() == JsonToken.NULL){
                reader.nextNull();
                return null;
            }

            reader.beginObject();
            reader.nextName();
            String username = reader.nextString();
            reader.nextName();
            String password = reader.nextString();
            reader.nextName();
            DBUser.Privilege[] privileges = DBUser.privilegesFromString(reader.nextString());
            reader.endObject();

            try {
                return new DBUser(username, password, privileges);
            }
            catch (InvalidInputFormatException e){
                System.out.println("Warning the program encountered an error; the user may be corrupt.");
                System.out.println(e.getMessage());
                return null;
            }
        }

        public void write(JsonWriter writer, DBUser user) throws IOException{
            if (user == null) {
                writer.nullValue();
                return;
            }
            writer.beginObject();
            writer.name("username").value(user.username);
            writer.name("password").value(user.password);
            writer.name("privileges").value(user.privilegesToString());
            writer.endObject();
        }
    }
    private static final MessageDigest digest;

    static {
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Warning there may be security issues. Check your java.security package.");
            throw new RuntimeException(e);
        }
    }

    public enum Privilege {RETRIEVE, CREATE, INSERT, ALTER, GRANT, DELETE}
    private static final Path DB_PATH = SCHEMAS.DatabaseDirectory.getDatabasePath();
    private final String username;
    public final String password;
    private Privilege[] privileges;
    private BufferedWriter databaseWriter;
    private BufferedReader databaseRetriever;
    private final Gson jsonConverter;
    private static final int MIN_USERNAME_LENGTH = 5;
    private static final int MAX_USERNAME_LENGTH = 20;
    private static final int MIN_PASSWORD_LENGTH = 12;
    private static final int MAX_PASSWORD_LENGTH = 18;
    private static final int MIN_INPUT_LENGTH = 4;
    private static final int MAX_INPUT_LENGTH = 30;

    public DBUser(String username, String password, Privilege... privileges) throws InvalidInputFormatException {
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
        username = username.toUpperCase();

        if (username.length() < MIN_USERNAME_LENGTH || username.length() > MAX_USERNAME_LENGTH)
            throw new InvalidInputFormatException("Your username must include at least " + MIN_USERNAME_LENGTH + " and at most " + MAX_USERNAME_LENGTH + " symbols.");

        for (int i = 0; i < username.length(); i++) {
            char sym = username.charAt(i);
            if (sym != '_' && (sym < 'A' || sym > 'Z'))
                throw new InvalidInputFormatException("Your username can only include uppercase latin letters.");
        }
    }

    private void validatePassword(String password) throws InvalidInputFormatException{

        if (password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH)
            throw new InvalidInputFormatException("Your password must include at least " + MIN_PASSWORD_LENGTH + " and at most " + MAX_PASSWORD_LENGTH + " symbols.");

        int symbolCount = 0, uppercaseCount = 0, lowercaseCount = 0, numberCount = 0;
        for (int i = 0; i < password.length(); i++) {
            char sym = password.charAt(i);
            if (sym >= '#' && sym <= '&')
                symbolCount++;
            else if (sym >= '0' && sym <= '9')
                numberCount++;
            else if (sym >= 'A' && sym <= 'Z')
                uppercaseCount++;
            else if (sym >= 'a' && sym <= 'z')
                lowercaseCount++;
            else
                throw new InvalidInputFormatException("Your password must include only numbers, uppercase and lowercase latin letter, and the symbols # $ % &");
        }

        if (symbolCount == 0 || lowercaseCount == 0 || uppercaseCount == 0 || numberCount == 0)
            throw new InvalidInputFormatException("Your password must include at least one uppercase letter, lowercase letter, number and symbol(# $ % &).");
    }

    private void validateInput(String input) throws InvalidInputFormatException{

        if (input.length() < MIN_INPUT_LENGTH || input.length() > MAX_INPUT_LENGTH)
            throw new InvalidInputFormatException("Error the input must be in the range [" + MIN_INPUT_LENGTH + ", " + MAX_INPUT_LENGTH + "]");

        input = input.toUpperCase();

        for (int i = 0; i < input.length(); i++){
            char sym = input.charAt(i);
            if (sym != '-' && sym != '_' && (sym < 'A' || sym > 'Z'))
                throw new InvalidInputFormatException("Error the input should consist of uppercase latin letters, numbers or symbols hyphen-minus and low line.");
        }
    }

    private Privilege[] validatePrivileges(Privilege[] privileges) throws InvalidInputFormatException{
        int privilegeCount = 0;
        for (Privilege privilege : privileges) {
            if (privilege != null)
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

        DBUser.Privilege[] privileges = new DBUser.Privilege[6];

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
                case '5':
                    privileges[4] = DBUser.Privilege.DELETE;
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
                case Privilege.DELETE:
                    privilegesString.append('5');
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

    public static DBUser login(String username, String password) throws IOException, IdentificationFailed{
        FileReader fileReader = new FileReader(SCHEMAS.DatabaseDirectory.getDatabaseDirectory() + "/USERS/" + username + ".json");
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeHierarchyAdapter(DBUser.class, new DBUserAdapter());
        Gson gson = builder.create();
        DBUser user = gson.fromJson(fileReader, DBUser.class);

        if (user.identifyUser(username, password))
            return user;
        return null;
    }

    public void logout() throws IOException{
        String json = this.jsonConverter.toJson(this);
        this.databaseWriter = new BufferedWriter(new FileWriter(DB_PATH.resolve("USERS").resolve(this.username + ".json").toString()));
        this.databaseWriter.write(json);
        this.databaseWriter.close();
    }

    private boolean hasPrivilege(Privilege p) throws UnprivilegedActionException{
        for (Privilege privilege : this.privileges) {
            if (privilege == Privilege.GRANT || privilege == p)
                return true;
        }
        throw new UnprivilegedActionException(this.username, p);
    }

    public void createUser(String username, String password, Privilege[] privileges) throws UnprivilegedActionException, InvalidInputFormatException, IOException {
        hasPrivilege(Privilege.GRANT);
        privileges = validatePrivileges(privileges);
        DBUser newUser = new DBUser(username, password, privileges);
        this.databaseWriter = new BufferedWriter(new FileWriter(DB_PATH.resolve("USERS").resolve(username + ".json").toString()));
        this.databaseWriter.write(this.jsonConverter.toJson(newUser));
        this.databaseWriter.close();
    }

    public void createTable(String tableName) throws UnprivilegedActionException, InvalidInputFormatException, IOException{
        hasPrivilege(Privilege.CREATE);
        validateInput(tableName);
        Files.createDirectories(DB_PATH.resolve("TABLES").resolve(tableName));
    }

    public void deleteUser(String username) throws UnprivilegedActionException, IOException{
        hasPrivilege(Privilege.DELETE);
        Files.delete(DB_PATH.resolve("USERS").resolve(username + ".json"));
    }

    public void deleteTable(String tableName) throws UnprivilegedActionException, InvalidInputFormatException{
        hasPrivilege(Privilege.DELETE);
        validateInput(tableName);
        deleteDirectory(new File(DB_PATH.resolve("TABLES").resolve(tableName).toString()));
    }

    private static void deleteDirectory(File directory){
        if (directory.isDirectory()) {
            for (File file : Objects.requireNonNull(directory.listFiles())) {
                deleteDirectory(file);  // Recursive call for subdirectories
            }
            // Delete the empty directory after deleting contents
            directory.delete();
        } else {
            // Delete the file if it's not a directory
            directory.delete();
        }
    }

    @Override
    public String toString(){return null;}

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
