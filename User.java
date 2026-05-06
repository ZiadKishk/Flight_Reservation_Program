import java.util.List;

public abstract class User {
    private String userId;
    private String username;
    private String password;
    private String name;
    private String email;
    private String contactInfo;

    public User(String userId, String username, String password, String name, String email, String contactInfo) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.contactInfo = contactInfo;
    }

    public abstract void showDashboard();

    // Getters and setters
    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username != null && !username.isEmpty()) {
            this.username = username;
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password != null && password.length() >= 6 && password.matches(".*[a-zA-Z].*") && password.matches(".*[0-9].*")) {
            this.password = password;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null && !name.isEmpty()) {
            this.name = name;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email != null && email.contains("@")) {
            this.email = email;
        }
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        if (contactInfo != null && !contactInfo.isEmpty()) {
            this.contactInfo = contactInfo;
        }
    }

    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public void logout() {
        System.out.println("User logged out successfully.");
    }

    public void updateProfile(String name, String email, String contactInfo) {
        setName(name);
        setEmail(email);
        setContactInfo(contactInfo);
    }

    public abstract void bookFlight(Flight selectedFlight);

    public void saveToFile() {
        String userData = String.join(",",
                this.userId,
                this.username,
                this.password,
                this.name,
                this.email,
                this.contactInfo,
                this.getClass().getSimpleName()
        );

    }

    public static User loadFromFile(String userId) {
        List<String> userLines = FileHandler.readFrom("users.txt");
        for (String line : userLines) {
            String[] parts = line.split(",");
            if (parts[0].equals(userId)) {
                // Create the appropriate user type
                switch (parts[6]) {
                    case "Customer":
                        return new Customer(parts[0], parts[1], parts[2], parts[3],
                                parts[4], parts[5], "", "", "");
                    case "Agent":
                        return new Agent(parts[0], parts[1], parts[2], parts[3],
                                parts[4], parts[5], "", "", 0.0);
                    case "Administrator":
                        return new Administrator(parts[0], parts[1], parts[2], parts[3],
                                parts[4], parts[5], "", 0);
                }
            }
        }
        return null;
    }
}