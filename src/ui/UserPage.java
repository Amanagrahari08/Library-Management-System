package ui;

import javax.swing.*;
import java.awt.*;

public class UserPage extends JFrame {
    public UserPage() {
        setTitle("User Dashboard - Library Management System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1, 10, 10));

        JButton checkAvailabilityButton = new JButton("Check Book Availability");
        JButton issueBookButton = new JButton("Issue Book");
        JButton returnBookButton = new JButton("Return Book");

        add(checkAvailabilityButton);
        add(issueBookButton);
        add(returnBookButton);

       checkAvailabilityButton.addActionListener(e -> new BookAvailabilityPage());
       issueBookButton.addActionListener(e -> new IssueBookPage());
       returnBookButton.addActionListener(e -> new ReturnBookPage());

        setVisible(true);
    }
}
