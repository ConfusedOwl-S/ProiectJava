package p1;

//import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * Clasa ce contine metodele folosite pentru manipularea bazei de date folosita in aplicatie 'BazaDeDate'
 */
public class DatabaseManipulation {
	//Userul conectat curent
	public static Utilizator firstUser;
	
	/**
	 * Metoda ce verifica daca un tabel dat exista in baza de date
	 * @param conn
	 * @param tableName
	 * @return true / false
	 */
	public static boolean existentaTabel(Connection conn, String tableName) {
	    try (ResultSet rs = conn.getMetaData().getTables(null, "APP", tableName.toUpperCase(), null)) {
	        return rs.next();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
	/**
	 * Metoda main face conexiunea la baza de date si creaza tabelele folosite daca acestea nu exista deja
	 * @param args
	 */
	public static void main(String[] args) {
	 try (Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/BazaDeDate;create=true")) {
         if (conn != null) {
             try (Statement statement = conn.createStatement()) {
                 if (!existentaTabel(conn,"Users")) {
					// Se creaza tabelul 'Users'
					statement.executeUpdate("CREATE TABLE Users(" + 
					"username VARCHAR(50) PRIMARY KEY," + 
					"password VARCHAR(255) NOT NULL," + 
					"isAdmin BOOLEAN NOT NULL" + 
					")");
					System.out.println("Tabel Users a fost creat");
					// Insereaza un user default 'admin'
					statement.executeUpdate(
							"INSERT INTO Users(username, password, isAdmin) VALUES('admin', '1234', true)");
					System.out.println("Admin user adaugat");
                 }
                 
				if (!existentaTabel(conn, "Animale")) {
					// Se creaza un tabel 'Animale'
					statement.executeUpdate("CREATE TABLE Animale(" +
					"id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
					"nume VARCHAR(255), " + 
					"specie VARCHAR(255), " + 
					"rasa VARCHAR(255), " + 
					"sex CHAR(1), " + 
					"dataSosire DATE, " + 
					"stareSanatate VARCHAR(255), " + 
					"descriereFizica VARCHAR(255), " + 
					"stareAdoptie BOOLEAN, " +
					"picture VARCHAR(32672))"); // Store Base64 string (adjust size based on image size)
					System.out.println("Tabel Animale a fost creat");
				}
				else
				{
					System.out.println("Tabelul exista!(Animale)");
				}
				
				if (!existentaTabel(conn, "Adoptions")) {
					// Se creaza un tabel 'Animale'
					statement.executeUpdate("CREATE TABLE Adoptions (" +
						    "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
						    "numePersoana VARCHAR(100) NOT NULL, " +
						    "nrTel INT NOT NULL, " +
						    "adresa VARCHAR(255) NOT NULL, " +
						    "idAnimal INT NOT NULL, " +
						    "numeAnimal VARCHAR(100), " +
						    "dataAdoptie DATE NOT NULL, " +
						    "aprobare BOOLEAN DEFAULT FALSE, " +
						    "CONSTRAINT fk_id_animal FOREIGN KEY (idAnimal) REFERENCES Animale(id) ON DELETE CASCADE " + //Daca un animal va fi sters, adoptiile ce au legatura cu acel animal vor fi si ele sterse
							")");
					System.out.println("Tabel Adoptii a fost creat");
				}
				else
				{
					System.out.println("Tabelul exista!(Adoptii)");
				}
             }
         }
     } catch (SQLException e) {
         e.printStackTrace();
     }
	}
	
    /**
     * Metoda ce verifica daca un username exista in tabelul Users al bazei de date
     * @param username
     * @return true / false depinzand daca userul exista in baza de date
     */
    public static boolean checkUsername(String username) {
        String query = "SELECT COUNT(*) FROM Users WHERE username = ?";
        
        try (Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/BazaDeDate;create=true");
             PreparedStatement statement = conn.prepareStatement(query)) {
        	statement.setString(1, username);
            
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return true; // Returns true if the username exists
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Returns false if there was an error or username doesn't exist
    }
    /**
     * Metoda care stocheaza un nou user in baza de date
     * @param user
     * @return true / false depinzand daca inregistrarea a avut succes
     */
    public static boolean registerUser(Utilizator user) {
    	String query = "INSERT INTO Users(username, password, isAdmin) VALUES (?, ?, ?)";
    	
        try (Connection conn =  DriverManager.getConnection("jdbc:derby://localhost:1527/BazaDeDate;create=true");
            PreparedStatement statement = conn.prepareStatement(query)) {
        	statement.setString(1, user.getUsername());
        	statement.setString(2, user.getPassword()); // For production, hash the password before storing
        	statement.setBoolean(3, user.isAdmin());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                return true; // Inregistrare reusita
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Inregistrare esuata
    }
	
    /**
     * Metoda de autentificare a unui user existent
     * @param username
     * @param password
     * @return un obiectde tip Utilizator corespunzator user-ului conectat
     */
     public static Utilizator authenticateUser(String username, String password) {
    	 String query = "SELECT * FROM Users WHERE username = ? AND password = ?";
            
    	 try (Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/BazaDeDate;create=true");
    			 PreparedStatement statement = conn.prepareStatement(query)) {
    		statement.setString(1, username);
            statement.setString(2, password);

            try (ResultSet rs = statement.executeQuery()) {
            	 if (rs.next()) {
                     String retrievedUsername = rs.getString("username");
                     String retrievedPassword = rs.getString("password");
                     boolean isAdmin = rs.getBoolean("isAdmin");

                     // Se returneaza datele utilizatorului accesat
                     firstUser = new Utilizator(retrievedUsername, retrievedPassword, isAdmin);
                     return firstUser;
                 } else {
                     // If no matching user found
                     System.out.println("Invalid username or password.");
                 }
             }
           } catch (SQLException e) {
        	   e.printStackTrace();
           }
    	 return null;
     }
     
     /**
      * Metoda ce adauga o noua inregistrare in tabela Animale a bazei de date
      * @param nume
      * @param specie
      * @param rasa
      * @param sex
      * @param stareSanatate
      * @param descriere
      * @param base64Image
      * @return true / flase depinzand daca inregistrarea a avut succes
      */
     public static boolean addAnimal(String nume, String specie, String rasa, String sex, String stareSanatate, String descriere, String base64Image) {
    	// Se ia data curenta pentru data de sosire
         Date dataSosire = new Date();
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
         String formattedDate = sdf.format(dataSosire);

         try (Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/BazaDeDate;create=true")) {
             // Prepare SQL INSERT statement
             String query = "INSERT INTO Animale(nume, specie, rasa, sex, dataSosire, stareSanatate, descriereFizica, stareAdoptie, picture) "
                     + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
             
             try (PreparedStatement statement = conn.prepareStatement(query)) {
            	 statement.setString(1, nume);
            	 statement.setString(2, specie);
            	 statement.setString(3, rasa);
            	 statement.setString(4, sex);
            	 statement.setDate(5, java.sql.Date.valueOf(formattedDate));  // Use java.sql.Date for proper date format
            	 statement.setString(6, stareSanatate);
            	 statement.setString(7, descriere);
            	 statement.setBoolean(8, false);
            	 statement.setString(9, base64Image);  // Save the Base64 string

                 int rowsAffected = statement.executeUpdate();
                 if (rowsAffected > 0) {
                     return true;
                 } 
             }
         } catch (SQLException ex) {
             ex.printStackTrace();
         }
         return false;
     }
	/**
	 * Metoda ce sterge o inregistrare din tabela Animale in functie de id-ul furnizat
	 * @param id
	 * @return true / false daca inregistrarea a fost gasita si stergerea a avut loc cu succes
	 */
	public static boolean deleteAnimalById(int id) {
		String query = "DELETE FROM Animale WHERE id = ?";
	    try (Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/BazaDeDate;create=true");
	         PreparedStatement statement = conn.prepareStatement(query)) {
	        statement.setInt(1, id);
	        int rowsAffected = statement.executeUpdate();
	        return rowsAffected > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
	/**
	 * Metoda ce returneaza inregistrarile din tabela Animale sub forma unui ArrayList de obiecte de tip Animal
	 * @return List<Animal> animal
	 */
	public static List<Animal> getAnimalsFromDatabase() {
        List<Animal> animals = new ArrayList<>();
        String query = "SELECT * FROM Animale"; 
        ResultSet rs = null;

        try (Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/BazaDeDate;create=true");
        		PreparedStatement statement = conn.prepareStatement(query)){
            rs = statement.executeQuery();
            // Iteram prin obiectul RuleSet si cream obiecte de tip Animal
            while (rs.next()) {
                int id = rs.getInt("ID");
                String nume = rs.getString("NUME");
                String specie = rs.getString("SPECIE");
                String rasa = rs.getString("RASA");
                String sex = rs.getString("SEX");
                Date dataSosire = rs.getDate("DATASOSIRE");
                String stareSanatate = rs.getString("STARESANATATE");
                String descriereFizica = rs.getString("DESCRIEREFIZICA");
                boolean stareAdoptie = rs.getBoolean("STAREADOPTIE");
                String picture = rs.getString("PICTURE");
                
                // Create the Animal object
                Animal animal = new Animal(id, nume, specie, rasa, sex, dataSosire, stareSanatate, descriereFizica, stareAdoptie, picture);
                
                // Se adauga obiectul la lista
                animals.add(animal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } //finally {
            /*try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (Connection conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }*/
        //}

        return animals;
    }
	
	/**
	 * Metoda ce returneaza un set ce contine toate speciile animalelor din baza de date
	 * @return specii
	 */
	public static Set<String> getSpeciesFromDatabase() {
	    Set<String> specii = new HashSet<>();
	    
	    try (Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/BazaDeDate;create=true")) {
	        String query = "SELECT DISTINCT specie FROM Animale";
	        try (Statement statement = connection.createStatement();
	             ResultSet resultSet = statement.executeQuery(query)) {
	            
	            while (resultSet.next()) {
	                specii.add(resultSet.getString("specie"));
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return specii;
	}
	
	/**
	 * Metoda ce va adauga obiecte de tip Adoptie in tabela Adoptions
	 * @param name
	 * @param nrTel
	 * @param adress
	 * @param id
	 * @return true / false in fuctie de succesul inregistarii in tabel
	 */
	public static boolean applyAnimalAdoption(String name, int nrTel, String adress, int id) {
		Date dataSosire = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(dataSosire);
		String query = "INSERT INTO Adoptions(numePersoana, nrTel, adresa, idAnimal, numeAnimal, dataAdoptie, aprobare) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
		String fetchAnimalNameQuery = "SELECT nume FROM Animale WHERE id = ?";

		try (Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/BazaDeDate;create=true");
				PreparedStatement statement = conn.prepareStatement(query)) {
			
				String numeAnimal = null;
				try (PreparedStatement fetchStmt = conn.prepareStatement(fetchAnimalNameQuery)) {
					fetchStmt.setInt(1, id);
					try (ResultSet rs = fetchStmt.executeQuery()) {
						if (rs.next()) {
	                    numeAnimal = rs.getString("nume");
						} else {
							System.out.println("Animal with the given ID is not found");
							return false; // Return false if no valid animal is found
						}
					}
				}
			
				// Seteaza parametrii pentru statemenyul preparat
				statement.setString(1, name);
				statement.setInt(2, nrTel);
				statement.setString(3, adress);
				statement.setInt(4, id);
				statement.setString(5, numeAnimal);
				statement.setDate(6, java.sql.Date.valueOf(formattedDate));  // Use java.sql.Date for proper date format
				statement.setBoolean(7, false);

				// Executa operatia de inserare in baza de date
				int rowsAffected = statement.executeUpdate();
				return rowsAffected > 0; // Returneaza adevarat daca operatia a fost executata cu succes
		} catch (SQLException e) {
			e.printStackTrace();
			return false; // Returneaza fals in caz de eroare
		}
	}
	/**
     * Metoda ce verifica daca un animal exista in tabelul Animale al bazei de date
     * @param id
     * @return true / false depinzand daca animalul exista in baza de date
     */
    public static boolean checkID(int id) {
        String query = "SELECT COUNT(*) FROM Animale WHERE id = ? AND stareAdoptie = false";
        
        try (Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/BazaDeDate;create=true");
             PreparedStatement statement = conn.prepareStatement(query)) {
        	statement.setInt(1, id);
            
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                	int count = rs.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Metoda ce returneaza datele din interiorul tabelei Adoptions
     * @return List<Adoptie> adoptions
     */
    public static List<Adoptie> getAdoptionsFromDatabase() {
        List<Adoptie> adoptions = new ArrayList<>();
        String query = "SELECT * FROM Adoptions";

        try (Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/BazaDeDate;create=true");
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String numePersoana = rs.getString("numePersoana");
                int nrTel = rs.getInt("nrTel");
                String adresa = rs.getString("adresa");
                int idAnimal = rs.getInt("idAnimal");
                String numeAnimal = rs.getString("numeAnimal");
                Date dataAdoptie = rs.getDate("dataAdoptie");
                boolean aprobare = rs.getBoolean("aprobare");

                adoptions.add(new Adoptie(id, numePersoana, nrTel, adresa, idAnimal, numeAnimal, dataAdoptie, aprobare));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adoptions;
    }
	
    /**
     * Metoda ce respinge o cerere de adoptie
     * @param adoptie
     */
    public static void rejectAdoption(Adoptie adoptie) {
        String deleteAdoptionQuery = "DELETE FROM Adoptions WHERE id = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/BazaDeDate;create=true");
             PreparedStatement deleteAdoptionStmt = conn.prepareStatement(deleteAdoptionQuery)) {

            // Delete the adoption request
            deleteAdoptionStmt.setInt(1, adoptie.getId());
            deleteAdoptionStmt.executeUpdate();

            //JOptionPane.showMessageDialog(this, "Adoption rejected successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
            //JOptionPane.showMessageDialog(this, "Error rejecting adoption.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Metoda ce aproba o cerere de adoptie
     * @param adoptie
     */
    public static void approveAdoption(Adoptie adoptie) {
        String updateAdoptionQuery = "UPDATE Adoptions SET aprobare = TRUE WHERE id = ?";
        String updateAnimalQuery = "UPDATE Animale SET stareAdoptie = TRUE WHERE id = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/BazaDeDate;create=true");
             PreparedStatement updateAdoptionStmt = conn.prepareStatement(updateAdoptionQuery);
             PreparedStatement updateAnimalStmt = conn.prepareStatement(updateAnimalQuery)) {

            updateAdoptionStmt.setInt(1, adoptie.getId());
            updateAdoptionStmt.executeUpdate();

            updateAnimalStmt.setInt(1, adoptie.getIdAnimal());
            updateAnimalStmt.executeUpdate();

            //JOptionPane.showMessageDialog(this, "Adoption approved successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
            //JOptionPane.showMessageDialog(this, "Error approving adoption.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
