package core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import exceptions.IdentificationFailed;
import exceptions.IncorrectUsername;
import exceptions.InvalidInputFormatException;
import exceptions.UnprivilegedActionException;
import wrapper.Connection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class Employee extends User{

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

        }

        @Override
        public Employee read(JsonReader reader) throws IOException {
            if (reader.peek() == JsonToken.NULL){
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

            try {
                return new Employee(SSN, name, surname, email, birthDate, gender, salary, department);
            }
            catch (InvalidInputFormatException e){
                System.out.println("Warning the application maybe corrupt.");
                System.out.println(e.getMessage());
                return null;
            }
        }
    }

    enum Department{CUSTOMER_SERVICE, INFORMATION_TECNOLOGIES}

    private static final String DB_CLIENT_USERNAME = "EMPL_LOGIN_CLIENT";
    private static final String DB_CLIENT_PASSWORD = "";
    private int salary;
    private Department department;
    private String username;


    public Employee(int SSN, String name, String surname, String email, Date birthDate, Gender gender) throws IdentificationFailed, InvalidInputFormatException {
        super(SSN, name, surname, email, birthDate, gender);
        this.username = name + " " + surname;
        try {
            validateUsername(username);
        }
        catch (InvalidInputFormatException e) {
            for (int i = 0; ; i++) {
                try {
                    validateUsername(username + '_' + i);
                    break;
                } catch (InvalidInputFormatException e1) {}
            }
        }
    }

    public Employee(int SSN, String name, String surname, String email, Date birthDate, Gender gender, int salary, Department department) throws InvalidInputFormatException{
        super(SSN, name, surname, email, birthDate, gender);
        this.salary = salary;
        this.department = department;
    }

    private void validateUsername(String username) throws IdentificationFailed, InvalidInputFormatException{
        Connection connection;
        ArrayList<Employee> EMPLOYEES = new ArrayList<>();
        try {
            connection = Connection.getConnection(DB_CLIENT_USERNAME, DB_CLIENT_PASSWORD);
            EMPLOYEES = connection.retrieve("EMPLOYEE", Employee.class);
        }
        catch (IOException | UnprivilegedActionException e){
            System.out.println(e.getMessage());
            System.out.println("Warning the database user was not found. The database file system may be corrupt.");
        }

        for (Employee emp : EMPLOYEES){
            if (emp.identifyUsername(username))
                throw new InvalidInputFormatException();
        }
    }

    public boolean identifyUsername(String username) throws IncorrectUsername{
        if (username.length() != this.username.length())
            throw new IncorrectUsername();

        for (int i = 0; i < this.username.length(); i++) {
            if (this.username.charAt(i) != username.charAt(i))
                throw new IncorrectUsername();
        }

        return true;
    }

    public boolean identifyPassword(String password){}

    public static String departmentToString(Department d){
        if (d == Department.CUSTOMER_SERVICE)
            return "Customer Service";
        if (d == Department.INFORMATION_TECNOLOGIES)
            return "Information Technologies";
        return null;
    }

    public static Department departmentFromString(String d){
        if (d.equals("Customer Service"))
            return Department.CUSTOMER_SERVICE;
        if (d.equals("Information Technologies"))
            return Department.INFORMATION_TECNOLOGIES;
        return null;
    }

    @Override
    public boolean identification(String password) {
        return false;
    }

    @Override
    public User login(String username, String password) throws IdentificationFailed{

        Connection connection;
        try {
            connection = Connection.getConnection(DB_CLIENT_USERNAME, DB_CLIENT_PASSWORD);
            ArrayList<User> EMPLOYEES = connection.retrieve("EMPLOYEE", User.class);
        }
        catch (IOException | UnprivilegedActionException e){
            System.out.println(e.getMessage());
            System.out.println("Warning the database user was not found. The database file system may be corrupt.");
            return null;
        }



        return null;
    }

    @Override
    public void logout() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeHierarchyAdapter(User.class, new UserAdapter());
        Gson jsonConverter = builder.create();
    }

    @Override
    public void changePassword() {

    }
}
