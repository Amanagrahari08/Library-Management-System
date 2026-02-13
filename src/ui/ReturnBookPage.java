package ui;

import dao.TransactionDAO;
import javax.swing.*;
import java.awt.*;

public class ReturnBookPage extends JFrame {
    private JTextField txtTransactionId;
    private JButton btnReturn;
    private TransactionDAO transactionDAO;

    public ReturnBookPage() {
        setTitle("Return Book");
        setSize(400, 200);
        setLayout(new GridLayout(2, 2, 10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        transactionDAO = new TransactionDAO(); // ✅ FIXED Constructor issue

        add(new JLabel("Transaction ID:"));
        txtTransactionId = new JTextField();
        add(txtTransactionId);

        btnReturn = new JButton("Return Book");
        add(btnReturn);

        btnReturn.addActionListener(e -> returnBook());

        setVisible(true);
    }

    private void returnBook() {
        try {
            int transactionId = Integer.parseInt(txtTransactionId.getText().trim());
            if (transactionDAO.returnBook(transactionId)) {
                JOptionPane.showMessageDialog(this, "Book returned successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to return book!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid Transaction ID!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new ReturnBookPage();
    }
}
