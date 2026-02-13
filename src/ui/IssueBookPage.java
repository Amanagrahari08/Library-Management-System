package ui;

import dao.TransactionDAO;
import model.Transaction;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;

public class IssueBookPage extends JFrame {
    private JTextField txtUserId, txtBookId;
    private JButton btnIssue;
    private TransactionDAO transactionDAO;

    public IssueBookPage() {
        setTitle("Issue Book");
        setSize(400, 300);
        setLayout(new GridLayout(3, 2, 10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        transactionDAO = new TransactionDAO();

        add(new JLabel("User ID:"));
        txtUserId = new JTextField();
        add(txtUserId);

        add(new JLabel("Book ID:"));
        txtBookId = new JTextField();
        add(txtBookId);

        btnIssue = new JButton("Issue Book");
        add(btnIssue);

        btnIssue.addActionListener(e -> issueBook());

        setVisible(true);
    }

    private void issueBook() {
        try {
            int userId = Integer.parseInt(txtUserId.getText().trim());
            int bookId = Integer.parseInt(txtBookId.getText().trim());
            Date issueDate = new Date(System.currentTimeMillis());
            Date dueDate = new Date(System.currentTimeMillis() + (7L * 24 * 60 * 60 * 1000)); // 7 days from now

            Transaction transaction = new Transaction(userId, bookId, issueDate, dueDate, "ISSUED");

            if (transactionDAO.issueBook(transaction)) {
                JOptionPane.showMessageDialog(this, "Book issued successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to issue book!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid User ID or Book ID!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new IssueBookPage();
    }
}
