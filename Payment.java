import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Payment {
    public String paymentId;
    public String bookingReference;
    public double amount;
    public String method;
    public String status;
    public String transactionDate;

    public Payment(String paymentId, String bookingReference, double amount, String method) {
        this.paymentId = paymentId;
        this.bookingReference = bookingReference;
        this.amount = amount;
        this.method = method;
        this.status = "Pending";
        this.transactionDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public boolean processPayment() {
        this.status = "Completed";
        return true;
    }

    public boolean validatePaymentDetails() {
        return amount > 0 && !method.isEmpty();
    }

    public void updateStatus(String status) {
        this.status = status;
    }

   
}
