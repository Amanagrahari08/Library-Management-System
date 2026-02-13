package ui;

import dao.ReportDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Reports extends JFrame {
    private JButton btnActiveIssues, btnMembershipList, btnOverdueReturns, btnPendingRequests, btnBack;
    private JTextArea reportArea;
    private ReportDAO reportDAO;

    public Reports() {
        reportDAO = new ReportDAO();
        setTitle("📊 Reports");
        setSize(500, 400);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Buttons Panel
        JPanel panelButtons = new JPanel();
        panelButtons.setLayout(new GridLayout(5, 1, 5, 5));

        btnActiveIssues = new JButton("📌 Active Issues");
        btnMembershipList = new JButton("📋 Membership & Books");
        btnOverdueReturns = new JButton("⚠ Overdue Returns");
        btnPendingRequests = new JButton("⏳ Pending Issue Requests");
        btnBack = new JButton("🔙 Back");

        panelButtons.add(btnActiveIssues);
        panelButtons.add(btnMembershipList);
        panelButtons.add(btnOverdueReturns);
        panelButtons.add(btnPendingRequests);
        panelButtons.add(btnBack);

        // Report Display Area
        reportArea = new JTextArea();
        reportArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(reportArea);

        add(panelButtons, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);

        // Active Issues
        btnActiveIssues.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> activeIssues = reportDAO.getActiveIssues();
                displayReport("📌 Active Issues", activeIssues);
            }
        });

        // Membership List
        btnMembershipList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> membershipList = reportDAO.getMembershipList();
                displayReport("📋 Membership & Books", membershipList);
            }
        });

        // Overdue Returns
        btnOverdueReturns.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> overdueReturns = reportDAO.getOverdueReturns();
                displayReport("⚠ Overdue Returns", overdueReturns);
            }
        });

        // Pending Issue Requests
        btnPendingRequests.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> pendingRequests = reportDAO.getPendingIssueRequests();
                displayReport("⏳ Pending Issue Requests", pendingRequests);
            }
        });

        // Back Button
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the Reports Window
            }
        });

        setVisible(true);
    }

    // Function to display report data
    private void displayReport(String title, List<String> data) {
        StringBuilder reportText = new StringBuilder(title + "\n\n");
        if (data.isEmpty()) {
            reportText.append("No records found.");
        } else {
            for (String record : data) {
                reportText.append(record).append("\n");
            }
        }
        reportArea.setText(reportText.toString());
    }
}
