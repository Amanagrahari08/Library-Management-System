package ui;

import dao.ReportDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReportsPage extends JFrame {
    private JButton btnActiveIssues, btnMasterList, btnOverdue, btnBack;
    private JTextArea reportArea;
    private ReportDAO reportDAO;

    public ReportsPage() {
        reportDAO = new ReportDAO();
        setTitle("Reports Section");
        setSize(600, 400);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Top Panel for Buttons
        JPanel topPanel = new JPanel();
        btnActiveIssues = new JButton("📌 Active Issues");
        btnMasterList = new JButton("📌 Master List");
        btnOverdue = new JButton("📌 Overdue Returns");
        btnBack = new JButton("🔙 Back");

        topPanel.add(btnActiveIssues);
        topPanel.add(btnMasterList);
        topPanel.add(btnOverdue);
        topPanel.add(btnBack);

        // Report Area
        reportArea = new JTextArea();
        reportArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(reportArea);

        // Add Components
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Button Actions
        btnActiveIssues.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reportArea.setText("");
                reportDAO.getActiveIssues();
            }
        });

        btnMasterList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reportArea.setText("");
                reportDAO.getMasterList();
            }
        });

        btnOverdue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reportArea.setText("");
                reportDAO.getOverdueReturns();
            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the window
            }
        });

        setVisible(true);
    }
}
