import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingWorker;
import javax.swing.JComboBox;

public class editVideo {

	private JButton jbChoose;
	private JButton jbReplace;
	private JTextField userInfo;
	private JTextField titleName;
	private JTextField titleText;
	SpringLayout layout = new SpringLayout();
	private JButton jbTitlePage;
	private JTextField mp4Choice;
	protected File titleInputFile;
	private JTextField mp4Display;
	private JComboBox fonts;
	private JTextField fontSelection;
	private String fontName;
	private JComboBox fontSize;
	private JTextField fontSizeSelection;
	private String fontSizeString;
	
	public void insertTitlePageTab(final JTabbedPane pane){
		JPanel titlePagePanel = new JPanel();
		pane.addTab("Add Title", titlePagePanel);
		setTitlePageFeatures(titlePagePanel);
	}

	public void insertCreditPageTab(final JTabbedPane pane){
		JPanel creditPagePanel = new JPanel();
		pane.addTab("Add Credits", creditPagePanel);
		//setCreditsPageFeatures(creditPagePanel);
	}
	
	
	
	//method to add tab to the tabbed pane 
	/*public void insertReplaceAudio(final JTabbedPane pane){
		//add tabs at the top of the tabbed pane
		JPanel removePanel = new JPanel();
		pane.addTab( "Replace Audio", removePanel);
		setReplacePanelFeatures(removePanel);
	}
	*/

	private void setTitlePageFeatures(final JPanel panel){	
		panel.setLayout(layout);
		addVideoChooser(panel);
		addPanelFeatures(panel);
		addFontOptions(panel);
		addFontSizeOptions(panel);



		jbTitlePage = new JButton("Set Title Page");
		panel.add(jbTitlePage);

		//move the text box containing user file choice button to its location
		layout.putConstraint(SpringLayout.WEST, jbTitlePage, 15, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, jbTitlePage, 110, SpringLayout.NORTH, mp4Choice);
		layout.putConstraint(SpringLayout.NORTH,fonts,80,SpringLayout.NORTH,mp4Choice);
		layout.putConstraint(SpringLayout.WEST,fonts,105,SpringLayout.WEST,panel);
		layout.putConstraint(SpringLayout.NORTH,fontSelection,83,SpringLayout.NORTH,mp4Choice);
		layout.putConstraint(SpringLayout.WEST, fontSelection, 15, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH,fontSize,80,SpringLayout.NORTH,mp4Choice);
		layout.putConstraint(SpringLayout.WEST, fontSize, 300, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, fontSizeSelection, 83, SpringLayout.NORTH, mp4Choice);
		layout.putConstraint(SpringLayout.WEST,fontSizeSelection,187,SpringLayout.WEST,panel);


		jbTitlePage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("asjfa");
				getTitleScreenshotSW titleScreenShotSW = new getTitleScreenshotSW();
				try {
					titleScreenShotSW.doInBackground();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}//end action performed
		}); // end add action listener
	}
	
	private void addFontOptions(JPanel panel){
		String[] fontOptions = {"Sans","Mono","Serif"};
		fonts = new JComboBox(fontOptions);
		panel.add(fonts);
		fontSelection = new JTextField();
		fontSelection.setText("Select Font: ");
		fontSelection.setEditable(false);
		panel.add(fontSelection);
		fonts.setSelectedIndex(0);
		fonts.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
				fontName = (String)cb.getSelectedItem();
			}
			
		});
		
	}
	
	private void addFontSizeOptions(JPanel panel){
		String[] fontSizeOptions = {"8","10","12","14","16","18","20"};
		fontSize = new JComboBox(fontSizeOptions);
		panel.add(fontSize);
		fontSizeSelection = new JTextField();
		fontSizeSelection.setText("Select Font Size: ");
		fontSizeSelection.setEditable(false);
		panel.add(fontSizeSelection);
		fontSize.setSelectedIndex(3);
		fontSize.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cbSize = (JComboBox)e.getSource();
				fontSizeString = (String)cbSize.getSelectedItem();
				
			}
			
		});
		
		
	}


	private class getTitleScreenshotSW extends SwingWorker<Integer,String>{

		protected void done(){
			BufferedImage img = null;
			try{
				img = ImageIO.read(new File("TitleScreenShot.jpg"));
				System.out.println("donedone");
			} catch(IOException e){
				
			}
			userInfo.setText("Displaying Preview");	
		}


		@Override
		protected Integer doInBackground() throws Exception {
			System.out.println("doing");
			String file = titleInputFile.getAbsolutePath();
			String[] cmd = {"avconv","-i",file,"-vframes","1","-an","-s","800x444","-ss","30","TitleScreenShot.jpg"};
			ProcessBuilder builder = new ProcessBuilder(cmd);
			Process process = builder.start();
			System.out.println("doing");

			return null;
			
		}


	}

	private void addPanelFeatures(final JPanel panel){
		//add text box requesting user to input an output name
		titleText = new JTextField();
		titleText.setText("Output file name :");
		titleText.setPreferredSize(new Dimension(115,20));
		titleText.setEditable(false);
		panel.add(titleText);
		//move the title text to its location on the screen
		layout.putConstraint(SpringLayout.WEST, titleText, 15, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, titleText, 55, SpringLayout.NORTH, panel);

		//add the user input box for the title of the output file
		titleName = new JTextField();
		titleName.setPreferredSize(new Dimension(115,20));
		panel.add(titleName);
		//move the mp3 output file name to its location on the screen
		layout.putConstraint(SpringLayout.WEST, titleName, 125, SpringLayout.WEST, titleText);
		layout.putConstraint(SpringLayout.NORTH, titleName, 55, SpringLayout.NORTH, panel);


		mp4Display = new JTextField();
		mp4Display.setText(".mp4");
		mp4Display.setEditable(false);
		titleName.setPreferredSize(new Dimension(115,20));
		panel.add(mp4Display);
		//move the choose file button to its location
		layout.putConstraint(SpringLayout.WEST, mp4Display, 115
				, SpringLayout.WEST, titleName);
		layout.putConstraint(SpringLayout.NORTH, mp4Display, 55, SpringLayout.NORTH, panel);

		//add textbox for error messages to be conveyed to the user
		userInfo = new JTextField();
		userInfo.setPreferredSize(new Dimension(275,20));
		userInfo.setEditable(false);

		//move the text box containing user file choice button to its location
		layout.putConstraint(SpringLayout.WEST, userInfo, 15, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, userInfo, 55, SpringLayout.NORTH, jbChoose);
		panel.add(userInfo);

	}

	private void addVideoChooser(final JPanel panel){
		//create and add functionality for file choosing button
		jbChoose = new JButton("Choose File");
		JFileChooser jfile = null;
		jbChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Opens JFileChooser when button pressed
				JFileChooser jfile = new JFileChooser();
				int response = jfile.showOpenDialog(null);
				if (response == JFileChooser.APPROVE_OPTION) {
					titleInputFile = jfile.getSelectedFile();
					String chosenFile = jfile.getSelectedFile().toString();
					mp4Choice.setText(chosenFile);
				}
				jfile.setVisible(true);
			}
		});
		panel.add(jbChoose);


		//move the choose file button to its location
		layout.putConstraint(SpringLayout.WEST, jbChoose, 15, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, jbChoose, 25, SpringLayout.NORTH, panel);

		//creates text field to store which file the user input
		mp4Choice = new JTextField();
		mp4Choice.setPreferredSize(new Dimension(150,20));
		mp4Choice.setEditable(false);
		panel.add(mp4Choice);

		//move the text box containing user file choice button to its location
		layout.putConstraint(SpringLayout.WEST, mp4Choice, 125, SpringLayout.WEST, jbChoose);
		layout.putConstraint(SpringLayout.NORTH, mp4Choice, 27, SpringLayout.NORTH, panel);



	}
	
	private void startTitleEditing(String chosenFile){
		
	}
}

