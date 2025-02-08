package p1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
//import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
/**
 * Clasa ce descrie reprezentarea grafica a ecranului de inregistrare a unui nou User in baza de date
 */
public class Register extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField userTextField;
	private JPasswordField passwordField;
	private JPasswordField confirmPasswordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Register frame = new Register();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Register() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 725, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 128, 128));
		panel.setBounds(0, 0, 353, 463);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("D:\\eclipse-workspace\\Proiect\\rb_1365 (1).png"));
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setBounds(30, 80, 260, 291);
		panel.add(lblNewLabel);
		
		JLabel UserLabel = new JLabel("Username");
		UserLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		UserLabel.setBounds(385, 51, 120, 26);
		contentPane.add(UserLabel);
		/**
		 * Campul in care se completeaza 'username-ul' utilizatorului
		 */
		userTextField = new JTextField();
		userTextField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		userTextField.setColumns(10);
		userTextField.setBounds(385, 87, 289, 29);
		contentPane.add(userTextField);
		
		JLabel passLabel = new JLabel("Password");
		passLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		passLabel.setBounds(383, 139, 150, 26);
		contentPane.add(passLabel);
		
		JLabel confirmLabel = new JLabel("Confirm Password");
		confirmLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		confirmLabel.setBounds(385, 228, 226, 21);
		contentPane.add(confirmLabel);
		/**
		 * Butonul va prelua datele introduse si va incerca inregistrarea lor in baza de date folosita
		 */
		JButton registerButton = new JButton("Register");
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = userTextField.getText();
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());
                //Se verifica daca campul pentru 'password' si 'confirmPassword' sunt identice si va afisa o eroare daca nu sunt
                if (!password.equals(confirmPassword)) {
                	JOptionPane.showMessageDialog(null, "Parolele nu se potrivesc!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else
                	//Se verifica daca utilizatorul introdus exista deja in baza de date si daca da se afiseaza o eroare
                	if (DatabaseManipulation.checkUsername(username) == false) {
                		JOptionPane.showMessageDialog(null, "Utilizator deja existent.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                	else {
                		Utilizator user = new Utilizator(username, password, false);
                		boolean result = DatabaseManipulation.registerUser(user);
                		//Se verifica daca adaugarea in baza de date s-a executat cu succes
                		if (result) {
                            System.out.println("Inregistrare cu succes.");
                        } else {
                            System.out.println("Inregistrare esuata.");
                        }
                		
                		//Se intoarce la ecranul de Login
                		Login frame = new Login();
        				Register.super.dispose();
        				frame.setVisible(true);
                	}
			}
		});
		registerButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
		registerButton.setBounds(542, 365, 118, 29);
		contentPane.add(registerButton);
		/**
		 * Butonul se intoarce la ecranul de 'Login'
		 */
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login frame = new Login();
				Register.super.dispose();
				frame.setVisible(true);
			}
		});
		btnBack.setFont(new Font("Times New Roman", Font.BOLD, 18));
		btnBack.setBounds(399, 365, 118, 29);
		contentPane.add(btnBack);
		/**
		 * Campul in care se completeaza 'parola' utilizatorului
		 */
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Times New Roman", Font.BOLD, 18));
		passwordField.setBounds(385, 175, 289, 29);
		contentPane.add(passwordField);
		/**
		 * Campul in care se completeaza 'parola' utilizatorului a doua oara pentru confirmare
		 */
		confirmPasswordField = new JPasswordField();
		confirmPasswordField.setFont(new Font("Times New Roman", Font.BOLD, 18));
		confirmPasswordField.setBounds(385, 259, 289, 29);
		contentPane.add(confirmPasswordField);
		
		JLabel errorLabel = new JLabel("");
		errorLabel.setForeground(new Color(128, 0, 0));
		errorLabel.setFont(new Font("Times New Roman", Font.ITALIC, 15));
		errorLabel.setBounds(399, 426, 45, 13);
		contentPane.add(errorLabel);
	}
}
