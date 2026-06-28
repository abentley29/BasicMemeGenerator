// Auto added libraries
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JTextField;
//import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

// Needed libraries I added
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.ButtonGroup;
import javax.swing.JMenuItem;

public class MemeGenerator extends JFrame {

	// Fields created with the design window
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField topTextField;
	private JTextField bottomTextField;
	private JTextField RTextField;
	private JTextField GTextField;
	private JTextField BTextField;
	private JTextField watermarkTextField;
	private JPanel colorPreviewPanel;
	//private JCheckBox bottomTextCheckBox;
	//private JCheckBox topTextCheckBox;
	private final JLabel bottomTextLabel = new JLabel("Bottom Text:");
	// Initial color for the text selected
	private Color selectedColor = Color.WHITE;
	
	// Local declarations I added in order to add actions and such to them
    private JPanel imagePanel;
    private JButton topTextClearButton;
    private JButton bottomTextClearButton;
    private JButton saveButton;
    private BufferedImage currentImage;
    private JMenuBar menuBar;

	
	private String currentFilePath = ""; // To keep track of the current file path for saving


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MemeGenerator frame = new MemeGenerator();
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
	public MemeGenerator() 
	{
		// Window set up and design from Design window
		setTitle("Meme Generator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1366, 768);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// The original C# code I was given used a PictureBox in order to handle the importing the image.
		// A PictureBox has its image property set in the designer, but that is not the case for my code.
		// Swing did not have a PictureBox option for me to use, so I had to manually load in the image below.
		// To do this I had to use ImageIO to get the photo
		
		// Get the meme picture from the src using a try
        try 
        {
        	currentImage = ImageIO.read(getClass().getResource("/Popular/sadPikachu.jpg"));
        }
        catch(Exception e)
        {
        	// 
            JOptionPane.showMessageDialog(this, "Could not load image...");
        }

        // Now that it was loaded in, I needed to put the photo in and format it to the JPanel.
        // To do so, I created paintComponent in order to do so.
        
        // Image panel with pikachu in it
		imagePanel = new JPanel()
				{
			@Override
			protected void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				
				// Scale the meme picture to the panel size
				if (currentImage != null) {
                g.drawImage(currentImage, 0, 0, getWidth(), getHeight(), null);
            }

			// Graphics for the text drawing over the image
            Graphics2D g2 = (Graphics2D) g;
            g2.setFont(new Font("Impact", Font.BOLD, 22));
            g2.setStroke(new BasicStroke(3));

            // The original code used Graphics.DrawString that formatted with a black shadow with white letters.
            // I tried to duplicate that by overlapping text
            
            // Draw top text using the function I defined
            // if (topTextCheckBox.isSelected()) {
                drawCenteredText(g2, topTextField.getText(), 30);
            // }

            // Draw bottom text using the function I defined
            // if (bottomTextCheckBox.isSelected()) {
                drawCenteredText(g2, bottomTextField.getText(), getHeight() - 20);
            // }
                
                // Watermark logic 
                String watermark = watermarkTextField.getText();
                if (watermark != null && !watermark.isEmpty()) {
                    g2.setFont(new Font("Impact", Font.BOLD, 18));
                    g2.setColor(new Color(255, 255, 255, 180)); // semi-transparent white

                    int wmWidth = g2.getFontMetrics().stringWidth(watermark);
                    int x = getWidth() - wmWidth - 10;
                    int y = getHeight() - 10;

                    g2.drawString(watermark, x, y);
                }
        }
    };
    
    // Scaling
    imagePanel.setBounds(500, 72, 792, 594);
	contentPane.add(imagePanel);
		
	// Create top text
	topTextField = new JTextField();
	topTextField.setFont(new Font("Tahoma", Font.PLAIN, 18));
	topTextField.setBounds(20, 72, 402, 32);
	contentPane.add(topTextField);
	topTextField.setColumns(10);
		
	// In the original code, when the text was updated it updated automatically in the meme
	// Although, in Java, I had to add listeners so it will repaint automatically.
	// Without it, it would only update the text on certain actions such as resizing the tab.
	
	// Listener so the meme text updates as the user types
	topTextField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() 
		{
		    public void insertUpdate(javax.swing.event.DocumentEvent e) { imagePanel.repaint(); }
		    public void removeUpdate(javax.swing.event.DocumentEvent e) { imagePanel.repaint(); }
		    public void changedUpdate(javax.swing.event.DocumentEvent e) { imagePanel.repaint(); }
		}
		);


	// Create bottom text
	bottomTextField = new JTextField();
	bottomTextField.setFont(new Font("Tahoma", Font.PLAIN, 18));
	bottomTextField.setColumns(10);
	bottomTextField.setBounds(20, 207, 402, 32);
	contentPane.add(bottomTextField);
		
	// Same for the bottom text of the meme
	bottomTextField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() 
		{
		    public void insertUpdate(javax.swing.event.DocumentEvent e) { imagePanel.repaint(); }
		    public void removeUpdate(javax.swing.event.DocumentEvent e) { imagePanel.repaint(); }
		    public void changedUpdate(javax.swing.event.DocumentEvent e) { imagePanel.repaint(); }
		}
		);
		
	// Create bottom checkbox
	// bottomTextCheckBox = new JCheckBox("Enable Bottom Text");
	// bottomTextCheckBox.setBounds(10, 87, 182, 20);
	// contentPane.add(bottomTextCheckBox);
		
	// Same issue with the repainting for the check boxes.
	// Listeners had to be added in order to the text box to take away or add the text without another action
	// Update or remove the text when the check box is selected 
	// bottomTextCheckBox.addActionListener(e -> imagePanel.repaint());

	// Create top check box
	// topTextCheckBox = new JCheckBox("Enable Top Text");
	// topTextCheckBox.setBounds(10, 27, 182, 14);
	// contentPane.add(topTextCheckBox);
		
	// Same logic for the top text
	// topTextCheckBox.addActionListener(e -> imagePanel.repaint());

	// Create save file
	saveButton = new JButton("Save Meme to Files");
	saveButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
	saveButton.addActionListener(new ActionListener() {
			
		// Auto generated and the code wont work without
		public void actionPerformed(ActionEvent e) {
			
		}
		}
	);
	
	saveButton.setBounds(746, 689, 273, 32);
	contentPane.add(saveButton);
		
	topTextClearButton = new JButton("Clear");
	topTextClearButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
	topTextClearButton.addActionListener(new ActionListener() {
		
		public void actionPerformed(ActionEvent e) {
			}
		}
	);
	
	topTextClearButton.setBounds(324, 114, 98, 32);
	contentPane.add(topTextClearButton);
		
	bottomTextClearButton = new JButton("Clear");
	bottomTextClearButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
	bottomTextClearButton.setBounds(324, 249, 98, 32);
	contentPane.add(bottomTextClearButton);
	bottomTextLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
	bottomTextLabel.setBounds(21, 165, 137, 32);
	contentPane.add(bottomTextLabel);
		
	JLabel topTextLabel = new JLabel("Top Text:");
	topTextLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
	topTextLabel.setBounds(20, 40, 98, 32);
	contentPane.add(topTextLabel);
	
	JRadioButton radioButtonBlue = new JRadioButton("Blue");
	radioButtonBlue.setFont(new Font("Tahoma", Font.PLAIN, 18));
	radioButtonBlue.setBounds(196, 355, 170, 20);
	contentPane.add(radioButtonBlue);
	
	JRadioButton radioButtonPink = new JRadioButton("Pink");
	radioButtonPink.setFont(new Font("Tahoma", Font.PLAIN, 18));
	radioButtonPink.setBounds(196, 377, 198, 20);
	contentPane.add(radioButtonPink);
	
	JRadioButton radioButtonWhite = new JRadioButton("White");
	radioButtonWhite.setFont(new Font("Tahoma", Font.PLAIN, 18));
	radioButtonWhite.setBounds(31, 355, 131, 20);
	contentPane.add(radioButtonWhite);
	// Since by default the text starts out white
	radioButtonWhite.setSelected(true);
	
	JRadioButton radioButtonBlack = new JRadioButton("Black");
	radioButtonBlack.setFont(new Font("Tahoma", Font.PLAIN, 18));
	radioButtonBlack.setBounds(31, 377, 111, 20);
	contentPane.add(radioButtonBlack);
	
	JRadioButton radioButtonRed = new JRadioButton("Red");
	radioButtonRed.setFont(new Font("Tahoma", Font.PLAIN, 18));
	radioButtonRed.setBounds(31, 399, 111, 20);
	contentPane.add(radioButtonRed);

	JRadioButton radioButtonRGB = new JRadioButton("Custom RGB Color");
	radioButtonRGB.setFont(new Font("Tahoma", Font.PLAIN, 18));
	radioButtonRGB.setBounds(31, 448, 237, 20);
	contentPane.add(radioButtonRGB);

	//RGB stuff

	RTextField = new JTextField();
	RTextField.setFont(new Font("Tahoma", Font.PLAIN, 18));
	RTextField.setColumns(10);
	RTextField.setBounds(108, 474, 170, 20);
	contentPane.add(RTextField);

	// Listener for the red text field for the color preview
	RTextField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
	    public void insertUpdate(javax.swing.event.DocumentEvent e) { colorPreview(); }
	    public void removeUpdate(javax.swing.event.DocumentEvent e) { colorPreview(); }
	    public void changedUpdate(javax.swing.event.DocumentEvent e) { colorPreview(); }
	});


	GTextField = new JTextField();
	GTextField.setFont(new Font("Tahoma", Font.PLAIN, 18));
	GTextField.setColumns(10);
	GTextField.setBounds(108, 504, 170, 20);
	contentPane.add(GTextField);
	 
	// Same for the green values
	GTextField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
	    public void insertUpdate(javax.swing.event.DocumentEvent e) { colorPreview(); }
	    public void removeUpdate(javax.swing.event.DocumentEvent e) { colorPreview(); }
	    public void changedUpdate(javax.swing.event.DocumentEvent e) { colorPreview(); }
	});

	BTextField = new JTextField();
	BTextField.setFont(new Font("Tahoma", Font.PLAIN, 18));
	BTextField.setColumns(10);
	BTextField.setBounds(108, 534, 170, 22);
	contentPane.add(BTextField);
	
	// Same for the blue values
	BTextField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
	    public void insertUpdate(javax.swing.event.DocumentEvent e) { colorPreview(); }
	    public void removeUpdate(javax.swing.event.DocumentEvent e) { colorPreview(); }
	    public void changedUpdate(javax.swing.event.DocumentEvent e) { colorPreview(); }
	});
	
	// Group all of the buttons together since they all change the color of text
	ButtonGroup colorGroup = new ButtonGroup();
	colorGroup.add(radioButtonWhite);
	colorGroup.add(radioButtonBlack);
	colorGroup.add(radioButtonRed);
	colorGroup.add(radioButtonRGB);
	colorGroup.add(radioButtonPink);
	colorGroup.add(radioButtonBlue);
	
	
	// Now the listeners for said buttons
	radioButtonWhite.addActionListener(e -> { selectedColor = Color.WHITE; imagePanel.repaint(); });
	radioButtonBlack.addActionListener(e -> { selectedColor = Color.BLACK; imagePanel.repaint(); });
	radioButtonRed.addActionListener(e -> { selectedColor = Color.RED; imagePanel.repaint(); });
	radioButtonPink.addActionListener(e -> { selectedColor = Color.PINK; imagePanel.repaint(); });
	radioButtonBlue.addActionListener(e -> { selectedColor = Color.BLUE; imagePanel.repaint(); });
	radioButtonRGB.addActionListener(e -> { 
		try{
			colorPreview();
			int r = Integer.parseInt(RTextField.getText());
			int g = Integer.parseInt(GTextField.getText());
			int b = Integer.parseInt(BTextField.getText());

			selectedColor = new Color(r, g, b);
			imagePanel.repaint();
			colorPreview();

		} catch (NumberFormatException ex){
			JOptionPane.showMessageDialog(this, "Numbers only 0-225");
		}
	});
	
	// Custom color preview box
	colorPreviewPanel = new JPanel();
	colorPreviewPanel.setBounds(301, 474, 80, 80); // adjust if needed
	colorPreviewPanel.setBackground(Color.WHITE); // default
	colorPreviewPanel.setBorder(javax.swing.BorderFactory.createLineBorder(Color.BLACK));
	contentPane.add(colorPreviewPanel);
	
	// Water mark logic
	JLabel watermarkLabel = new JLabel("Watermark Text:");
	watermarkLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
	watermarkLabel.setBounds(31, 610, 200, 20);
	contentPane.add(watermarkLabel);

	watermarkTextField = new JTextField();
	watermarkTextField.setFont(new Font("Tahoma", Font.PLAIN, 18));
	watermarkTextField.setBounds(31, 640, 392, 28);
	contentPane.add(watermarkTextField);

	// Listener for watermark 
	watermarkTextField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
	    public void insertUpdate(javax.swing.event.DocumentEvent e) { imagePanel.repaint(); }
	    public void removeUpdate(javax.swing.event.DocumentEvent e) { imagePanel.repaint(); }
	    public void changedUpdate(javax.swing.event.DocumentEvent e) { imagePanel.repaint(); }
	});

	menuBar = new JMenuBar();
	menuBar.setFont(new Font("Segoe UI", Font.PLAIN, 18));
	menuBar.setBounds(0, 0, 1362, 21);
	contentPane.add(menuBar);
	
	JMenu popularMenu = new JMenu("Popular");
	JMenu tvMenu = new JMenu("Television");
	JMenu moviesMenu = new JMenu("Movies");
	JMenu othersMenu = new JMenu("Others");
	JMenu customMenu = new JMenu("Custom");

	menuBar.add(popularMenu);
	menuBar.add(tvMenu);
	menuBar.add(moviesMenu);
	menuBar.add(othersMenu);
	menuBar.add(customMenu);

	JLabel lblTextColor = new JLabel("Text Color:");
	lblTextColor.setFont(new Font("Tahoma", Font.PLAIN, 18));
	lblTextColor.setBounds(10, 310, 98, 18);
	contentPane.add(lblTextColor);
	
	// Add the actual meme choices for each menu
	// Popular
    JMenuItem cryingJordanItem = new JMenuItem("Crying Jordan");
    cryingJordanItem.addActionListener(e -> switchMeme("Popular", "cryingJordan.jpeg", "Crying Jordan"));
    popularMenu.add(cryingJordanItem);

    JMenuItem mostInterestingItem = new JMenuItem("Most Interesting Man");
    mostInterestingItem.addActionListener(e -> switchMeme("Popular", "mostInterestingManInTheWorld.jpeg", "Most Interesting Man"));
    popularMenu.add(mostInterestingItem);

    JMenuItem kobeItem = new JMenuItem("Kobe");
    kobeItem.addActionListener(e -> switchMeme("Popular", "kobe.jpg", "Kobe"));
    popularMenu.add(kobeItem);

    JMenuItem willyWonkaItem = new JMenuItem("Willy Wonka");
    willyWonkaItem.addActionListener(e -> switchMeme("Popular", "willyWonka.jpg", "Willy Wonka"));
    popularMenu.add(willyWonkaItem);

    JMenuItem picardGoofyItem = new JMenuItem("Picard Goofy");
    picardGoofyItem.addActionListener(e -> switchMeme("Popular", "picardGoofy.jpeg", "Picard Goofy"));
    popularMenu.add(picardGoofyItem);

    JMenuItem disasterGirlItem = new JMenuItem("Disaster Girl");
    disasterGirlItem.addActionListener(e -> switchMeme("Popular", "disasterGirl.jpeg", "Disaster Girl"));
    popularMenu.add(disasterGirlItem);

    JMenuItem sadPikachuItem = new JMenuItem("Sad Pikachu");
    sadPikachuItem.addActionListener(e -> switchMeme("Popular", "sadPikachu.jpg", "Sad Pikachu"));
    popularMenu.add(sadPikachuItem);


// Television
    JMenuItem alfItem = new JMenuItem("ALF");
    alfItem.addActionListener(e -> switchMeme("Television", "alf.jpeg", "ALF"));
    tvMenu.add(alfItem);

    JMenuItem alBundayItem = new JMenuItem("Al Bunday");
    alBundayItem.addActionListener(e -> switchMeme("Television", "alBunday.jpeg", "Al Bunday"));
    tvMenu.add(alBundayItem);

    JMenuItem fryItem = new JMenuItem("Fry Futurama");
    fryItem.addActionListener(e -> switchMeme("Television", "fryFuturama.jpeg", "Fry Futurama"));
    tvMenu.add(fryItem);


// Movies
    JMenuItem deadpoolItem = new JMenuItem("Deadpool");
    deadpoolItem.addActionListener(e -> switchMeme("Movies", "deadpool.jpg", "Deadpool"));
    moviesMenu.add(deadpoolItem);

    JMenuItem deadpoolWolverineItem = new JMenuItem("Deadpool and Wolverine");
    deadpoolWolverineItem.addActionListener(e -> switchMeme("Movies", "deadpoolWolverine.jpeg", "Deadpool and Wolverine"));
    moviesMenu.add(deadpoolWolverineItem);

    JMenuItem hanPointItem = new JMenuItem("Han Point");
    hanPointItem.addActionListener(e -> switchMeme("Movies", "hanPoint.jpg", "Han Point"));
    moviesMenu.add(hanPointItem);

    JMenuItem hanSoloEmpireItem = new JMenuItem("Han Solo Empire");
    hanSoloEmpireItem.addActionListener(e -> switchMeme("Movies", "hanSoloEmpire.jpeg", "Han Solo Empire"));
    moviesMenu.add(hanSoloEmpireItem);

    JMenuItem tenThingsItem = new JMenuItem("10 Things");
    tenThingsItem.addActionListener(e -> switchMeme("Movies", "10things2.jpg", "10 Things"));
    moviesMenu.add(tenThingsItem);

    JMenuItem mordorItem = new JMenuItem("Mordor");
    mordorItem.addActionListener(e -> switchMeme("Movies", "mordor.jpeg", "Mordor"));
    moviesMenu.add(mordorItem);


// Others
    JMenuItem assumptionItem = new JMenuItem("Assumption");
    assumptionItem.addActionListener(e -> switchMeme("Others", "assumption.jpg", "Assumption"));
    othersMenu.add(assumptionItem);

    JMenuItem fishItem = new JMenuItem("Stole My Fish");
    fishItem.addActionListener(e -> switchMeme("Others", "stoleMyFish.jpg", "Stole My Fish"));
    othersMenu.add(fishItem);

    JMenuItem kayodeItem = new JMenuItem("Kayode");
    kayodeItem.addActionListener(e -> switchMeme("Others", "kayode.jpg", "Kayode"));
    othersMenu.add(kayodeItem);


// Custom Menu
JMenuItem customItem = new JMenuItem("Load Custom Image");
customItem.addActionListener(new ActionListener()
{
	public void actionPerformed(ActionEvent e)
	{
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Select a Custom Image");
		chooser.setFileFilter(new FileNameExtensionFilter(
			"Image Files", "jpg", "jpeg", "png", "gif", "bmp"
		));

		int result = chooser.showOpenDialog(MemeGenerator.this);

		if (result == JFileChooser.APPROVE_OPTION)
		{
			File selectedFile = chooser.getSelectedFile();
			switchMeme(selectedFile.getParent(), selectedFile.getName(), selectedFile.getName());
		}
	}
});

customMenu.add(customItem);
JLabel lblRed = new JLabel("Red:");
lblRed.setFont(new Font("Tahoma", Font.PLAIN, 18));
lblRed.setBounds(41, 474, 47, 18);
contentPane.add(lblRed);
JLabel lblGreen = new JLabel("Green:");
lblGreen.setFont(new Font("Tahoma", Font.PLAIN, 18));
lblGreen.setBounds(41, 505, 67, 18);
contentPane.add(lblGreen);
JLabel lblBlue = new JLabel("Blue:");
lblBlue.setFont(new Font("Tahoma", Font.PLAIN, 18));
lblBlue.setBounds(41, 538, 67, 18);
contentPane.add(lblBlue);




	// Once again, C# will automatically refresh things when updated.
	// Java needs listeners in order to do so, so I added some for the clear buttons.
	
	// Handle the clear top text button action
    topTextClearButton.addActionListener(new ActionListener() 
    {
    	public void actionPerformed(ActionEvent e)
    	{
    		clearMeme("top");
    	}
        }
        );
        
    // Handle the clear bottom text button action
    bottomTextClearButton.addActionListener(new ActionListener() 
    {
    	public void actionPerformed(ActionEvent e)
    	{
			clearMeme("bot");
    	}
        }
		
	);
	
    
    // Have the save meme button action
    saveButton.addActionListener(new ActionListener()
        {
    	public void actionPerformed(ActionEvent e)
    	{
    		saveMeme();
    		
    	}
    	}
    );
    }
				
	// The C# code was able to use Graphics.MeasureString in order to center the text on the meme.
	// In order to do this in Java, I used Font metrics which basically did the same thing.
	
// Handles version 3 image switching.
// If there is already an image loaded and the user chooses another,
// ask them if they are sure before changing it.
private void switchMeme(String folderName, String fileName, String displayName)
{
	String newImagePath = folderName + "/" + fileName;

	if (!currentFilePath.isEmpty() && !currentFilePath.equals(newImagePath))
	{
		int result = JOptionPane.showConfirmDialog(
			this,
			"Are you sure you want to change to " + displayName + "?",
			"Confirm Image Change",
			JOptionPane.YES_NO_OPTION
		);

		if (result != JOptionPane.YES_OPTION)
		{
			return;
		}
	}

	if (loadImage(folderName, fileName))
	{
		currentFilePath = newImagePath;
		imagePanel.repaint();
	}
	else
	{
		JOptionPane.showMessageDialog(this, "Could not load image: " + displayName);
	}
}

// Tries to load the meme image from the folder tied to the menu.
// This lets the menu option immediately update the image on the right side.
private boolean loadImage(String folderName, String fileName)
{
	String[] possiblePaths =
	{
		folderName + "/" + fileName,
		"src/" + folderName + "/" + fileName,
		fileName
	};

	for (String path : possiblePaths)
	{
		try
		{
			File file = new File(path);

			if (file.exists())
			{
				currentImage = ImageIO.read(file);
				return true;
			}
		}
		catch (Exception e)
		{
		}

		try
		{
			java.net.URL resource = getClass().getResource("/" + path);

			if (resource != null)
			{
				currentImage = ImageIO.read(resource);
				return true;
			}
		}
		catch (Exception e)
		{
		}
	}

	return false;
}


	// Draw the centered text on the meme like the original program
	private void drawCenteredText(Graphics2D g2, String text, int y)
	{
	    int x;

	    // Auto-shrink font so text never goes off the image
	    int size = 40;
	    g2.setFont(new Font("Impact", Font.PLAIN, size));

	    FontMetrics fm = g2.getFontMetrics();

	    // Shrink the text until it fits inside the image panel
	    while (fm.stringWidth(text) > imagePanel.getWidth() - 20 && size > 12)
	    {
	        size--;
	        g2.setFont(new Font("Impact", Font.PLAIN, size));
	        fm = g2.getFontMetrics();
	    }

	    // Center the text horizontally
	    x = (imagePanel.getWidth() - fm.stringWidth(text)) / 2;

	    // Black shadow of the text
	    g2.setColor(Color.BLACK);
	    g2.drawString(text, x + 2, y + 2);

	    // Main text in selected color
	    g2.setColor(selectedColor);
	    g2.drawString(text, x, y);
	}
	
	// The original code was not able to save an image properly with the text.
	// That code used SaveFileDialog to export the meme.
	// For my code in java, I was able to successfully export it with BufferedImage and then I used JFileChooser to export it.
	// This allows the user to browse their files and choose where to save it.
	
	// Function to save the meme to the files
	private void saveMeme()
	{
		// Try catch for if there is a problem
		try
		{
			// Format and create the image with the font
			BufferedImage output = new BufferedImage
					(
					imagePanel.getWidth(), imagePanel.getHeight(), BufferedImage.TYPE_INT_RGB
					);
			Graphics2D g2 = output.createGraphics();
			imagePanel.paint(g2);
			g2.dispose();
			
			// Let the user choose where the file will be saved
			JFileChooser choose = new JFileChooser();
			choose.setDialogTitle("Save Your Meme");
			
			// If to see if it is successful or not to print the proper message
			if(choose.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
			{
				// Successful file save
				File file = choose.getSelectedFile();

				// Always force .jpg extension unless user already typed .jpg or .jpeg
				String path = file.getAbsolutePath().toLowerCase();

				if (!path.endsWith(".jpg") && !path.endsWith(".jpeg")) {
				    file = new File(file.getAbsolutePath() + ".jpg");
				}

				ImageIO.write(output, "jpg", file);
				
				JOptionPane.showMessageDialog(this, "Your meme has saved!");
			}
		}
		
		// Catch if problem
		catch(Exception except)
		{
			// If the file did not save let the user know
			JOptionPane.showMessageDialog(this, "Error, meme did not save.");
		}
	}
	private void clearMeme(String pos)
	{
	    int result = JOptionPane.showConfirmDialog(
	        this,
	        "Are you sure you want to clear your text?",
	        "Confirm Clear",
	        JOptionPane.YES_NO_OPTION
	    );

	    if (result != JOptionPane.YES_OPTION) {
	        return; // user clicked NO
	    }

	    if (pos == "top") {
	        topTextField.setText("");
	    }
	    if (pos == "bot") {
	        bottomTextField.setText("");
	    }

	    imagePanel.repaint();

	    // Final message AFTER clearing
	    JOptionPane.showMessageDialog(this, "Text Cleared.");
	}
	
	// Logic for the color preview box
	private void colorPreview() {
	    try {
	    	// Parse the text from the RGB text fields
	        int r = Integer.parseInt(RTextField.getText());
	        int g = Integer.parseInt(GTextField.getText());
	        int b = Integer.parseInt(BTextField.getText());

	        // Values for rgb
	        r = Math.max(0, Math.min(255, r));
	        g = Math.max(0, Math.min(255, g));
	        b = Math.max(0, Math.min(255, b));

	        colorPreviewPanel.setBackground(new Color(r, g, b));
	    } catch (Exception ex) {
	        // If invalid input just show the same color as the background
	        colorPreviewPanel.setBackground(Color.LIGHT_GRAY);
	    }
	    
	}
	



}
