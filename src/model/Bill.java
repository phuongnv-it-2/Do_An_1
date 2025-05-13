package model;
import java.util.Date;

public class Bill {
    private int billID;
    private int customerID;
    private int employeeID;
    private Date billDate;
    private double totalAmount;
    private String paymentMethod;
    private String status;

    public Bill() {
    }

    public Bill(int billID, int customerID, int employeeID, Date billDate,
                double totalAmount, String paymentMethod, String status) {
        this.billID = billID;
        this.customerID = customerID;
        this.employeeID = employeeID;
        this.billDate = billDate;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.status = status;
    }

    // Getter & Setter
    public int getBillID() {
        return billID;
    }

    public void setBillID(int billID) {
        this.billID = billID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
