package exceptions;

import core.DBUser;

public class UnprivilegedActionException extends DatabaseException{
    public UnprivilegedActionException(){
        super("Database user attempts an action without privilege.");
    }

    public UnprivilegedActionException(String message){
        super(message);
    }

    public UnprivilegedActionException(String username, DBUser.Privilege mustHave){
        super("The user " + username + " " + mustHave + " privilege.");
    }
}
