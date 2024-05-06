package AvaBank.core;

public class User {
    public enum Gender{
        MALE, FEMALE
    }
    private String fullName;
    private Gender gender;
    private String userID;
    private String email;
    private String phoneNumber;
    private String address;


    public User(String fullName, Gender gender, String userId,  String email, String phoneNumber, String address) {
        this.fullName = fullName;
        this.gender = gender;
        this.userID = userId;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    // Getters and Setters
    public String getUserID() {
        return userID;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }


    @Override
    public String toString() {
        // later change toString method to save data in a file more efficiently
        return "User{" +
                "gender=" + gender +
                ", userId='" + userID + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}