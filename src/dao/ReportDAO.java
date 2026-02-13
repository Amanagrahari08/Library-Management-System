package dao;

import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportDAO {
    private Connection conn;

    public ReportDAO() {
        conn = DatabaseConnection.getConnection();
    }

    // 1️⃣ Get Active Issues
    public List<String> getActiveIssues() {
        List<String> issues = new ArrayList<>();
        String sql = "SELECT t.transaction_id, u.full_name, b.title, t.issue_date, t.due_date " +
                "FROM transactions t " +
                "JOIN users u ON t.user_id = u.user_id " +
                "JOIN books b ON t.book_id = b.book_id " +
                "WHERE t.return_date IS NULL";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                issues.add("Transaction ID: " + rs.getInt("transaction_id") +
                        " | User: " + rs.getString("full_name") +
                        " | Book: " + rs.getString("title") +
                        " | Issued: " + rs.getDate("issue_date") +
                        " | Due: " + rs.getDate("due_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return issues;
    }

    // 2️⃣ Get Membership & Books List
    public List<String> getMembershipList() {
        List<String> memberships = new ArrayList<>();
        String sql = "SELECT user_id, full_name, email, membership_status FROM users";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                memberships.add("User ID: " + rs.getInt("user_id") +
                        " | Name: " + rs.getString("full_name") +
                        " | Email: " + rs.getString("email") +
                        " | Status: " + rs.getString("membership_status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return memberships;
    }

    // 3️⃣ Get Overdue Returns
    public List<String> getOverdueReturns() {
        List<String> overdue = new ArrayList<>();
        String sql = "SELECT t.transaction_id, u.full_name, b.title, t.due_date " +
                "FROM transactions t " +
                "JOIN users u ON t.user_id = u.user_id " +
                "JOIN books b ON t.book_id = b.book_id " +
                "WHERE t.return_date IS NULL AND t.due_date < CURDATE()";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                overdue.add("Transaction ID: " + rs.getInt("transaction_id") +
                        " | User: " + rs.getString("full_name") +
                        " | Book: " + rs.getString("title") +
                        " | Due Date: " + rs.getDate("due_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return overdue;
    }

    // 4️⃣ Get Pending Issue Requests
    public List<String> getPendingIssueRequests() {
        List<String> pending = new ArrayList<>();
        String sql = "SELECT r.request_id, u.full_name, b.title, r.request_date " +
                "FROM issue_requests r " +
                "JOIN users u ON r.user_id = u.user_id " +
                "JOIN books b ON r.book_id = b.book_id " +
                "WHERE r.status = 'PENDING'";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                pending.add("Request ID: " + rs.getInt("request_id") +
                        " | User: " + rs.getString("full_name") +
                        " | Book: " + rs.getString("title") +
                        " | Requested On: " + rs.getDate("request_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pending;
    }

    // 5️⃣ Get Master List of Users, Books, and Movies
    public List<String> getMasterList() {
        List<String> masterList = new ArrayList<>();
        String sql = "SELECT 'User' AS type, user_id AS id, full_name AS name FROM users " +
                "UNION ALL " +
                "SELECT 'Book', book_id, title FROM books " +
                "UNION ALL " +
                "SELECT 'Movie', movie_id, title FROM movies";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                masterList.add("Type: " + rs.getString("type") +
                        " | ID: " + rs.getInt("id") +
                        " | Name: " + rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return masterList;
    }

}
