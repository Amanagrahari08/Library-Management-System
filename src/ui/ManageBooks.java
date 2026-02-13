package ui;

import dao.BookDAO;
import model.Book;
import util.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class ManageBooks extends JFrame {
    private JTextField txtTitle, txtAuthor, txtCategoryId, txtISBN, txtTotalCopies, txtAvailableCopies;
    private JTable bookTable;
    private DefaultTableModel tableModel;
    private BookDAO bookDAO;

    public ManageBooks() {
        setTitle("Manage Books");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        Connection conn = DatabaseConnection.getConnection();
        bookDAO = new BookDAO(conn);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));

        panel.add(new JLabel("Title:"));
        txtTitle = new JTextField();
        panel.add(txtTitle);

        panel.add(new JLabel("Author:"));
        txtAuthor = new JTextField();
        panel.add(txtAuthor);

        panel.add(new JLabel("Category ID:"));
        txtCategoryId = new JTextField();
        panel.add(txtCategoryId);

        panel.add(new JLabel("ISBN:"));
        txtISBN = new JTextField();
        panel.add(txtISBN);

        panel.add(new JLabel("Total Copies:"));
        txtTotalCopies = new JTextField();
        panel.add(txtTotalCopies);

        panel.add(new JLabel("Available Copies:"));
        txtAvailableCopies = new JTextField();
        panel.add(txtAvailableCopies);

        add(panel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        JButton btnAdd = new JButton("Add Book");
        JButton btnUpdate = new JButton("Update Book");
        JButton btnDelete = new JButton("Delete Book");
        JButton btnRefresh = new JButton("Refresh");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnRefresh);

        add(buttonPanel, BorderLayout.SOUTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Title", "Author", "Category ID", "ISBN", "Total", "Available"}, 0);
        bookTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(bookTable);
        add(scrollPane, BorderLayout.CENTER);

        loadBooks();

        btnAdd.addActionListener(e -> addBook());
        btnUpdate.addActionListener(e -> updateBook());
        btnDelete.addActionListener(e -> deleteBook());
        btnRefresh.addActionListener(e -> loadBooks());

        setVisible(true);
    }

    // ✅ Load books into table
    private void loadBooks() {
        tableModel.setRowCount(0);
        List<Book> books = bookDAO.getAllBooks();
        for (Book book : books) {
            tableModel.addRow(new Object[]{book.getBookId(), book.getTitle(), book.getAuthor(), book.getCategoryId(), book.getIsbn(), book.getTotalCopies(), book.getAvailableCopies()});
        }
    }

    // ✅ Add Book
    private void addBook() {
        Book book = new Book(0, txtTitle.getText(), txtAuthor.getText(), Integer.parseInt(txtCategoryId.getText()), txtISBN.getText(), Integer.parseInt(txtTotalCopies.getText()), Integer.parseInt(txtAvailableCopies.getText()), null);
        if (bookDAO.addBook(book)) {
            JOptionPane.showMessageDialog(this, "Book added successfully!");
            loadBooks();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add book!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ✅ Update Book
    private void updateBook() {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a book to update!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int bookId = (int) bookTable.getValueAt(selectedRow, 0);
        Book book = new Book(
                bookId,
                txtTitle.getText(),
                txtAuthor.getText(),
                Integer.parseInt(txtCategoryId.getText()),
                txtISBN.getText(),
                Integer.parseInt(txtTotalCopies.getText()),
                Integer.parseInt(txtAvailableCopies.getText()),
                null
        );

        if (bookDAO.updateBook(book)) {
            JOptionPane.showMessageDialog(this, "Book updated successfully!");
            loadBooks();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update book!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    

    // ✅ Delete Book
    private void deleteBook() {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a book to delete!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int bookId = (int) bookTable.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this book?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (bookDAO.deleteBook(bookId)) {
                JOptionPane.showMessageDialog(this, "Book deleted successfully!");
                loadBooks();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete book!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    public static void main(String[] args) {
        new ManageBooks();
    }
}
