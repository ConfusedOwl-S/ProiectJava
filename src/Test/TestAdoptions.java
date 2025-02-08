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

import p1.Adoptie;
import p1.DatabaseManipulation;

class TestAdoptions {

	private static Connection conn;

    @BeforeAll
    public static void setUp() throws SQLException {
        // Create an in-memory Derby database for testing
        conn = DriverManager.getConnection("jdbc:derby://localhost:1527/testBD;create=true");
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
    public void testApplyAnimalAdoption() throws SQLException {
    	 // Add an animal for adoption
        boolean added = DatabaseManipulation.addAnimal("Rex", "Dog", "Disno", "M", "Healthy", "Big cat", "base64encodedimage");
        assertTrue(added, "Animal should be successfully added.");

        // Get the ID of the added animal
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT id FROM Animale WHERE nume = 'Rex'");
        
        // Ensure that we have a result
        if (rs.next()) {
            int animalId = rs.getInt("id");

            // Apply for adoption
            boolean adoptionApplied = DatabaseManipulation.applyAnimalAdoption("John Doe", 1234567890, "123 Main St", animalId);
            assertTrue(adoptionApplied, "Adoption should be successfully applied.");
        } else {
            fail("No animal found with the name 'Rex'");
        }
        
        // Close resources
        rs.close();
        stmt.close();
    }

    @Test
    public void testApproveAdoption() throws SQLException {
        // Set up adoption
        boolean added = DatabaseManipulation.addAnimal("Rex", "Dog", "Disno", "M", "Healthy", "Big cat", "base64encodedimage");
        assertTrue(added, "Animal should be added successfully.");
        
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT id FROM Animale WHERE nume = 'Rex'");
        rs.next();
        int animalId = rs.getInt("id");

        DatabaseManipulation.applyAnimalAdoption("John Doe", 1234567890, "123 Main St", animalId);

        // Fetch adoption and approve
        rs = stmt.executeQuery("SELECT * FROM Adoptions WHERE idAnimal = " + animalId);
        rs.next();
        int adoptionId = rs.getInt("id");

        Adoptie adoption = new Adoptie(adoptionId, "John Doe", 1234567890, "123 Main St", animalId, "Rex", new java.sql.Date(System.currentTimeMillis()), false);

        // Approve adoption
        DatabaseManipulation.approveAdoption(adoption);

        // Check if adoption was approved
        rs = stmt.executeQuery("SELECT aprobare FROM Adoptions WHERE id = " + adoptionId);
        rs.next();
        assertTrue(rs.getBoolean("aprobare"), "Adoption should be approved.");
    }

    @Test
    public void testRejectAdoption() throws SQLException {
        // Set up adoption
        boolean added = DatabaseManipulation.addAnimal("Rex", "Dog", "Disno", "M", "Healthy", "Big cat", "base64encodedimage");
        assertTrue(added, "Animal should be added successfully.");
        
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT id FROM Animale WHERE nume = 'Rex'");
        rs.next();
        int animalId = rs.getInt("id");

        DatabaseManipulation.applyAnimalAdoption("John Doe", 1234567890, "123 Main St", animalId);

        // Fetch adoption and reject
        rs = stmt.executeQuery("SELECT * FROM Adoptions WHERE idAnimal = " + animalId);
        rs.next();
        int adoptionId = rs.getInt("id");

        Adoptie adoption = new Adoptie(adoptionId, "John Doe", 1234567890, "123 Main St", animalId, "Rex", new java.sql.Date(System.currentTimeMillis()), false);

        // Reject adoption
        DatabaseManipulation.rejectAdoption(adoption);

        // Verify rejection
        rs = stmt.executeQuery("SELECT COUNT(*) FROM Adoptions WHERE id = " + adoptionId);
        rs.next();
        assertEquals(0, rs.getInt(1), "Adoption should be removed from the database.");
    }
}
