package AvaBank.cli;

import AvaBank.core.Customer;

import java.math.BigDecimal;

/**
 * The EmployeePC class represents a personal computer (PC) used by employees for money transfer operations.
 * It extends the MoneyTransferService class and provides additional functionality for employee login.
 */
public class EmployeePC extends MoneyTransferService {

    private boolean logined;

    /**
     * Constructs an EmployeePC object with the specified serial number.
     *
     * @param serialNumber the serial number of the employee PC
     */
    public EmployeePC(int serialNumber) {
        super(serialNumber);
        logined = false;
    }

    /**
     * Checks if the provided login credentials match an employee's information in the database.
     *
     * @param phoneNumber the phone number associated with the employee
     * @param password    the password associated with the employee's account
     * @return true if the login credentials match an employee's information, otherwise false
     */
    public boolean login(String phoneNumber, String password) { // later implement EmployeeAuthenticationManager
        // We initially have declared employees in our database.
        // This method returns a corresponding boolean value for an employee to login into their program and start working.
        // When true, the GUI for the employee starts to properly work with its methods.
        return true;
    }
}

