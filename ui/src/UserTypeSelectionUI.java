import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserTypeSelectionUI extends JFrame {
    private JButton btnDatabaseUser;
    private JButton btnCustomerService;
    private JButton btnBackOffice;
    private JLabel lblLogo;

    public UserTypeSelectionUI() {
        setTitle("Select User Type");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window on the screen
        getContentPane().setBackground(new Color(70, 130, 180));
        setLayout(new BorderLayout(10, 10));
        setMinimumSize(new Dimension(300, 350));
        setResizable(false);

        // Logo at the top
        ImageIcon logoIcon = new ImageIcon("/Users/arinapambukyan/Documents/GitHub/AVASoft/ui/src/AVALogo.png"); // Replace with the actual path
        lblLogo = new JLabel(logoIcon, SwingConstants.CENTER);
        lblLogo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Added some vertical padding

        // Title "AVA BANK" below the logo
        JLabel lblTitle = new JLabel("AVA BANK", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitle.setForeground(new Color(255, 212, 2)); // Golden text

        // Subtitle "CHOOSE YOUR DEPARTMENT"
        JLabel lblSubtitle = new JLabel("CHOOSE YOUR DEPARTMENT", SwingConstants.CENTER);
        lblSubtitle.setFont(new Font("Arial", Font.BOLD, 14));
        lblSubtitle.setForeground(Color.WHITE);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(70, 130, 180));
        headerPanel.add(lblLogo, BorderLayout.NORTH);
        headerPanel.add(lblTitle, BorderLayout.CENTER);
        headerPanel.add(lblSubtitle, BorderLayout.SOUTH);


        add(headerPanel, BorderLayout.NORTH);

        // Panel for buttons
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        buttonPanel.setBackground(new Color(70, 130, 180));

        btnDatabaseUser = new JButton("INFORMATION TECHNOLOGIES");
        btnCustomerService = new JButton("CUSTOMER SERVICE");
        btnBackOffice = new JButton("BACK OFFICE");

        // Setting up the buttons with increased font size and smaller size adjustments
        setupButton(btnDatabaseUser, DatabaseUserLoginUI.class, new Dimension(280, 30));
        //setupButton(btnCustomerService, CustomerServiceLoginUI.class, new Dimension(280, 30));
        //setupButton(btnBackOffice, BackOfficeLoginUI.class, new Dimension(280, 30));

        buttonPanel.add(btnDatabaseUser);
        buttonPanel.add(btnCustomerService);
        buttonPanel.add(btnBackOffice);

        add(buttonPanel, BorderLayout.CENTER);
        pack();
        setVisible(true);
    }

    private void setupButton(JButton button, Class<? extends JFrame> loginUI, Dimension buttonSize) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(new Color(255, 183, 2));
        button.setBackground(new Color(70, 130, 180));
        button.setPreferredSize(buttonSize);
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    loginUI.getDeclaredConstructor().newInstance().setVisible(true);
                    UserTypeSelectionUI.this.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(UserTypeSelectionUI.this,
                            "Error opening login window: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        new UserTypeSelectionUI();
    }
}
