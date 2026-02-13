package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserDashboard extends JFrame {
    private JButton btnCheckBooks, btnTransactions, btnLogout;

    public UserDashboard() {
        setTitle("📌 User Dashboard");
        setSize(300, 200);
        setLayout(new GridLayout(3, 1, 10, 10));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        btnCheckBooks = new JButton("📖 View Books");
        btnTransactions = new JButton("📑 My Transactions");
        btnLogout = new JButton("🚪 Logout");

        add(btnCheckBooks);
        add(btnTransactions);
        add(btnLogout);

        // View Books
        btnCheckBooks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ManageBooks();  // Users can only view books
            }
        });

        // My Transactions (Issued Books & Returns)
        btnTransactions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ManageTransaction();
            }
        });

        // Logout
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();  // Close User Dashboard
                new LoginPage();  // Redirect to Login
            }
        });

        setVisible(true);
    }
}
