package p1;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.io.File;

//import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
//import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
//import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
//import javax.swing.filechooser.FileNameExtensionFilter;
/**
 * Clasa ce descrie reprezentarea grafica a ecranului folosit pentru generarea de aplicatii de adoptie a unui animal
 */
public class ApplyFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nameTextField;
	private JTextField TelTextField;
	private JTextField adressTextField;
	private JTextField idTextField;
	//private File selectedImageFile = null;  // To store the selected image file
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ApplyFrame frame = new ApplyFrame();
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
	public ApplyFrame() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 560);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 128, 128));
		panel.setBounds(0, 0, 436, 54);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("D:\\eclipse-workspace\\Proiect\\rb_1365 (2).png"));
		lblNewLabel.setBounds(10, 10, 45, 34);
		panel.add(lblNewLabel);
		
		JLabel nameLabel = new JLabel("Nume si prenume");
		nameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		nameLabel.setBounds(24, 79, 166, 13);
		contentPane.add(nameLabel);
		/**
		 * Campul in care se completeaza numele legal al utilizatorului
		 */
		nameTextField = new JTextField();
		nameTextField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		nameTextField.setBounds(23, 102, 382, 27);
		contentPane.add(nameTextField);
		nameTextField.setColumns(10);
		
		JLabel telLabel = new JLabel("Numar telefon");
		telLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		telLabel.setBounds(24, 155, 166, 18);
		contentPane.add(telLabel);
		
		JLabel adressLabel = new JLabel("Adresa");
		adressLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		adressLabel.setBounds(24, 234, 166, 13);
		contentPane.add(adressLabel);
		
		JLabel idLabel = new JLabel("ID-ul animalului");
		idLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		idLabel.setBounds(24, 315, 166, 13);
		contentPane.add(idLabel);
		/**
		 * Campul in care se completeaza datele de contact(Nr. de telefon) al aplicantului
		 */
		TelTextField = new JTextField();
		TelTextField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		TelTextField.setColumns(10);
		TelTextField.setBounds(24, 178, 382, 27);
		contentPane.add(TelTextField);
		/**
		 * Campul in care se completeaza adresa aplicantului
		 */
		adressTextField = new JTextField();
		adressTextField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		adressTextField.setColumns(10);
		adressTextField.setBounds(23, 257, 382, 27);
		contentPane.add(adressTextField);
		/**
		 * Campul in care se completeaza id-ul corespunzator animalului pentru care se face aplicatia
		 */
		idTextField = new JTextField();
		idTextField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		idTextField.setColumns(10);
		idTextField.setBounds(23, 338, 382, 27);
		contentPane.add(idTextField);
		
		/**
		 * Butonul va prelua datele introduse si va incerca sa le introduca in baza de date
		 */
		JButton applyButton = new JButton("Apply");
		applyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = nameTextField.getText();
		        String nrTel = TelTextField.getText();
		        String adress = adressTextField.getText();
		        String id = idTextField.getText();
		        

		        // Se verifica daca toate campurile sunt completate
		        if (name.isEmpty() || nrTel.isEmpty() || adress.isEmpty() || id.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Campuri necompletate!", "Error", JOptionPane.ERROR_MESSAGE);
		            return;
		        }
		        
		        if (DatabaseManipulation.checkID(Integer.parseInt(id)) == false) {
            		JOptionPane.showMessageDialog(null, "Animalul nu este valabil.", "Error", JOptionPane.ERROR_MESSAGE);
                }
		        else {
		        	boolean result = DatabaseManipulation.applyAnimalAdoption(name, Integer.parseInt(nrTel), adress, Integer.parseInt(id));
	        		//Se verifica daca adaugarea in baza de date s-a executat cu succes
	        		if (result) {
	                    System.out.println("Inregistrare cu succes.");
	                } else {
	                    System.out.println("Inregistrare esuata."); //Mesajul se afiseaza daca datele introduse sunt invalide sau conexiunea cu baza de date este inexistenta
	                }
	        		
	        		//Se inchide formularul
					ApplyFrame.super.dispose();
		        }     
			}
		});
		applyButton.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		applyButton.setBounds(141, 457, 153, 39);
		contentPane.add(applyButton);
	}

}
