package exceptions;

public class IncorrectPassword extends IdentificationFailed{
    public IncorrectPassword(){
        super("The provided password is incorrect.");
    }

    public IncorrectPassword(String message){
        super(message);
    }
}
