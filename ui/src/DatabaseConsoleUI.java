import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseConsoleUI extends JFrame {
    private JTextArea dataArea;

    public DatabaseConsoleUI() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Database User Console");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Menu bar setup
        setJMenuBar(createMenuBar());

        // Data display
        dataArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(dataArea);
        add(scrollPane, BorderLayout.CENTER);

        // Status bar
        JLabel statusLabel = new JLabel(" Ready");
        add(statusLabel, BorderLayout.SOUTH);

        // Load initial data
        loadData();
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);

        JMenu dataMenu = new JMenu("Data");
        JMenuItem refreshItem = new JMenuItem("Refresh");
        refreshItem.addActionListener(e -> loadData());
        dataMenu.add(refreshItem);

        menuBar.add(fileMenu);
        menuBar.add(dataMenu);
        return menuBar;
    }

    private void loadData() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:url", "user", "password"); // Replace with actual connection details
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM some_table"); // Replace with actual query
            StringBuilder builder = new StringBuilder();
            while (resultSet.next()) {
                builder.append(resultSet.getString("column_name")).append("\n"); // Adjust according to actual data structure
            }
            dataArea.setText(builder.toString());
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DatabaseConsoleUI ui = new DatabaseConsoleUI();
            ui.setVisible(true);
        });
    }
}
