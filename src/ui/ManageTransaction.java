package ui;

import dao.TransactionDAO;
import model.Transaction;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;

public class ManageTransaction extends JFrame {
    private JTextField txtUserId, txtBookId;
    private JButton btnIssue;
    private TransactionDAO transactionDAO;

    public ManageTransaction() {
        setTitle("Manage Transactions");
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
        int userId = Integer.parseInt(txtUserId.getText().trim());
        int bookId = Integer.parseInt(txtBookId.getText().trim());
        Date issueDate = new Date(System.currentTimeMillis());
        Date dueDate = new Date(System.currentTimeMillis() + (7L * 24 * 60 * 60 * 1000));

        Transaction transaction = new Transaction(userId, bookId, issueDate, dueDate, "ISSUED");
        transactionDAO.issueBook(transaction);
    }

    public static void main(String[] args) {
        new ManageTransaction();
    }
}
