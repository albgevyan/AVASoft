import exceptions.IdentificationFailed;
import wrapper.Connection;

import javax.swing.*;

public class DatabaseUserLoginUI extends LoginUI {
    public DatabaseUserLoginUI() {
        super();
        setTitle("Database User Login");
    }

    @Override
    protected void performLogin() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        try {
            Connection conn = Connection.getConnection(username, password);
            if (conn != null) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                // new window
            } else {
                JOptionPane.showMessageDialog(this,
                        "Login Failed: Please check your username and password and try again.",
                        "Login Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (IdentificationFailed ife) {
            JOptionPane.showMessageDialog(this,
                    "Login Failed: " + ife.getMessage(),
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
