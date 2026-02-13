package ui;

import dao.UserDAO;
import model.User;
import util.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.List;

public class ManageUsers extends JFrame {
    private JTextField txtUsername, txtFullName, txtEmail, txtPhone, txtRole;
    private JPasswordField txtPassword;
    private JTable userTable;
    private DefaultTableModel tableModel;
    private UserDAO userDAO;

    public ManageUsers() {
        setTitle("Manage Users");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Database Connection
        Connection conn = DatabaseConnection.getConnection();
        userDAO = new UserDAO(conn);

        // **📌 UI Components**
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));

        panel.add(new JLabel("Username:"));
        txtUsername = new JTextField();
        panel.add(txtUsername);

        panel.add(new JLabel("Password:"));
        txtPassword = new JPasswordField();
        panel.add(txtPassword);

        panel.add(new JLabel("Full Name:"));
        txtFullName = new JTextField();
        panel.add(txtFullName);

        panel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panel.add(txtEmail);

        panel.add(new JLabel("Phone:"));
        txtPhone = new JTextField();
        panel.add(txtPhone);

        panel.add(new JLabel("Role (ADMIN/USER):"));
        txtRole = new JTextField();
        panel.add(txtRole);

        add(panel, BorderLayout.NORTH);

        // **📌 Buttons**
        JPanel buttonPanel = new JPanel();
        JButton btnAdd = new JButton("Add User");
        JButton btnUpdate = new JButton("Update User");
        JButton btnDelete = new JButton("Delete User");
        JButton btnRefresh = new JButton("Refresh");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnRefresh);

        add(buttonPanel, BorderLayout.SOUTH);

        // **📌 Table for Users**
        tableModel = new DefaultTableModel(new String[]{"ID", "Username", "Full Name", "Email", "Phone", "Role"}, 0);
        userTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(userTable);
        add(scrollPane, BorderLayout.CENTER);

        // **📌 Load Users Initially**
        loadUsers();

        // **📌 Button Actions**
        btnAdd.addActionListener(e -> addUser());
        btnUpdate.addActionListener(e -> updateUser());
        btnDelete.addActionListener(e -> deleteUser());
        btnRefresh.addActionListener(e -> loadUsers());

        setVisible(true);
    }

    // **📌 Load Users in Table**
    private void loadUsers() {
        tableModel.setRowCount(0);
        List<User> users = userDAO.getAllUsers();
        for (User user : users) {
            tableModel.addRow(new Object[]{
                    user.getUserId(), user.getUsername(), user.getFullName(), user.getEmail(), user.getPhone(), user.getRole()
            });
        }
    }

    // **📌 Add User**
    private void addUser() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());
        String fullName = txtFullName.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();
        String role = txtRole.getText();

        if (username.isEmpty() || password.isEmpty() || fullName.isEmpty() || email.isEmpty() || phone.isEmpty() || role.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        User user = new User(0, username, password, fullName, email, phone, role, "ACTIVE");
        if (userDAO.addUser(user)) {
            JOptionPane.showMessageDialog(this, "User Added Successfully!");
            loadUsers();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to Add User!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // **📌 Update User**
    private void updateUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a user to update!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int userId = (int) tableModel.getValueAt(selectedRow, 0);
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());
        String fullName = txtFullName.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();
        String role = txtRole.getText();

        User user = new User(userId, username, password, fullName, email, phone, role, "ACTIVE");
        if (userDAO.updateUser(user)) {
            JOptionPane.showMessageDialog(this, "User Updated Successfully!");
            loadUsers();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to Update User!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // **📌 Delete User**
    private void deleteUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a user to delete!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int userId = (int) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this user?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (userDAO.deleteUser(userId)) {
                JOptionPane.showMessageDialog(this, "User Deleted Successfully!");
                loadUsers();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to Delete User!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        new ManageUsers();
    }
}
