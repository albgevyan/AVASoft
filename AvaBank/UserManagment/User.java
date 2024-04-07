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
    private String password;

    public User(Gender gender, String userId, String fullName, String email, String phoneNumber, String address, String citizenship, String password) {
        this.gender = gender;
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.citizenship = citizenship;
        this.password = password;
    }

    public User(User user){
        this(user.getGender(), user.getUserId(), user.getFullName(), user.getEmail(), user.getPhoneNumber(), user.getAddress(), user.getCitizenship(), user.getPassword());
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

    public String getCitizenship() {
        return citizenship;
    }
    public void setCitizenship(String citizenship){
        this.citizenship = citizenship;
    }
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                "gender=" + gender +
                ", userId='" + userId + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", citizenship='" + citizenship + '\'' +
                '}';
    }
}