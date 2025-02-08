package p1;

import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
//import javax.swing.JToolBar;
import javax.swing.ListCellRenderer;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
//import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
//import javax.swing.JTextPane;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
//import java.util.ArrayList;
//import java.util.Date;
import java.util.List;
import java.util.Set;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
//import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
/**
 * Clasa MainScreen descrie reprezentarea grafica a ecranului folosit de utilizatorii ce nu sunt admini
 */
public class MainScreen extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JTextField searchField;
    private JComboBox<String> speciesFilterCombo;
    private JList<Animal> animalList;
    private DefaultListModel<Animal> animalListModel;
    private List<Animal> animals;
    
    /**
     * Metoda va actualiza dinamic lista de animale afisate pe ecran in functie de filtere active
     */
    private void updateAnimalList() {
        String searchQuery = searchField.getText().toLowerCase();
        String selectedSpecies = (String) speciesFilterCombo.getSelectedItem();
        animalListModel.clear(); // Clear the current list

        for (Animal animal : animals) {
            boolean matchesSearch = animal.getNume().toLowerCase().contains(searchQuery);
            boolean matchesSpecies = selectedSpecies.equals("All") || animal.getSpecie().equals(selectedSpecies);

            if (matchesSearch && matchesSpecies) {
                animalListModel.addElement(animal);
            }
        }
    }

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainScreen frame = new MainScreen();
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
	public MainScreen() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 725, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 128, 128));
		panel.setBounds(0, 0, 711, 54);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setIcon(new ImageIcon("D:\\eclipse-workspace\\Proiect\\rb_1365 (2).png"));
		lblNewLabel_2.setBounds(10, 10, 45, 34);
		panel.add(lblNewLabel_2);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 77, 113));
		panel_1.setBounds(0, 53, 172, 410);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel userLabel = new JLabel("User: ");
		userLabel.setForeground(new Color(192, 192, 192));
		userLabel.setFont(new Font("SansSerif", Font.ITALIC, 18));
		userLabel.setBounds(10, 10, 54, 31);
		panel_1.add(userLabel);
		
		/**
		 * Continutul acestui label va fi username-ul utilizatorului curent
		 */
		JLabel nameLabel = new JLabel("");
		nameLabel.setFont(new Font("SansSerif", Font.ITALIC, 16));
		nameLabel.setForeground(new Color(192, 192, 192));
		nameLabel.setBounds(65, 10, 97, 31);
		panel_1.add(nameLabel);
		nameLabel.setText(DatabaseManipulation.firstUser.getUsername());
		
		JPanel backgroundPanel = new JPanel();
		backgroundPanel.setBackground(new Color(192, 192, 192));
		backgroundPanel.setBounds(173, 53, 538, 410);
		contentPane.add(backgroundPanel);
		backgroundPanel.setLayout(new CardLayout(0, 0));
		
		JPanel animalPanel = new JPanel();
		backgroundPanel.add(animalPanel, "name_58822516353500");
		animalPanel.setLayout(new BorderLayout());
		
		JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        animalPanel.add(filterPanel, BorderLayout.NORTH);
        
        //SearchBar dupa numele animalului
        searchField = new JTextField(20);
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                updateAnimalList();
            }
        });
        filterPanel.add(searchField);
        
     	//Se filtreaza animalele dupa specie
        Set<String> species = DatabaseManipulation.getSpeciesFromDatabase();
        // Se adauga "All" la optiunile setului
        species.add("All");
        speciesFilterCombo = new JComboBox<>(species.toArray(new String[0]));
        speciesFilterCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateAnimalList();
            }
        });
        filterPanel.add(speciesFilterCombo);
        
        // Animal List (JList) and Scrollable Panel
        animalListModel = new DefaultListModel<>();
        animalList = new JList<>(animalListModel);
        JScrollPane scrollPane = new JScrollPane(animalList);
        animalPanel.add(scrollPane, BorderLayout.CENTER);
        
        //Fiecare element din lista va afisa datele unei inregistrari din tabela Animale
        JPanel detailPanel = new JPanel();
        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));
        
        JLabel animalNameLabel = new JLabel();
        JLabel speciesLabel = new JLabel();
        JLabel breedLabel = new JLabel();
        JLabel healthLabel = new JLabel();
        JLabel arrivalDateLabel = new JLabel();
        JLabel adoptionStatusLabel = new JLabel();
        JLabel imageLabel = new JLabel(); // For the image
        JTextArea descriptionArea = new JTextArea();
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setEditable(false);

        // Add components to detailPanel
        detailPanel.add(animalNameLabel);
        detailPanel.add(speciesLabel);
        detailPanel.add(breedLabel);
        detailPanel.add(healthLabel);
        detailPanel.add(arrivalDateLabel);
        detailPanel.add(adoptionStatusLabel);
        detailPanel.add(imageLabel);
        detailPanel.add(descriptionArea);

        backgroundPanel.add(detailPanel, BorderLayout.EAST);
        /**
         * Formatare de  layout pentru elementeledin lista folosind CellRenderer
         */
        animalList.setCellRenderer(new ListCellRenderer<Animal>() {
            public Component getListCellRendererComponent(JList<? extends Animal> list, Animal value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
            	JPanel panel = new JPanel(new BorderLayout());
                panel.setOpaque(true);

                // Set background and foreground colors
                if (isSelected) {
                    panel.setBackground(list.getSelectionBackground());
                } else {
                    panel.setBackground(list.getBackground());
                }

                if (value != null) {
                    // Create a label for the image
                    JLabel imageLabel = new JLabel();
                    String imageBytes = value.getPicture();
                    if (imageBytes != null) {
                        Image pic = ImageUtils.decodeBase64ToImage(imageBytes);
                        ImageIcon icon = new ImageIcon(pic.getScaledInstance(200, 200, Image.SCALE_SMOOTH));
                        imageLabel.setIcon(icon);
                    }

                    // Create a label for the text details
                    JLabel detailsLabel = new JLabel("<html>" +
                    		"<b>ID: #</b> " + value.getId() + "<br>" +
                    		"<b>Nume: </b> " + value.getNume() + "<br>" +
                    		"<b>Specie: </b> " + value.getSpecie() + "<br>" +
                    		"<b>Rasa: </b> " + value.getRasa() + "<br>" +
                    		"<b>Sex: </b> " + value.getSex() + "<br>" +
                            "<b>Data sosire: </b> " + value.getDataSosire() + "<br>" +      
                            "<b>Stare sanatate: </b> " + value.getStareSanatate() + "<br>" +
                            "<b>Descriere fizica: </b> " + value.getDescriereFizica() + "<br>" +
                            "<b>Adoptat: </b> " + (value.getStareAdoptie() ? "Da" : "Nu") + "<br>" +
                            "</html>");
                    detailsLabel.setOpaque(true);
                    
                    detailsLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14)); // Larger font

                    // Add padding for spacing
                    detailsLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                    panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                    // Add the components to the panel
                    panel.add(imageLabel, BorderLayout.WEST);
                    panel.add(detailsLabel, BorderLayout.CENTER);
                }

                return panel;
            }
        });
        
     // Se populeaza lista cu date din tabela Animale
        animals = DatabaseManipulation.getAnimalsFromDatabase(); // Fetch all animals from the database
        updateAnimalList(); // Update list based on filters
		/**
		 * Buton care afiseaza lista de animale a adapostului
		 */
		JButton viewAnimalButton = new JButton("Animale");
		viewAnimalButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				animalPanel.setVisible(true);		
			}
		});
		viewAnimalButton.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		viewAnimalButton.setForeground(new Color(192, 192, 192));
		viewAnimalButton.setBackground(new Color(0, 77, 113));
		viewAnimalButton.setBounds(0, 51, 172, 42);
		panel_1.add(viewAnimalButton);
		
		/**
		 * Buton ce deschide ecranul "ApplyFrame"
		 */
		JButton applyButton = new JButton("Cerere de adoptie");
		applyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ApplyFrame frame = new ApplyFrame();
				frame.setVisible(true);
			}
		});
		applyButton.setForeground(Color.LIGHT_GRAY);
		applyButton.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		applyButton.setBackground(new Color(0, 77, 113));
		applyButton.setBounds(0, 93, 172, 42);
		panel_1.add(applyButton);
		
		/**
		 * Buton ce se intoarce la ecranul de 'Login'
		 */
		JButton logoutButton = new JButton("Logout");
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login frame = new Login();
				MainScreen.super.dispose();
				frame.setVisible(true);
			}
		});
		logoutButton.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		logoutButton.setForeground(Color.LIGHT_GRAY);
		logoutButton.setBackground(new Color(0, 77, 113));
		logoutButton.setBounds(0, 135, 172, 42);
		panel_1.add(logoutButton);
	}
}
