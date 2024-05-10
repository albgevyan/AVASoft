package core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import exceptions.*;
import wrapper.Connection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class Employee extends User {

    public class EmployeeAdapter extends TypeAdapter<Employee> {
        @Override
        public void write(JsonWriter writer, Employee employee) throws IOException {
            if (employee == null) {
                writer.nullValue();
                return;
            }

            new User.UserAdapter().write(writer, employee);

            writer.beginObject();
            writer.name("salary").value(employee.salary);
            writer.name("department").value(departmentToString(employee.department));
            writer.name("username").value(employee.username);
        }

        @Override
        public Employee read(JsonReader reader) throws IOException {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return null;
            }

            reader.beginObject();
            reader.nextName();
            int SSN = reader.nextInt();
            reader.nextName();
            String name = reader.nextString();
            reader.nextName();
            String surname = reader.nextString();
            reader.nextName();
            String email = reader.nextString();
            reader.nextName();
            Date birthDate = new Date(reader.nextString());
            reader.nextName();
            Gender gender = User.genderFromString(reader.nextString());
            reader.nextName();
            int salary = reader.nextInt();
            reader.nextName();
            Department department = Employee.departmentFromString(reader.nextString());
            reader.nextName();
            String username = reader.nextString();
            reader.nextName();
            String password = reader.nextString();

            try {
                return new Employee(SSN, name, surname, email, birthDate, gender, salary, department, username, password);
            } catch (InvalidInputFormatException e) {
                System.out.println("Warning the application maybe corrupt.");
                System.out.println(e.getMessage());
                return null;
            }
        }
    }

    public enum Department {CUSTOMER_SERVICE, INFORMATION_TECHNOLOGIES}

    private static final String DB_CLIENT_USERNAME = "EMPL_LOGIN_CLIENT";
    private static final String DB_CLIENT_PASSWORD = "Xyz#12345678";
    private static final int MIN_PASSWORD_LENGTH = 12;
    private static final int MAX_PASSWORD_LENGTH = 18;
    private int salary;
    private Department department;
    private String username;
    private String password;


    public Employee(int SSN, String name, String surname, String email, Date birthDate, Gender gender) throws InvalidInputFormatException {
        super(SSN, name, surname, email, birthDate, gender);
    }

    public Employee(int SSN, String name, String surname, String email, Date birthDate, Gender gender, int salary, Department department, String password) throws InvalidInputFormatException {
        super(SSN, name, surname, email, birthDate, gender);
        this.salary = salary;
        this.department = department;
        this.password = password;
        String username = name + " " + surname;
        try {
            validateUsername(username);
        } catch (InvalidInputFormatException e) {
            for (int i = 0; ; i++) {
                try {
                    validateUsername(username + '_' + i);
                    break;
                } catch (IdentificationFailed e1) {
                    System.out.println("Warning the application maybe corrupt.");
                    System.out.println(e1.getMessage());
                } catch (InvalidInputFormatException e1) {
                }
            }
        } catch (IdentificationFailed e) {
            System.out.println("Warning the application maybe corrupt.");
            System.out.println(e.getMessage());
        }
        this.username = username;
    }

    public Employee(int SSN, String name, String surname, String email, Date birthDate, Gender gender, int salary, Department department, String username, String password) throws InvalidInputFormatException {
        super(SSN, name, surname, email, birthDate, gender);
        this.salary = salary;
        this.department = department;
        this.username = username;
        this.password = password;
    }

    private void validateUsername(String username) throws IdentificationFailed, InvalidInputFormatException {
        Connection connection;
        ArrayList<Employee> EMPLOYEES = new ArrayList<>();
        try {
            connection = Connection.getConnection(DB_CLIENT_USERNAME, DB_CLIENT_PASSWORD);
            EMPLOYEES = connection.retrieve("EMPLOYEE", Employee.class);
        } catch (IOException | UnprivilegedActionException e) {
            System.out.println(e.getMessage());
            System.out.println("Warning the database user was not found. The database file system may be corrupt.");
        }

        for (Employee emp : EMPLOYEES) {
            if (emp.identifyUsername(username))
                throw new InvalidInputFormatException();
        }
    }

    private void validatePassword(String password) throws InvalidInputFormatException {

        if (password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH)
            throw new InvalidInputFormatException("Your password must include at least " + MIN_PASSWORD_LENGTH + " and at most " + MAX_PASSWORD_LENGTH + " symbols.");

        int symbolCount = 0, uppercaseCount = 0, lowercaseCount = 0, numberCount = 0;
        for (int i = 0; i < password.length(); i++) {
            char sym = password.charAt(i);
            if (sym >= '#' && sym <= '&')
                symbolCount++;
            else if (sym >= '0' && sym <= '9')
                numberCount++;
            else if (sym >= 'A' && sym <= 'Z')
                uppercaseCount++;
            else if (sym >= 'a' && sym <= 'z')
                lowercaseCount++;
            else
                throw new InvalidInputFormatException("Your password must include only numbers, uppercase and lowercase latin letter, and the symbols # $ % &");
        }

        if (symbolCount == 0 || lowercaseCount == 0 || uppercaseCount == 0 || numberCount == 0)
            throw new InvalidInputFormatException("Your password must include at least one uppercase letter, lowercase letter, number and symbol(# $ % &).");
    }

    public boolean identifyUsername(String username) throws IncorrectUsername {
        if (username.length() != this.username.length())
            throw new IncorrectUsername();

        for (int i = 0; i < this.username.length(); i++) {
            if (this.username.charAt(i) != username.charAt(i))
                throw new IncorrectUsername();
        }

        return true;
    }

    public boolean identifyPassword(String password) throws IncorrectPassword {
        if (password.length() != this.password.length())
            throw new IncorrectPassword();

//        password = getSHA256Hash(password);

        for (int i = 0; i < this.password.length(); i++) {
            if (this.password.charAt(i) != password.charAt(i))
                throw new IncorrectPassword();
        }

        return true;
    }

    public static String departmentToString(Department d) {
        if (d == Department.CUSTOMER_SERVICE)
            return "Customer Service";
        if (d == Department.INFORMATION_TECHNOLOGIES)
            return "Information Technologies";
        return null;
    }

    public static Department departmentFromString(String d) {
        if (d.equals("Customer Service"))
            return Department.CUSTOMER_SERVICE;
        if (d.equals("Information Technologies"))
            return Department.INFORMATION_TECHNOLOGIES;
        return null;
    }

    public boolean identification(String username, String password) throws IdentificationFailed {
        return identifyUsername(username) && identifyPassword(password);
    }

    @Override
    public User login(String username, String password) throws IdentificationFailed {

        Connection connection;
        ArrayList<Employee> EMPLOYEES;
        try {
            connection = Connection.getConnection(DB_CLIENT_USERNAME, DB_CLIENT_PASSWORD);
            EMPLOYEES = connection.retrieve("EMPLOYEE", Employee.class);
        } catch (IOException | UnprivilegedActionException e) {
            System.out.println(e.getMessage());
            System.out.println("Warning the database user was not found. The database file system may be corrupt.");
            return null;
        }

        for (Employee emp : EMPLOYEES) {
            if (emp.identification(username, password))
                return emp;
        }

        return null;
    }

    @Override
    public void logout() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeHierarchyAdapter(Employee.class, new EmployeeAdapter());
        Gson jsonConverter = builder.create();
    }
}
