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
import javax.swing.JOptionPane;
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
 * Clasa ce descrie reprezentarea grafica a ecranului folosit de catre utilizatorii 'admin'
 */
public class AdminScreen extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JTextField searchField;
    private JComboBox<String> speciesFilterCombo;
    private JList<Animal> animalList;
    private DefaultListModel<Animal> animalListModel;
    private List<Animal> animals;
    
    //private JList<Adoptie> adoptionList;
    //private DefaultListModel<Adoptie> adoptionListModel;
    private List<Adoptie> adoptions;
    
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
     * Metoda ce actualizeaza dinamic lista de aplicatii de adoptie
     */
    //private void updateAdoptionList() {
    //    adoptionListModel.clear(); // Clear the current list
    //    for (Adoptie adoptie : adoptions) {
    //        adoptionListModel.addElement(adoptie); // Add adoption request to the list
    //    }
    //}
    /**
     * Metoda ce da refresh la ecranul curent
     */
    public void refreshScreen() {
    	AdminScreen.super.dispose();
    	AdminScreen frame = new AdminScreen();
		frame.setVisible(true);
    }

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminScreen frame = new AdminScreen();
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
	public AdminScreen() {
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
		
		JPanel requestPanel = new JPanel();
		backgroundPanel.add(requestPanel, "name_58928613188900");
		
		//adoptionListModel = new DefaultListModel<>();
        //requestPanel.setLayout(new BorderLayout(0, 0));
        //adoptionList = new JList<>(adoptionListModel);
        
        //JScrollPane requestScrollPane = new JScrollPane(adoptionList);
        //requestPanel.add(requestScrollPane);
        
        adoptions = DatabaseManipulation.getAdoptionsFromDatabase(); // Populeaza lista cu inregistrarile de adoptii din baza de date
        //updateAdoptionList();
        
        JPanel adoptionPanel = new JPanel();
        adoptionPanel.setLayout(new BoxLayout(adoptionPanel, BoxLayout.Y_AXIS));
        
        for (Adoptie adoption : adoptions) {
            // Create a panel for each adoption request
            JPanel requestPanel1 = new JPanel();
            requestPanel1.setLayout(new BorderLayout()); // Use BorderLayout for structured layout
            requestPanel1.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Optional styling

            // Left: Adoption details
            JLabel detailsLabel = new JLabel("<html>" +
                    "<b>ID: #</b> " + adoption.getId() + "<br>" +
                    "<b>Nume persoana: </b> " + adoption.getNumePersoana() + "<br>" +
                    "<b>Nr. telefon: </b> " + adoption.getNrTel() + "<br>" +
                    "<b>Adresa: </b> " + adoption.getAdresa() + "<br>" +
                    "<b>Animal: </b> " + adoption.getNumeAnimal() + "<br>" +
                    "<b>Data adoptie: </b> " + adoption.getDataAdoptie() + "<br>" +
                    "<b>Aprobare: </b> " + (adoption.isAprobare() ? "Da" : "Nu") + "<br>" +
                    "</html>");
            detailsLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

            // Butoane pentru acceptare sau respingere a unei cereri de adoptie
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT)); // Arrange buttons horizontally

            JButton approveButton = new JButton("Approve");
            JButton rejectButton = new JButton("Reject");

            // Add action listeners
            approveButton.addActionListener(e -> {
                // Approve logic
                DatabaseManipulation.approveAdoption(adoption);
                //updateAdoptionList(); // Refresh the UI
                updateAnimalList();
                refreshScreen();
            });

            rejectButton.addActionListener(e -> {
                // Reject logic
                DatabaseManipulation.rejectAdoption(adoption);
                //updateAdoptionList(); // Refresh the UI
                refreshScreen();
            });

            buttonPanel.add(approveButton);
            buttonPanel.add(rejectButton);

            // Add components to the request panel
            requestPanel1.add(detailsLabel, BorderLayout.CENTER);
            requestPanel1.add(buttonPanel, BorderLayout.EAST);

            // Add request panel to the main panel
            adoptionPanel.add(requestPanel1);
        }
        
        JScrollPane scrollPane2 = new JScrollPane(adoptionPanel);
        requestPanel.add(scrollPane2, BorderLayout.CENTER); // Assuming `mainFrame` is your JFrame
        requestPanel.revalidate();
        requestPanel.repaint();
		
		/*adoptionList.setCellRenderer(new ListCellRenderer<Adoptie>() {
		    public Component getListCellRendererComponent(JList<? extends Adoptie> list, Adoptie value, int index,
		                                                  boolean isSelected, boolean cellHasFocus) {
		        JPanel panel = new JPanel();
		        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		        // Set background color when item is selected
		        if (isSelected) {
		            panel.setBackground(list.getSelectionBackground());
		        } else {
		            panel.setBackground(list.getBackground());
		        }

		        if (value != null) {
		            // Create labels for each adoption request detail
		            JLabel detailsLabel = new JLabel("<html>" +
		                    "<b>ID: #</b> " + value.getId() + "<br>" +
		                    "<b>Nume persoana: </b> " + value.getNumePersoana() + "<br>" +
		                    "<b>Nr. telefon: </b> " + value.getNrTel() + "<br>" +
		                    "<b>Adresa: </b> " + value.getAdresa() + "<br>" +
		                    "<b>Animal: </b> " + value.getNumeAnimal() + "<br>" +
		                    "<b>Data adoptie: </b> " + value.getDataAdoptie() + "<br>" +
		                    "<b>Aprobare: </b> " + (value.isAprobare() ? "Da" : "Nu") + "<br>" +
		                    "</html>");
		            //detailsLabel.setOpaque(true);
		            detailsLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14)); // Larger font
		            detailsLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding
		            panel.add(detailsLabel);

		            // Create buttons for approval and rejection
		            JPanel buttonPanel = new JPanel();
		            buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		            JButton approveButton = new JButton("Approve");
		            JButton rejectButton = new JButton("Reject");
		            
		            approveButton.setFocusable(false);
		            rejectButton.setFocusable(false);

		            // Add ActionListener for Approve button
		            approveButton.addActionListener(new ActionListener() {
		                @Override
		                public void actionPerformed(ActionEvent e) {
		                    // Approve adoption
		                    DatabaseManipulation.approveAdoption(value);
		                    // Update the list
		                    updateAdoptionList();
		                }
		            });

		            // Add ActionListener for Reject button
		            rejectButton.addActionListener(new ActionListener() {
		                @Override
		                public void actionPerformed(ActionEvent e) {
		                    // Reject adoption
		                	DatabaseManipulation.rejectAdoption(value);
		                    // Update the list
		                    updateAdoptionList();
		                }
		            });

		            buttonPanel.add(approveButton);
		            buttonPanel.add(rejectButton);
		            panel.add(buttonPanel, BorderLayout.SOUTH);
		        }

		        return panel;
		    }
		});
		*/
		
		requestPanel.setVisible(false);
		
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
         * Formatare de  layout pentru elementele din lista de animale folosind CellRenderer
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
        
        /*animalList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Animal selectedAnimal = animalList.getSelectedValue();
                if (selectedAnimal != null) {
                    // Update detail panel
                    nameLabel.setText("Name: " + selectedAnimal.getNume());
                    speciesLabel.setText("Species: " + selectedAnimal.getSpecie());
                    breedLabel.setText("Breed: " + selectedAnimal.getRasa());
                    healthLabel.setText("Health: " + selectedAnimal.getStareSanatate());
                    arrivalDateLabel.setText("Arrival: " + selectedAnimal.getDataSosire());
                    descriptionArea.setText("Description: " + selectedAnimal.getDescriereFizica());
                    adoptionStatusLabel.setText("Adopted: " + (selectedAnimal.isStareAdoptie() ? "Yes" : "No"));

                    // Display the image
                    String imageBytes = selectedAnimal.getPicture();
                    if (imageBytes != null) {
                    	Image pic = ImageUtils.decodeBase64ToImage(imageBytes);
                        ImageIcon icon = new ImageIcon(pic);
                        imageLabel.setIcon(icon);
                    } else {
                        imageLabel.setIcon(null);
                    }
                }
            }
        });*/

        // Se populeaza lista cu date din tabela Animale
        animals = DatabaseManipulation.getAnimalsFromDatabase(); // Fetch all animals from the database
        updateAnimalList(); // Update list based on filters
		/**
		 * Buton care afiseaza lista de animale a adapostului
		 */
		JButton viewAnimalButton = new JButton("Animale");
		viewAnimalButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				requestPanel.setVisible(false);
				animalPanel.setVisible(true);		
			}
		});
		viewAnimalButton.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		viewAnimalButton.setForeground(new Color(192, 192, 192));
		viewAnimalButton.setBackground(new Color(0, 77, 113));
		viewAnimalButton.setBounds(0, 51, 172, 42);
		panel_1.add(viewAnimalButton);
		
		/**
		 * Buton ce afiseaza lista de cereri de adoptie
		 */
		JButton viewReqButton = new JButton("Vizualizare cerei adoptie");
		viewReqButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				animalPanel.setVisible(false);
				requestPanel.setVisible(true);
			}
		});
		viewReqButton.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		viewReqButton.setForeground(Color.LIGHT_GRAY);
		viewReqButton.setBackground(new Color(0, 77, 113));
		viewReqButton.setBounds(0, 177, 172, 42);
		panel_1.add(viewReqButton);
		
		/*JButton userinfoButton = new JButton("Userinfo");
		userinfoButton.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		userinfoButton.setForeground(Color.LIGHT_GRAY);
		userinfoButton.setBackground(new Color(0, 77, 113));
		userinfoButton.setBounds(0, 219, 172, 42);
		panel_1.add(userinfoButton);*/
		
		/**
		 * Buton ce se intoarce la ecranul de 'Login'
		 */
		JButton logoutButton = new JButton("Logout");
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login frame = new Login();
				AdminScreen.super.dispose();
				frame.setVisible(true);
			}
		});
		logoutButton.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		logoutButton.setForeground(Color.LIGHT_GRAY);
		logoutButton.setBackground(new Color(0, 77, 113));
		logoutButton.setBounds(0, 219, 172, 42);
		panel_1.add(logoutButton);
		
		/**
		 * Buton ce deschide ecranul pentru adugarea uui nou animal pe listele de adoptie ale adapostului
		 */
		JButton addAnimalButton = new JButton("Adaugare animal");
		addAnimalButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddAnimal frame = new AddAnimal();
				frame.setVisible(true);
			}
		});
		addAnimalButton.setForeground(Color.LIGHT_GRAY);
		addAnimalButton.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		addAnimalButton.setBackground(new Color(0, 77, 113));
		addAnimalButton.setBounds(0, 93, 172, 42);
		panel_1.add(addAnimalButton);
		
		/**
		 * Buton ce sterge un animal din listele de adoptie a adapostului;
		 * Acest lucru sterge si toate aplicatiile de adoptie asociate animalului
		 */
		JButton deleteAnimalButton = new JButton("Stergere animale");
		deleteAnimalButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Se introduce Id-ul animalului ce se doreste a fi sters
		        String input = JOptionPane.showInputDialog(null, "Introduceți ID-ul animalului de șters:", 
		                                                   "Ștergere animal", JOptionPane.QUESTION_MESSAGE);
		        if (input != null && !input.trim().isEmpty()) {
		            try {
		                int animalId = Integer.parseInt(input.trim());
		                boolean success = DatabaseManipulation.deleteAnimalById(animalId);
		                if (success) {
		                    JOptionPane.showMessageDialog(null, "Animalul cu ID-ul " + animalId + " a fost șters cu succes.", 
		                                                  "Succes", JOptionPane.INFORMATION_MESSAGE);
		                    updateAnimalList();
		                    refreshScreen();
		                } else {
		                    JOptionPane.showMessageDialog(null, "Animalul cu ID-ul " + animalId + " nu a fost găsit.", 
		                                                  "Eroare", JOptionPane.ERROR_MESSAGE);
		                }
		            } catch (NumberFormatException ex) {
		                JOptionPane.showMessageDialog(null, "ID-ul introdus nu este valid.", 
		                                              "Eroare", JOptionPane.ERROR_MESSAGE);
		            }
		        }
			}
		});
		deleteAnimalButton.setForeground(Color.LIGHT_GRAY);
		deleteAnimalButton.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		deleteAnimalButton.setBackground(new Color(0, 77, 113));
		deleteAnimalButton.setBounds(0, 135, 172, 42);
		panel_1.add(deleteAnimalButton);		
	}
}
