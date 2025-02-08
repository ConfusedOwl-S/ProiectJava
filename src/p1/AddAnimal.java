package p1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
/**
 * Clasa ce descrie reprezentarea grafica a ecranului folosit pentru adugarea unui nou animal in lista de adoptie a adapostului
 */
public class AddAnimal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nameTextField;
	private JTextField SpecTextField;
	private JTextField raceTextField;
	private JTextField healthTextField;
	private JTextField descTextField;
	private File selectedImageFile = null;  // To store the selected image file

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddAnimal frame = new AddAnimal();
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
	public AddAnimal() {
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
		
		JLabel nameLabel = new JLabel("Nume");
		nameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		nameLabel.setBounds(24, 79, 166, 13);
		contentPane.add(nameLabel);
		
		/**
		 * Campul se completeaza cu numele animalului
		 */
		nameTextField = new JTextField();
		nameTextField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		nameTextField.setBounds(23, 102, 382, 27);
		contentPane.add(nameTextField);
		nameTextField.setColumns(10);
		
		JLabel specieLabel = new JLabel("Specie");
		specieLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		specieLabel.setBounds(24, 155, 166, 18);
		contentPane.add(specieLabel);
		
		JLabel sexLabel = new JLabel("Sex");
		sexLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		sexLabel.setBounds(24, 311, 37, 13);
		contentPane.add(sexLabel);
		
		JLabel raceLabel = new JLabel("Rasa");
		raceLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		raceLabel.setBounds(24, 234, 166, 13);
		contentPane.add(raceLabel);
		
		JLabel descLabel = new JLabel("Descriere fizica");
		descLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		descLabel.setBounds(24, 414, 166, 13);
		contentPane.add(descLabel);
		
		JLabel healthLabel = new JLabel("Starea sanatatii\r\n");
		healthLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		healthLabel.setBounds(24, 345, 166, 13);
		contentPane.add(healthLabel);
		
		/**
		 * Campul se completeaza cu specia animalului
		 */
		SpecTextField = new JTextField();
		SpecTextField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		SpecTextField.setColumns(10);
		SpecTextField.setBounds(24, 178, 382, 27);
		contentPane.add(SpecTextField);
		
		/**
		 * Campul se completeaza cu rasa animalului
		 */
		raceTextField = new JTextField();
		raceTextField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		raceTextField.setColumns(10);
		raceTextField.setBounds(23, 257, 382, 27);
		contentPane.add(raceTextField);
		
		/**
		 * RadioButtons folosite pentru alegerea sexului animalului (M/F)
		 */
		JRadioButton FNewRadioButton = new JRadioButton("F");
		FNewRadioButton.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		FNewRadioButton.setBounds(67, 308, 37, 21);
		contentPane.add(FNewRadioButton);
		
		JRadioButton MNewRadioButton = new JRadioButton("M");
		MNewRadioButton.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		MNewRadioButton.setBounds(120, 308, 49, 21);
		contentPane.add(MNewRadioButton);
		
		// Create a ButtonGroup and add the radio buttons
	    ButtonGroup sexGroup = new ButtonGroup();
	    sexGroup.add(FNewRadioButton);  // Add "F" to the group
	    sexGroup.add(MNewRadioButton);  // Add "M" to the group
		
	    /**
		 * Campul se completeaza cu starea de sanatate a animalului
		 */
		healthTextField = new JTextField();
		healthTextField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		healthTextField.setColumns(10);
		healthTextField.setBounds(24, 368, 382, 27);
		contentPane.add(healthTextField);
		
		/**
		 * Campul se completeaza cu descrierea fizica a animalului
		 */
		descTextField = new JTextField();
		descTextField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		descTextField.setColumns(10);
		descTextField.setBounds(23, 437, 382, 27);
		contentPane.add(descTextField);	
		
		JLabel saveLabel = new JLabel("");
		saveLabel.setForeground(new Color(128, 255, 0));
		saveLabel.setBounds(348, 316, 45, 13);
		contentPane.add(saveLabel);
		
		/**
		 * Butonul salveaza o imagine selectata din sistem
		 */
		JButton photoButton = new JButton("");
		photoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "jpeg", "gif"));
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    selectedImageFile = fileChooser.getSelectedFile();
                    JOptionPane.showMessageDialog(null, "Image selected: " + selectedImageFile.getName());
                    saveLabel.setText("Saved!");
                }
			}
		});
		photoButton.setBackground(new Color(240, 240, 240));
		photoButton.setIcon(new ImageIcon("C:\\Users\\simon\\Downloads\\photo-camera (1).png"));
		photoButton.setBounds(267, 308, 71, 50);
		contentPane.add(photoButton);
		
		/**
		 * Butonul preia datele introduse si incearca a le inregistra in baza de date folosita
		 */
		JButton addAnimalButton = new JButton("Adauga");
		addAnimalButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = nameTextField.getText();
		        String species = SpecTextField.getText();
		        String race = raceTextField.getText();
		        String sex = FNewRadioButton.isSelected() ? "F" : "M"; // Get selected sex
		        String healthStatus = healthTextField.getText();
		        String physicalDescription = descTextField.getText();
		        
		        // Convert selected image to Base64 string
                String base64Image = null;
                if (selectedImageFile != null) {
                    base64Image = ImageUtils.encodeImageToBase64(selectedImageFile);
                }

		        // Se verifica daca toate campurile sunt completate
		        if (name.isEmpty() || species.isEmpty() || race.isEmpty() || healthStatus.isEmpty() || physicalDescription.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Campuri necompletate!", "Error", JOptionPane.ERROR_MESSAGE);
		            return;
		        }
		        
		        boolean result = DatabaseManipulation.addAnimal(name, species, race, sex, healthStatus, physicalDescription, base64Image);
        		//Se verifica daca adaugarea in baza de date s-a executat cu succes
        		if (result) {
                    System.out.println("Inregistrare cu succes.");
                } else {
                    System.out.println("Inregistrare esuata.");
                }
        		
        		//Se inchide formularul
				AddAnimal.super.dispose();
			}
		});
		addAnimalButton.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		addAnimalButton.setBounds(140, 474, 153, 39);
		contentPane.add(addAnimalButton);
	}
}
