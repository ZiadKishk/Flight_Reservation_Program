import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AdminDashboard extends JFrame {
    public Administrator admin;

    public AdminDashboard(Administrator admin) {
        this.admin = admin;
        setTitle("Flight Booking System - Admin Dashboard");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(0, 102, 204));

        JLabel welcomeLabel = new JLabel("Welcome, Admin " + admin.getName() + "!");
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        headerPanel.add(welcomeLabel, BorderLayout.WEST);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            admin.logout();
            dispose();
            new LoginFrame().setVisible(true);
        });
        headerPanel.add(logoutButton, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // Main panel with tabs
        JTabbedPane tabbedPane = new JTabbedPane();

        // User Management tab
        JPanel userManagementPanel = new JPanel(new BorderLayout());
        userManagementPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Users table
        JTable usersTable = new JTable();
        JScrollPane usersScrollPane = new JScrollPane(usersTable);
        userManagementPanel.add(usersScrollPane, BorderLayout.CENTER);

        JButton refreshUsersButton = new JButton("Refresh Users");
        refreshUsersButton.addActionListener(e -> {
            List<User> users = BookingSystem.getInstance().getUsers();
            String[] columnNames = {"User ID", "Username", "Name", "Email", "Role"};
            Object[][] data = new Object[users.size()][5];

            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                data[i][0] = user.getUserId();
                data[i][1] = user.getUsername();
                data[i][2] = user.getName();
                data[i][3] = user.getEmail();

                if (user instanceof Customer) {
                    data[i][4] = "Customer";
                } else if (user instanceof Agent) {
                    data[i][4] = "Agent";
                } else if (user instanceof Administrator) {
                    data[i][4] = "Administrator";
                }
            }

            usersTable.setModel(new javax.swing.table.DefaultTableModel(
                    data, columnNames
            ));
        });

        JPanel userButtonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JButton addUserButton = new JButton("Add User");
        JButton editUserButton = new JButton("Edit User");
        JButton deleteUserButton = new JButton("Delete User");

        addUserButton.addActionListener(e -> {
            new RegistrationFrame().setVisible(true);
        });

        userButtonsPanel.add(refreshUsersButton);
        userButtonsPanel.add(addUserButton);
        userButtonsPanel.add(editUserButton);
        userButtonsPanel.add(deleteUserButton);

        userManagementPanel.add(userButtonsPanel, BorderLayout.SOUTH);
        tabbedPane.addTab("User Management", userManagementPanel);

        // System Settings tab
        JPanel settingsPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        settingsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField systemNameField = new JTextField("Flight Booking System");
        JTextField maintenanceModeField = new JTextField("false");
        JTextField maxLoginAttemptsField = new JTextField("3");
        JButton saveSettingsButton = new JButton("Save Settings");

        settingsPanel.add(new JLabel("System Name:"));
        settingsPanel.add(systemNameField);
        settingsPanel.add(new JLabel("Maintenance Mode:"));
        settingsPanel.add(maintenanceModeField);
        settingsPanel.add(new JLabel("Max Login Attempts:"));
        settingsPanel.add(maxLoginAttemptsField);
        settingsPanel.add(new JLabel());
        settingsPanel.add(saveSettingsButton);

        saveSettingsButton.addActionListener(e -> {
            boolean success = admin.modifySystemSettings("system_name", systemNameField.getText());
            success &= admin.modifySystemSettings("maintenance_mode", maintenanceModeField.getText());
            success &= admin.modifySystemSettings("max_login_attempts", maxLoginAttemptsField.getText());

            if (success) {
                JOptionPane.showMessageDialog(this, "Settings saved successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to save settings", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        tabbedPane.addTab("System Settings", settingsPanel);

        // Profile tab
        JPanel profilePanel = new JPanel(new GridLayout(0, 2, 10, 10));
        profilePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField nameField = new JTextField(admin.getName());
        JTextField emailField = new JTextField(admin.getEmail());
        JTextField contactField = new JTextField(admin.getContactInfo());
        JTextField securityLevelField = new JTextField(String.valueOf(admin.getSecurityLevel()));
        JButton updateProfileButton = new JButton("Update Profile");

        profilePanel.add(new JLabel("Name:"));
        profilePanel.add(nameField);
        profilePanel.add(new JLabel("Email:"));
        profilePanel.add(emailField);
        profilePanel.add(new JLabel("Contact Info:"));
        profilePanel.add(contactField);
        profilePanel.add(new JLabel("Security Level:"));
        profilePanel.add(securityLevelField);
        profilePanel.add(new JLabel());
        profilePanel.add(updateProfileButton);

        updateProfileButton.addActionListener(e -> {
            admin.updateProfile(
                    nameField.getText(),
                    emailField.getText(),
                    contactField.getText()
            );
            try {
                admin.setSecurityLevel(Integer.parseInt(securityLevelField.getText()));
                JOptionPane.showMessageDialog(this, "Profile updated successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a valid number for security level",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        tabbedPane.addTab("My Profile", profilePanel);

        add(tabbedPane, BorderLayout.CENTER);


    }
}
