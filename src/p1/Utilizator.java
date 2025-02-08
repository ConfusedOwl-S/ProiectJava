package p1;
/**
 * Clasa pentru definirea userilor
 * Stocheaza username si parola
 * Stocheaza daca userul respectiv este parte din staff-ul adapostului
 */
public class Utilizator {
	private String username;
	private String password;
	private boolean isAdmin;// true = "ADMIN" si false = "USER"
	
	public Utilizator() {}

	public Utilizator(String username, String password, boolean isAdmin) {
		this.username = username;
		this.password = password;
		this.isAdmin = isAdmin;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	@Override
	public String toString() {
		return username + "," + password + "," + isAdmin;
	}
}
