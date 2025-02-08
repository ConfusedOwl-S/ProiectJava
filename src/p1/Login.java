package p1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
/**
 * clasa Login extinde JFrame si descrie reprezenentarea grafica a ecranului de 'Login'
 */
public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField userTextField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 725, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel UserLabel = new JLabel("Username");
		UserLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		UserLabel.setBounds(387, 76, 120, 26);
		contentPane.add(UserLabel);
		
		/**
		 * Campul in care se completeaza 'username-ul' utilizatorului
		 */
		userTextField = new JTextField();
		userTextField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		userTextField.setBounds(387, 112, 289, 29);
		contentPane.add(userTextField);
		userTextField.setColumns(10);
		
		JLabel passLabel = new JLabel("Password");
		passLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		passLabel.setBounds(387, 181, 150, 26);
		contentPane.add(passLabel);
		
		/**
		 * Butonul 'Login' va deschide ecranul 'MainScreen' pentru utilizatori normali, ecranul 'AdminScreen' pentru admin,
		 * sau va afisa erori daca datele introduse nu sunt valide
		 */
		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = userTextField.getText();
				String password = new String(passwordField.getPassword());
				Utilizator user = DatabaseManipulation.authenticateUser(username, password);
				
				if(user != null) {
					if(user.isAdmin() == true) {
						AdminScreen frame = new AdminScreen();
						Login.super.dispose();
						frame.setVisible(true);
					}
					else
					{
						MainScreen frame = new MainScreen();
						Login.super.dispose();
						frame.setVisible(true);
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Username sau parola invalide.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		loginButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
		loginButton.setBounds(400, 312, 118, 29);
		contentPane.add(loginButton);
		
		/**
		 * Buton ce inchide fereastra actuala si deschide fereastra de Inregistrare a unui nou utilizator
		 */
		JButton registerButton = new JButton("Register");
		registerButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Register frame = new Register();
				Login.super.dispose();
				frame.setVisible(true);
			}
		});
		registerButton.setBounds(547, 312, 118, 29);
		contentPane.add(registerButton);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 128, 128));
		panel.setBounds(0, 0, 353, 463);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("D:\\eclipse-workspace\\Proiect\\rb_1365 (1).png"));
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setBounds(30, 80, 260, 291);
		panel.add(lblNewLabel);
		
		/**
		 * Campul in care se completeaza 'parola' utilizatorului
		 */
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		passwordField.setBounds(387, 217, 289, 29);
		contentPane.add(passwordField);
	}
}
