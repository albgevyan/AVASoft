import core.DBUser;
import exceptions.IdentificationFailed;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public abstract class LoginUI extends JFrame {
    protected JTextField txtUsername;
    protected JPasswordField txtPassword;
    private JButton btnLogin;
    private JLabel lblLogo;


    public LoginUI() {
        setTitle("AVABANK SYSTEM LOG-IN");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(70, 130, 180));
        setLayout(new BorderLayout(10, 10));
        setMinimumSize(new Dimension(400, 350));
        setResizable(false);

        ImageIcon logoIcon = new ImageIcon("/Users/arinapambukyan/Documents/GitHub/AVASoft/ui/src/AVALogo.png");
        lblLogo = new JLabel(logoIcon, SwingConstants.CENTER);
        lblLogo.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JLabel lblTitle = new JLabel("AVABANK SYSTEM LOG-IN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitle.setForeground(new Color(255, 212, 2));

        JPanel logoPanel = new JPanel(new BorderLayout());
        logoPanel.setBackground(new Color(70, 130, 180));
        logoPanel.add(lblLogo, BorderLayout.CENTER);
        logoPanel.add(lblTitle, BorderLayout.SOUTH);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(70, 130, 180));
        topPanel.add(new JPanel(), BorderLayout.NORTH);
        topPanel.add(logoPanel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        panel.setBackground(new Color(70, 130, 180));

        JLabel lblUsername = new JLabel("USERNAME:");
        lblUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        lblUsername.setForeground(Color.WHITE); // White text for visibility
        txtUsername = new JTextField(20);
        txtUsername.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        panel.add(lblUsername);
        panel.add(txtUsername);

        JLabel lblPassword = new JLabel("PASSWORD:");
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        lblPassword.setForeground(Color.WHITE);
        txtPassword = new JPasswordField(20);
        txtPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        panel.add(lblPassword);
        panel.add(txtPassword);

        btnLogin = new JButton("Next");
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.setForeground(new Color(7, 18, 93));
        btnLogin.setBackground(new Color(70, 130, 180));
        btnLogin.setOpaque(true);
        btnLogin.setFocusPainted(true);
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText();
                String password = new String(txtPassword.getPassword());

                try {
                    DBUser user = DBUser.login(username, password);
                    if (user != null) {
                        //openNewWindow();  // User is valid, open the new window
                    } else {
                        // User is not valid, show error message
                        JOptionPane.showMessageDialog(LoginUI.this,
                                "Incorrect username or password",
                                "Login Error",
                                JOptionPane.ERROR_MESSAGE);
                        txtUsername.setText("");
                        txtPassword.setText("");
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(LoginUI.this,
                            "An error occurred while trying to read user data: " + ex.getMessage(),
                            "Login Error",
                            JOptionPane.ERROR_MESSAGE);
                } catch (IdentificationFailed ex) {
                    JOptionPane.showMessageDialog(LoginUI.this,
                            ex.getMessage(),
                            "Login Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        lblTitle.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        southPanel.setOpaque(false);
        southPanel.add(btnLogin);
        southPanel.setBackground(new Color(70, 130, 180));

        add(panel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }

    protected abstract void performLogin();

    /*private void openNewWindow() {
        JFrame newWindow = new JFrame("New Window");
        newWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newWindow.setSize(300, 200);
        newWindow.setLocationRelativeTo(null);
        newWindow.setVisible(true);
    }


    public static void main(String[] args) {
        new LoginUI();
    }

     */
}


