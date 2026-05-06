public class Administrator extends User {
    private String adminId;
    private int securityLevel;

    public Administrator(String userId, String username, String password, String name, String email, String contactInfo,
                         String adminId, int securityLevel) {
        super(userId, username, password, name, email, contactInfo);
        this.adminId = adminId;
        this.securityLevel = securityLevel;
    }

    @Override
    public void showDashboard() {
        System.out.println("Administrator Dashboard");
    }

    @Override
    public void bookFlight(Flight selectedFlight) {

    }

    public User createUser(String userId, String username, String password, String name, String email,
                           String contactInfo, String role, String additionalId) {
        switch (role.toLowerCase()) {
            case "customer":
                return new Customer(userId, username, password, name, email, contactInfo,
                        additionalId, "", "");
            case "agent":
                return new Agent(userId, username, password, name, email, contactInfo,
                        additionalId, "", 0.0);
            case "administrator":
                return new Administrator(userId, username, password, name, email, contactInfo,
                        additionalId, 1);
            default:
                return null;
        }
    }

    public boolean modifySystemSettings(String setting, String value) {
        return true;
    }





    // Getters and setters
    public String getAdminId() {
        return adminId;
    }

    public int getSecurityLevel() {
        return securityLevel;
    }

    public void setSecurityLevel(int securityLevel) {
        this.securityLevel = securityLevel;
    }
}
