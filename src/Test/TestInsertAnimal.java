package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import p1.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TestInsertAnimal {

	private static Connection conn;

    @BeforeAll
    public static void setUp() throws SQLException {
        // Create an in-memory Derby database for testing
        conn = DriverManager.getConnection("jdbc:derby://localhost:1527/testBD;create=true");
    }

    @AfterEach
    public void cleanUp() throws SQLException {
    	Statement stmt = conn.createStatement();

        // Truncate both tables if possible
        stmt.executeUpdate("TRUNCATE TABLE Adoptions");
        stmt.executeUpdate("TRUNCATE TABLE Animale");

        // Optionally, verify
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM Animale");
        rs.next();
        System.out.println("Remaining animals: " + rs.getInt(1));

        rs = stmt.executeQuery("SELECT COUNT(*) FROM Adoptions");
        rs.next();
        System.out.println("Remaining adoptions: " + rs.getInt(1));
    }

    @AfterAll
    public static void tearDown() throws SQLException {
        conn.close();
    }

    @Test
    public void testAddAnimal() throws SQLException {
        boolean success = DatabaseManipulation.addAnimal("Rex", "Dog", "Disno", "M", "Healthy", "Big cat", "base64encodedimage");

        assertTrue(success, "Animal should be successfully added.");
        
        // Verify that the animal was added
        List<Animal> animals = DatabaseManipulation.getAnimalsFromDatabase();
        assertEquals(1, animals.size(), "One animal should be in the database.");
        
     // Clean up manually for this test
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("DELETE FROM Adoptions");  // Clean up dependent records
        stmt.executeUpdate("DELETE FROM Animale");  // Clean up animal records
    }

    @Test
    public void testDeleteAnimalById() throws SQLException {
        // Add an animal first
        boolean added = DatabaseManipulation.addAnimal("Rex", "Dog", "Disno", "M", "Healthy", "Big cat", "base64encodedimage");
        assertTrue(added, "Animal should be successfully added.");

        // Get animal ID
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT id FROM Animale WHERE nume = 'Rex'");
        rs.next();
        int animalId = rs.getInt("id");

        // Now delete the animal
        boolean deleted = DatabaseManipulation.deleteAnimalById(animalId);
        assertTrue(deleted, "Animal should be successfully deleted.");

        // Verify deletion
        rs = stmt.executeQuery("SELECT COUNT(*) FROM Animale WHERE id = " + animalId);
        rs.next();
        assertEquals(0, rs.getInt(1), "Animal should no longer exist in the database.");
    }
}
