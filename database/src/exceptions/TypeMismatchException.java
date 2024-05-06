package exceptions;

public class TypeMismatchException extends DatabaseException{
    public TypeMismatchException(){
        super();
    }

    public TypeMismatchException(String message){
        super(message);
    }

    public TypeMismatchException(Class<?> given, Class<?> expected){
        super("An object of different type cannot ne added to a table, diven " + given + " expected " + expected);
    }
}
