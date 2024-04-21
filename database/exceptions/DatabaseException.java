package exceptions;

public class DatabaseException extends Exception{
    public DatabaseException(){
        super("Failed to connect to the database.");
    }

    public DatabaseException(String message){
        super(message);
    }
}
