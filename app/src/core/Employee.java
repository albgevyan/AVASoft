package AvaBank.core;

/**
 * The Employee class represents an employee of AvaBank with functionalities related to customer management.
 * This class extends the User class and is responsible for registering new customers.
 */
public class Employee extends User {
    private String login;
    private String password;

    /**
     * Constructs an Employee object with the specified details.
     *
     * @param fullName    the full name of the employee
     * @param gender      the gender of the employee
     * @param userId      the user ID of the employee
     * @param email       the email address of the employee
     * @param phoneNumber the phone number of the employee
     * @param address     the address of the employee
     * @param login       the login username of the employee
     * @param password    the login password of the employee
     */
    public Employee(String fullName, Gender gender, String userId, String email, String phoneNumber, String address, String login, String password) {
        super(fullName, gender, userId, email, phoneNumber, address);
        this.login = login;
        this.password = password;
    }

    /**
     * Registers a new customer with the provided details.
     *
     * @param fullName    the full name of the customer
     * @param gender      the gender of the customer
     * @param userId      the user ID of the customer
     * @param email       the email address of the customer
     * @param phoneNumber the phone number of the customer
     * @param address     the address of the customer
     * @param password    the login password for the customer's account
     */

    public void registerCustomer(String fullName, Gender gender, String userId, String email, String phoneNumber, String address, String password){
        // GUI sends parameters to registerCustomer and creates a new Customer object which is then saved in a database
        Customer customer = new Customer(fullName, gender, userId, email, phoneNumber, address);
    }
}
