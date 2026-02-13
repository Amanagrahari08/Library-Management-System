package dao;

import model.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    private Connection conn;

    public BookDAO(Connection conn) {
        this.conn = conn;
    }

    // **1️⃣ Add New Book**
    public boolean addBook(Book book) {
        String sql = "INSERT INTO books (title, author, category_id, isbn, total_copies, available_copies) VALUES (?, ?, ?, ?, ?, ?)";

        System.out.println("Inserting book with category_id: " + book.getCategoryId());   // Debugging

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());

            // Ensure category_id is valid
            if (book.getCategoryId() == 0) {
                stmt.setNull(3, java.sql.Types.INTEGER);  // Allow NULL if no category is selected
            } else {
                stmt.setInt(3, book.getCategoryId());
            }

            stmt.setString(4, book.getIsbn());
            stmt.setInt(5, book.getTotalCopies());
            stmt.setInt(6, book.getAvailableCopies());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // **2️⃣ Get All Books**
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                books.add(new Book(
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("category_id"),   // Updated: category_id
                        rs.getString("isbn"),       // Updated: isbn
                        rs.getInt("total_copies"),
                        rs.getInt("available_copies"),
                        rs.getTimestamp("added_date") // Updated: added_date
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    // **3️⃣ Update Book**
    public boolean updateBook(Book book) {
        String sql = "UPDATE books SET title = ?, author = ?, category_id = ?, isbn = ?, total_copies = ?, available_copies = ? WHERE book_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setInt(3, book.getCategoryId());  // Updated: category_id
            stmt.setString(4, book.getIsbn());     // Updated: isbn
            stmt.setInt(5, book.getTotalCopies());
            stmt.setInt(6, book.getAvailableCopies());
            stmt.setInt(7, book.getBookId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // **4️⃣ Delete Book**
    public boolean deleteBook(int bookId) {
        String sql = "DELETE FROM books WHERE book_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Book> searchBooks(String keyword) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ? OR isbn LIKE ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");
            stmt.setString(3, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Book book = new Book(
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("category_id"), // Make sure Book has a categoryId field
                        rs.getString("isbn"),
                        rs.getInt("total_copies"),
                        rs.getInt("available_copies"),
                        rs.getTimestamp("added_date")
                );
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

}
