package AvaBank.core;

public class Employee extends User {
    private String login;
    private String password;
    public Employee(String fullName, Gender gender, String userId, String email, String phoneNumber, String address, String login, String password) {
        super(fullName, gender, userId, email, phoneNumber, address);
        this.login = login;
        this.password = password;
    }

    public void registerCustomer(String fullName, Gender gender, String userId, String email, String phoneNumber, String address, String password){
        // GUI sends parameters to registerCustomer and creates a new Customer object which is then saved in a database
        Customer customer = new Customer(fullName, gender, userId, email, phoneNumber, address);
    }
}
