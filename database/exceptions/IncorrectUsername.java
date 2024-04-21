package exceptions;

public class IncorrectUsername extends IdentificationFailed{
    public IncorrectUsername(){
        super("The provided username is incorrect");
    }

    public IncorrectUsername(String message){
        super(message);
    }
}
