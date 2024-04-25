package AvaBank.UserManagment;

public class User {
    public enum Gender{
        MALE, FEMALE
    }
    private Gender gender;
    private String userId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;
    private String citizenship;

    public User(Gender gender, String userId, String fullName, String email, String phoneNumber, String address, String citizenship) {
        this.gender = gender;
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.citizenship = citizenship;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "User " + userId;
    }
}