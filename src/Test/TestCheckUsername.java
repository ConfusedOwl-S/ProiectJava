package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import p1.DatabaseManipulation;
import p1.Utilizator;

class TestCheckUsername {

	private static Connection conn;

    @BeforeAll
    public static void setUp() throws SQLException {
        // Create an in-memory Derby database for testing
        conn = DriverManager.getConnection("jdbc:derby://localhost:1527/TestBD;create=true");
    }

    @AfterEach
    public void cleanUp() throws SQLException {
        // Clean up tables after each test
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("DELETE FROM Users");
        stmt.executeUpdate("DELETE FROM Animale");
        stmt.executeUpdate("DELETE FROM Adoptions");
    }

    @AfterAll
    public static void tearDown() throws SQLException {
        conn.close();
    }

    @Test
    public void testCheckUsername_ValidUser() throws SQLException {
        // Insert a test user
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("INSERT INTO Users(username, password, isAdmin) VALUES('admin', '1234', true)");

        // Test the checkUsername method
        boolean exists = DatabaseManipulation.checkUsername("admin");
        assertTrue(exists, "Username should exist in the database.");
    }

    @Test
    public void testCheckUsername_InvalidUser() {
        boolean exists = DatabaseManipulation.checkUsername("nonexistentUser");
        assertFalse(exists, "Username should not exist in the database.");
    }

    @Test
    public void testRegisterUser() throws SQLException {
        Utilizator user = new Utilizator("johnDoe", "password123", false);
        boolean success = DatabaseManipulation.registerUser(user);

        assertTrue(success, "User should be successfully registered.");
        
        // Check that user was inserted into the database
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM Users WHERE username = 'johnDoe'");
        rs.next();
        assertEquals(1, rs.getInt(1), "User should be inserted into the database.");
    }
}
