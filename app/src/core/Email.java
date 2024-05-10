package core;

import exceptions.InvalidInputFormatException;

public class Email{
    private String local;
    private String domain;

    public Email(String email) throws InvalidInputFormatException {

        if (email.isEmpty())
            throw new InvalidInputFormatException("Email cannot be empty.");

        int i = 0;
        for (char sym = email.charAt(0); sym != '@'; i++) {
            try {
                sym = email.charAt(i);
            }
            catch (IndexOutOfBoundsException e){
                throw new InvalidInputFormatException("Email address must include the At(@) symbol.");
            }
            if (!(sym >= 'a' && sym <= 'z') &&
                    !(sym == '_' || sym == '.')) {
                throw new InvalidInputFormatException("Email address can only contain latin letters, numbers, low line(_) and dot(.)");
            }
        }

        this.local = email.substring(0, i);
        this.domain = email.substring(i+1);

        for (char sym; i < email.length(); i++){
            sym = email.charAt(i+1);
            if (!(sym >= 'a' && sym <= 'z') && sym != '.') {
                throw new InvalidInputFormatException("");
            }
        }
    }

    @Override
    public String toString() {
        return this.local + '@' + this.domain;
    }
}
