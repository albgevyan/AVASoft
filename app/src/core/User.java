package core;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import exceptions.IdentificationFailed;
import exceptions.InvalidInputFormatException;

import java.io.IOException;
import java.util.Date;

public abstract class User {

    static class UserAdapter extends TypeAdapter<User> {

        @Override
        public void write(JsonWriter writer, User user) throws IOException {
            if (user == null) {
                writer.nullValue();
                return;
            }

            writer.beginObject();

            writer.name("SSN").value(user.SSN);
            writer.name("name").value(user.name);
            writer.name("surname").value(user.surname);
            writer.name("email").value(user.email.toString());
            writer.name("birth").value(user.birthDate.toString());
            writer.name("gender").value(User.genderToString(user.gender));

            writer.endObject();
        }

        @Override
        public User read(JsonReader reader) throws IOException {
            if (reader.peek() == JsonToken.NULL){
                reader.nextNull();
                return null;
            }

            reader.beginObject();
            reader.nextName();
            int SSN = reader.nextInt();
            reader.nextName();
            String name = reader.nextString();
            reader.nextName();
            String surname = reader.nextString();
            reader.nextName();
            String email = reader.nextString();
            reader.nextName();
            Date birthDate = new Date(reader.nextString());
            reader.nextName();
            Gender gender = User.genderFromString(reader.nextString());

            try {
                return new Employee(SSN, name, surname, email, birthDate, gender);
            }
            catch (InvalidInputFormatException e){
                System.out.println("Warning the application maybe corrupt.");
                System.out.println(e.getMessage());
                return null;
            }
        }
    }

    public enum Gender{MALE, FEMALE}
    private int SSN;
    private String name;
    private String surname;
    private Email email;
    private Date birthDate;
    private Gender gender;


    public User(int SSN, String name, String surname, String email, Date birthDate, Gender gender) throws InvalidInputFormatException{

        validateSSN(SSN);
        validateName(name);
        validateName(surname);
        validateBirthdate(birthDate);

        this.SSN = SSN;
        this.name = name;
        this.surname = surname;
        this.email = new Email(email);
        this.birthDate = birthDate;
        this.gender = gender;
    }

    private void validateSSN(int SSN) throws InvalidInputFormatException {

        // count the number of digits
        int length = 0;
        for (; SSN != 0; SSN /= 10, length++);

        if (length != 10)
            throw new InvalidInputFormatException("Social Service Number must have 10 digits.");
    }

    private void validateName(String name) throws InvalidInputFormatException{
        if (name.isEmpty())
            throw new InvalidInputFormatException("The name cannot be empty.");

        char sym = name.charAt(0);

        if (!(sym >= 'A' && sym <= 'Z'))
            throw new InvalidInputFormatException("The first letter of the name must be uppercase.");

        for (int i = 1; i < name.length(); i++) {
            sym = name.charAt(i);
            if (!(sym >= 'a' && sym <= 'z'))
                throw new InvalidInputFormatException("The name must include only lowercase latin letter, except the first letter.");
        }
    }

    private void validateBirthdate(Date birthDate) throws InvalidInputFormatException{
        if (birthDate.after(new Date()))
            throw new InvalidInputFormatException("Specify a day from the past.");
    }

    public static String genderToString(Gender g){
        if (g == Gender.FEMALE)
            return "female";
        if (g == Gender.MALE)
            return "male";
        return null;
    }

    public static Gender genderFromString(String g){
        if (g.equals("female"))
            return Gender.FEMALE;
        if (g.equals("male"))
            return Gender.MALE;
        return null;
    }

    public abstract User login(String username, String password) throws IdentificationFailed;

    public abstract void logout();

    @Override
    public String toString() {return null;}
}
