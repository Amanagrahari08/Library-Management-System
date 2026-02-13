package ui;

import javax.swing.*;
import java.awt.*;

public class AdminPage extends JFrame {
    public AdminPage(JFrame previousFrame) {
        setTitle("Admin Dashboard - Library Management System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1, 10, 10)); // Increased row count for Back Button

        JButton manageUsersButton = new JButton("Manage Users");
        JButton manageBooksButton = new JButton("Manage Books");
        JButton viewReportsButton = new JButton("View Reports");
        JButton logoutButton = new JButton("Logout");
        JButton backButton = new JButton("Back"); // Back Button

        add(manageUsersButton);
        add(manageBooksButton);
        add(viewReportsButton);
        add(logoutButton);
        add(backButton); // Adding Back Button

        // Actions
        manageUsersButton.addActionListener(e -> new UserPage());
        manageBooksButton.addActionListener(e -> new ManageBooks());
        viewReportsButton.addActionListener(e -> new ReportsPage());

        logoutButton.addActionListener(e -> {
            dispose();
            new LoginPage();
        });

        backButton.addActionListener(e -> {
            if (previousFrame != null) {
                previousFrame.setVisible(true);
                dispose();
            }
        });

        setVisible(true);
    }
}
