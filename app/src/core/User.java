package AvaBank.core;

/**
 * The User class represents a user in the AvaBank system.
 * It contains information such as full name, gender, user ID, email, phone number, and address.
 */
public class User {

    /**
     * Enum representing the gender of the user.
     */
    public enum Gender{
        MALE, FEMALE
    }

    private String fullName;
    private Gender gender;
    private String userID;
    private String email;
    private String phoneNumber;
    private String address;
    private String password;

    /**
     * Constructs a User object with the specified details.
     *
     * @param fullName     the full name of the user
     * @param gender       the gender of the user
     * @param userId       the user ID of the user
     * @param email        the email address of the user
     * @param phoneNumber  the phone number of the user
     * @param address      the address of the user
     */
    public User(String fullName, Gender gender, String userId,  String email, String phoneNumber, String address) {
        this.fullName = fullName;
        this.gender = gender;
        this.userID = userId;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    // Getters and Setters

    /**
     * Gets the user ID.
     *
     * @return the user ID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Gets the full name of the user.
     *
     * @return the full name of the user
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Gets the email address of the user.
     *
     * @return the email address of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the phone number of the user.
     *
     * @return the phone number of the user
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Gets the address of the user.
     *
     * @return the address of the user
     */
    public String getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Customer customer = (Customer) object;
        return getPhoneNumber().equals(customer.getPhoneNumber());
    }

    /**
     * Returns a string representation of the user.
     *
     * @return a string representation of the user
     */


    @Override
    public String toString() {
        return fullName + "," + gender + "," + userID + "," + email + "," + phoneNumber + "," + address;
    }
}
