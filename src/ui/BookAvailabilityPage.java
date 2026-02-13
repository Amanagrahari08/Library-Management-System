package ui;

import dao.BookDAO;
import model.Book;
import util.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.List;

public class BookAvailabilityPage extends JFrame {
    private JTextField txtSearch;
    private JTable bookTable;
    private DefaultTableModel tableModel;
    private BookDAO bookDAO;

    public BookAvailabilityPage() {
        setTitle("Check Book Availability");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Database Connection
        Connection conn = DatabaseConnection.getConnection();
        bookDAO = new BookDAO(conn);

        // **📌 Search Panel**
        JPanel searchPanel = new JPanel();
        txtSearch = new JTextField(20);
        JButton btnSearch = new JButton("Search");
        searchPanel.add(new JLabel("Search Book:"));
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);

        add(searchPanel, BorderLayout.NORTH);

        // **📌 Table for Books**
        tableModel = new DefaultTableModel(new String[]{"ID", "Title", "Author", "Category", "Available Copies"}, 0);
        bookTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(bookTable);
        add(scrollPane, BorderLayout.CENTER);

        // **📌 Search Button Action**
        btnSearch.addActionListener(e -> searchBooks());

        setVisible(true);
    }

    // **📌 Search Books from Database**
    private void searchBooks() {
        String keyword = txtSearch.getText().trim();
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a book title or author!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        tableModel.setRowCount(0);
        List<Book> books = bookDAO.searchBooks(keyword);
        if (books.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No books found!", "Info", JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (Book book : books) {
                tableModel.addRow(new Object[]{
                        book.getBookId(), book.getTitle(), book.getAuthor(), book.getCategoryId(), book.getAvailableCopies()
                });
            }
        }
    }

    public static void main(String[] args) {
        new BookAvailabilityPage();
    }
}
