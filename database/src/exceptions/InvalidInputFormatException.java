package exceptions;

public class InvalidInputFormatException extends Exception{
    public InvalidInputFormatException(){
        super("Error, the input is incorrect.");
    }

    public InvalidInputFormatException(String message){
        super(message);
    }
}
