package model;

import java.sql.Date;

public class Transaction {
    private int transactionId;
    private int userId;
    private int bookId;
    private Date issueDate;
    private Date dueDate;
    private Date returnDate;
    private double finePaid;
    private String status;

    // ✅ Constructor for issuing a book (No return date & fine)
    public Transaction(int userId, int bookId, Date issueDate, Date dueDate, String status) {
        this.userId = userId;
        this.bookId = bookId;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.status = status;
    }

    // ✅ Constructor for returning a book (With return date & fine)
    public Transaction(int transactionId, int userId, int bookId, Date issueDate, Date dueDate, Date returnDate, double finePaid, String status) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.bookId = bookId;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.finePaid = finePaid;
        this.status = status;
    }

    // ✅ Getters and Setters (No changes)
    public int getTransactionId() { return transactionId; }
    public void setTransactionId(int transactionId) { this.transactionId = transactionId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }
    public Date getIssueDate() { return issueDate; }
    public void setIssueDate(Date issueDate) { this.issueDate = issueDate; }
    public Date getDueDate() { return dueDate; }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }
    public Date getReturnDate() { return returnDate; }
    public void setReturnDate(Date returnDate) { this.returnDate = returnDate; }
    public double getFinePaid() { return finePaid; }
    public void setFinePaid(double finePaid) { this.finePaid = finePaid; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
