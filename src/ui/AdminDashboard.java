package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminDashboard extends JFrame {
    private JButton btnManageUsers, btnManageBooks, btnTransactions, btnReports, btnLogout;

    public AdminDashboard() {
        setTitle("📌 Admin Dashboard");
        setSize(400, 300);
        setLayout(new GridLayout(5, 1, 10, 10));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        btnManageUsers = new JButton("👥 Manage Users");
        btnManageBooks = new JButton("📚 Manage Books");
        btnTransactions = new JButton("🔄 Manage Transactions");
        btnReports = new JButton("📊 View Reports");
        btnLogout = new JButton("🚪 Logout");

        add(btnManageUsers);
        add(btnManageBooks);
        add(btnTransactions);
        add(btnReports);
        add(btnLogout);

        // Manage Users
        btnManageUsers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ManageUsers();
            }
        });

        // Manage Books
        btnManageBooks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ManageBooks();
            }
        });

        // Manage Transactions (Issue & Return Books)
        btnTransactions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ManageTransaction();
            }
        });

        // View Reports
        btnReports.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Reports();
            }
        });

        // Logout
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();  // Close Admin Dashboard
                new LoginPage();  // Redirect to Login
            }
        });

        setVisible(true);
    }
}
