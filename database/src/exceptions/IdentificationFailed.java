package exceptions;

public class IdentificationFailed extends Exception{
    public IdentificationFailed(){
        super("Identification failed.");
    }

    public IdentificationFailed(String message){
        super(message);
    }
}
