package AvaBank.login;

import AvaBank.database.CustomerDatabase;
import AvaBank.core.*;

import java.util.HashMap;
import java.util.Map;

public class CustomerAuthenticationManager {
    // Map to store user credentials (phone number -> password)
    // I just realised that we should not store passwords. To be reimplemented later
    private Map<String, String> credentials;

    public CustomerAuthenticationManager() {
        // Initialize the credentials map
        credentials = new HashMap<>();
        registerUser();
    }

    /**
     * Registers a new user with the provided phone number and password.
     *
     */
    public void registerUser() {
        // reads customer database file and puts phone number and bank account code into it
        CustomerDatabase database = new CustomerDatabase();
        for(int i = 0; i < database.getSize(); i++){
            Customer customer = database.getCustomer(i);
            credentials.put(customer.getPhoneNumber(), customer.getBankAccountPassword());
        }
    }

    /**
     * Authenticates a user based on the provided phone number and password.
     *
     * @param phoneNumber the phone number of the user
     * @param password    the password for the user's account
     * @return true if the authentication succeeds, false otherwise
     */
    public boolean authenticateUser(String phoneNumber, String password) {
        String storedPassword = credentials.get(phoneNumber);
        return storedPassword != null && storedPassword.equals(password);
    }

    /**
     * Checks if a user with the provided phone number is registered.
     *
     * @param phoneNumber the phone number of the user
     * @return true if the user is registered, false otherwise
     */
    public boolean isUserRegistered(String phoneNumber) {
        return credentials.containsKey(phoneNumber);
    }

}
