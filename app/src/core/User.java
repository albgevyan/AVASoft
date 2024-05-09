package core;

import java.util.Date;

public abstract class User {

    enum Gender{MALE, FEMALE}
    private int SSN;
    private String name;
    private String surname;
    private String email;
    private Date birthDate;
    private Gender gender;

    public abstract User login();

    public abstract void logout();
    public abstract void changePassword();
}
