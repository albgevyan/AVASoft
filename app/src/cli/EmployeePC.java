package AvaBank.cli;

public class EmployeePC extends MoneyTransferService {

    public EmployeePC(int serialNumber) {
        super(serialNumber);
    }


    public boolean loginMatches(String phoneNumber, String password) {
        // we have initially declared employees in our databes
        // this method returns corresponding boolean value for a employee to login into his program and start working
        // when true GUI for employee starts to properly work with its methods
        return true;
    }
}
