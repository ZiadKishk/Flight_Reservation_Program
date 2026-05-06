import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrationFrame extends JFrame {
    private JTextField userIdField, usernameField, nameField, emailField, contactField;
    private JPasswordField passwordField, confirmPasswordField;
    private JComboBox<String> roleComboBox;
    private JTextField roleSpecificField;
    private JButton registerButton, cancelButton;

    public RegistrationFrame() {
        setTitle("Flight Booking System - Register");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 102, 204));
        JLabel titleLabel = new JLabel("User Registration");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Center panel with form
        JPanel centerPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        centerPanel.add(new JLabel("User ID:"));
        userIdField = new JTextField();
        centerPanel.add(userIdField);

        centerPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        centerPanel.add(usernameField);

        centerPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        centerPanel.add(passwordField);

        centerPanel.add(new JLabel("Confirm Password:"));
        confirmPasswordField = new JPasswordField();
        centerPanel.add(confirmPasswordField);

        centerPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        centerPanel.add(nameField);

        centerPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        centerPanel.add(emailField);

        centerPanel.add(new JLabel("Contact Info:"));
        contactField = new JTextField();
        centerPanel.add(contactField);

        centerPanel.add(new JLabel("Role:"));
        roleComboBox = new JComboBox<>(new String[]{"Customer", "Agent", "Administrator"});
        roleComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateRoleSpecificField();
            }
        });
        centerPanel.add(roleComboBox);

        centerPanel.add(new JLabel("Role Specific ID:"));
        roleSpecificField = new JTextField();
        centerPanel.add(roleSpecificField);

        add(centerPanel, BorderLayout.CENTER);

        // Footer panel with buttons
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        registerButton = new JButton("Register");
        cancelButton = new JButton("Cancel");

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateFields()) {
                    String userId = userIdField.getText();
                    String username = usernameField.getText();
                    String password = new String(passwordField.getPassword());
                    String name = nameField.getText();
                    String email = emailField.getText();
                    String contactInfo = contactField.getText();
                    String role = (String) roleComboBox.getSelectedItem();
                    String roleSpecificId = roleSpecificField.getText();

                    User newUser = null;
                    switch (role) {
                        case "Customer":
                            newUser = new Customer(userId, username, password, name, email, contactInfo,
                                    roleSpecificId, "", "");
                            break;
                        case "Agent":
                            newUser = new Agent(userId, username, password, name, email, contactInfo,
                                    roleSpecificId, "", 0.0);
                            break;
                        case "Administrator":
                            newUser = new Administrator(userId, username, password, name, email, contactInfo,
                                    roleSpecificId, 1);
                            break;
                    }

                    if (newUser != null) {
                        // Add to BookingSystem
                        BookingSystem.getInstance().addUser(newUser);

                        // Immediately save to file
                        FileHandler.addUserTo(newUser);

                        JOptionPane.showMessageDialog(RegistrationFrame.this,
                                "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    }
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        footerPanel.add(registerButton);
        footerPanel.add(cancelButton);
        add(footerPanel, BorderLayout.SOUTH);

        updateRoleSpecificField();
    }

    private void updateRoleSpecificField() {
        String role = (String) roleComboBox.getSelectedItem();
        switch (role) {
            case "Customer":
                roleSpecificField.setText("CUST" + System.currentTimeMillis());
                break;
            case "Agent":
                roleSpecificField.setText("AGNT" + System.currentTimeMillis());
                break;
            case "Administrator":
                roleSpecificField.setText("ADMN" + System.currentTimeMillis());
                break;
        }
    }

    private boolean validateFields() {
        // Basic validation
        if (usernameField.getText().isEmpty() ||
                new String(passwordField.getPassword()).isEmpty() ||
                nameField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill in all required fields", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!new String(passwordField.getPassword()).equals(new String(confirmPasswordField.getPassword()))) {
            JOptionPane.showMessageDialog(this,
                    "Passwords do not match", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (new String(passwordField.getPassword()).length() < 6 ||
                !new String(passwordField.getPassword()).matches(".*[a-zA-Z].*") ||
                !new String(passwordField.getPassword()).matches(".*[0-9].*")) {
            JOptionPane.showMessageDialog(this,
                    "Password must be at least 6 characters with letters and numbers",
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!emailField.getText().contains("@")) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid email address", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
}