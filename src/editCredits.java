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


public class editCredits {

	private JButton _jbPreview;
	private JButton _jbChoose;
	private JButton jbReplace;
	private JButton _jbCreditsPage;
	private JTextField _userInfo;
	private JTextField _creditsName;
	private JTextField _mp4Choice;
	private JTextField _creditsText;
	private JTextField _mp4Display;
	private JTextField _fontSelection;
	private JTextField _fontSizeSelection;
	private JTextField _creditsTextInformer;
	private JTextField _creditsTextRaw;
	private JComboBox fonts;
	private JComboBox _fontSize;
	private String _fontName;
	private String _fontSizeString;
	protected File _creditsInputFile;	

	SpringLayout _layout = new SpringLayout();

	public void insertCreditPageTab(final JTabbedPane pane){
		JPanel creditPagePanel = new JPanel();
		pane.addTab("Create Credits Page", creditPagePanel);
		setCreditsPageFeatures(creditPagePanel);
	}

	private void setCreditsPageFeatures(final JPanel panel){	
		panel.setLayout(_layout);
		addVideoChooser(panel);
		addPanelFeatures(panel);
		addFontOptions(panel);
		addFontSizeOptions(panel);

		_jbPreview = new JButton("Show Preview");
		_jbCreditsPage = new JButton("Set Credits Page");
		panel.add(_jbCreditsPage);
		panel.add(_jbPreview);


		//move the text box containing user file choice button to its location
		_layout.putConstraint(SpringLayout.NORTH, _jbPreview, 130, SpringLayout.NORTH, _mp4Choice);
		_layout.putConstraint(SpringLayout.WEST, _jbPreview, 15, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH, _jbCreditsPage, 130, SpringLayout.NORTH, _mp4Choice);
		_layout.putConstraint(SpringLayout.WEST, _jbCreditsPage, 160, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH,fonts,77,SpringLayout.NORTH,_mp4Choice);
		_layout.putConstraint(SpringLayout.WEST,fonts,105,SpringLayout.WEST,panel);
		_layout.putConstraint(SpringLayout.NORTH,_fontSelection,80,SpringLayout.NORTH,_mp4Choice);
		_layout.putConstraint(SpringLayout.WEST, _fontSelection, 15, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH,_fontSize,77,SpringLayout.NORTH,_mp4Choice);
		_layout.putConstraint(SpringLayout.WEST, _fontSize, 300, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH, _fontSizeSelection, 80, SpringLayout.NORTH, _mp4Choice);
		_layout.putConstraint(SpringLayout.WEST,_fontSizeSelection,187,SpringLayout.WEST,panel);


		_jbCreditsPage.addActionListener(new ActionListener() {

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
		_fontSelection = new JTextField();
		_fontSelection.setText("Select Font: ");
		_fontSelection.setEditable(false);
		panel.add(_fontSelection);
		fonts.setSelectedIndex(0);
		fonts.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
				_fontName = (String)cb.getSelectedItem();
			}

		});

	}

	private void addFontSizeOptions(JPanel panel){
		String[] fontSizeOptions = {"40","46","52","58","64","70","76"};
		_fontSize = new JComboBox(fontSizeOptions);
		panel.add(_fontSize);
		_fontSizeSelection = new JTextField();
		_fontSizeSelection.setText("Select Font Size: ");
		_fontSizeSelection.setEditable(false);
		panel.add(_fontSizeSelection);
		_fontSize.setSelectedIndex(3);
		_fontSize.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cbSize = (JComboBox)e.getSource();
				_fontSizeString = (String)cbSize.getSelectedItem();

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
			_userInfo.setText("Displaying Preview");	
		}


		@Override
		protected Integer doInBackground() throws Exception {
			System.out.println("doing");
			String file = _creditsInputFile.getAbsolutePath();
			String[] cmd = {"avconv","-i",file,"-vframes","1","-an","-s","800x444","-ss","30","TitleScreenShot.jpg"};
			ProcessBuilder builder = new ProcessBuilder(cmd);
			Process process = builder.start();
			System.out.println("doing");

			return null;

		}


	}

	private void addPanelFeatures(final JPanel panel){
		//add text box requesting user to input an output name
		_creditsText = new JTextField();
		_creditsText.setText("Output file name :");
		_creditsText.setPreferredSize(new Dimension(115,20));
		_creditsText.setEditable(false);
		panel.add(_creditsText);
		//move the title text to its location on the screen
		_layout.putConstraint(SpringLayout.WEST, _creditsText, 15, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH, _creditsText, 42, SpringLayout.NORTH, panel);

		//add the user input box for the title of the output file
		_creditsName = new JTextField();
		_creditsName.setPreferredSize(new Dimension(115,20));
		panel.add(_creditsName);
		//move the mp3 output file name to its location on the screen
		_layout.putConstraint(SpringLayout.WEST, _creditsName, 125, SpringLayout.WEST, _creditsText);
		_layout.putConstraint(SpringLayout.NORTH, _creditsName, 42, SpringLayout.NORTH, panel);


		_mp4Display = new JTextField();
		_mp4Display.setText(".mp4");
		_mp4Display.setEditable(false);
		_creditsName.setPreferredSize(new Dimension(115,20));
		panel.add(_mp4Display);
		//move the choose file button to its location
		_layout.putConstraint(SpringLayout.WEST, _mp4Display, 115, SpringLayout.WEST, _creditsName);
		_layout.putConstraint(SpringLayout.NORTH, _mp4Display, 42, SpringLayout.NORTH, panel);

		//add textbox for error messages to be conveyed to the user
		_userInfo = new JTextField();
		_userInfo.setPreferredSize(new Dimension(275,20));
		_userInfo.setEditable(false);

		//move the text box containing user file choice button to its location
		_layout.putConstraint(SpringLayout.WEST, _userInfo, 15, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH, _userInfo, 55, SpringLayout.NORTH, _jbChoose);
		panel.add(_userInfo);
		
		_creditsTextInformer = new JTextField();
		_creditsTextInformer.setText("Please specify text to add to video: ");
		_creditsTextInformer.setEditable(false);
		_layout.putConstraint(SpringLayout.NORTH, _creditsTextInformer, 120,SpringLayout.NORTH, panel);
		_layout.putConstraint(SpringLayout.WEST, _creditsTextInformer, 15, SpringLayout.WEST, panel);
		panel.add(_creditsTextInformer);
		
		_creditsTextRaw = new JTextField();
		_creditsTextRaw.setPreferredSize(new Dimension (300,20));
		panel.add(_creditsTextRaw);
		_layout.putConstraint(SpringLayout.NORTH, _creditsTextRaw, 120,SpringLayout.NORTH, panel);
		_layout.putConstraint(SpringLayout.WEST, _creditsTextRaw, 240, SpringLayout.WEST, _jbChoose);

	}

	private void addVideoChooser(final JPanel panel){
		//create and add functionality for file choosing button
		_jbChoose = new JButton("Choose File");
		JFileChooser jfile = null;
		_jbChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Opens JFileChooser when button pressed
				JFileChooser jfile = new JFileChooser();
				int response = jfile.showOpenDialog(null);
				if (response == JFileChooser.APPROVE_OPTION) {
					_creditsInputFile = jfile.getSelectedFile();
					String chosenFile = jfile.getSelectedFile().toString();
					_mp4Choice.setText(chosenFile);
				}
				jfile.setVisible(true);
			}
		});
		panel.add(_jbChoose);


		//move the choose file button to its location
		_layout.putConstraint(SpringLayout.WEST, _jbChoose, 15, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH, _jbChoose, 10, SpringLayout.NORTH, panel);

		//creates text field to store which file the user input
		_mp4Choice = new JTextField();
		_mp4Choice.setPreferredSize(new Dimension(150,20));
		_mp4Choice.setEditable(false);
		panel.add(_mp4Choice);

		//move the text box containing user file choice button to its location
		_layout.putConstraint(SpringLayout.WEST, _mp4Choice, 125, SpringLayout.WEST, _jbChoose);
		_layout.putConstraint(SpringLayout.NORTH, _mp4Choice, 12, SpringLayout.NORTH, panel);



	}

	private void startTitleEditing(String chosenFile){

	}
}


