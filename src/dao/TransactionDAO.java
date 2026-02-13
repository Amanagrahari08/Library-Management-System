package dao;

import model.Transaction;
import util.DatabaseConnection;

import java.sql.*;

public class TransactionDAO {
    private Connection conn;

    public TransactionDAO() {
        this.conn = DatabaseConnection.getConnection();
    }

    // ✅ Issue a Book (with available_copies update)
    public boolean issueBook(Transaction transaction) {
        String checkAvailability = "SELECT available_copies FROM books WHERE book_id = ?";
        String insertTransaction = "INSERT INTO transactions (user_id, book_id, issue_date, due_date, status) VALUES (?, ?, ?, ?, 'ISSUED')";
        String updateBookCopies = "UPDATE books SET available_copies = available_copies - 1 WHERE book_id = ?";

        try {
            conn.setAutoCommit(false); // Start transaction

            // Step 1: Check if book is available
            try (PreparedStatement checkStmt = conn.prepareStatement(checkAvailability)) {
                checkStmt.setInt(1, transaction.getBookId());
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    int available = rs.getInt("available_copies");
                    if (available <= 0) {
                        System.out.println("Book is not available.");
                        conn.rollback();
                        return false;
                    }
                } else {
                    System.out.println("Book not found.");
                    conn.rollback();
                    return false;
                }
            }

            // Step 2: Insert the transaction
            try (PreparedStatement insertStmt = conn.prepareStatement(insertTransaction)) {
                insertStmt.setInt(1, transaction.getUserId());
                insertStmt.setInt(2, transaction.getBookId());
                insertStmt.setDate(3, transaction.getIssueDate());
                insertStmt.setDate(4, transaction.getDueDate());
                insertStmt.executeUpdate();
            }

            // Step 3: Reduce available_copies
            try (PreparedStatement updateStmt = conn.prepareStatement(updateBookCopies)) {
                updateStmt.setInt(1, transaction.getBookId());
                updateStmt.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        }
        return false;
    }

    // ✅ Return a Book (with available_copies increase)
    public boolean returnBook(int transactionId) {
        String getBookId = "SELECT book_id FROM transactions WHERE transaction_id = ?";
        String updateTransaction = "UPDATE transactions SET return_date = CURDATE(), status = 'RETURNED' WHERE transaction_id = ?";
        String updateBookCopies = "UPDATE books SET available_copies = available_copies + 1 WHERE book_id = ?";

        try {
            conn.setAutoCommit(false); // Start transaction

            int bookId = -1;
            try (PreparedStatement stmt = conn.prepareStatement(getBookId)) {
                stmt.setInt(1, transactionId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    bookId = rs.getInt("book_id");
                } else {
                    System.out.println("Transaction not found.");
                    conn.rollback();
                    return false;
                }
            }

            // Step 1: Update the transaction status
            try (PreparedStatement stmt = conn.prepareStatement(updateTransaction)) {
                stmt.setInt(1, transactionId);
                stmt.executeUpdate();
            }

            // Step 2: Increase available copies
            try (PreparedStatement stmt = conn.prepareStatement(updateBookCopies)) {
                stmt.setInt(1, bookId);
                stmt.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        }

        return false;
    }
}