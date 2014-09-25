
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

public class editTitle {

	private JButton _jbChoose;
	private JButton _jbPreview;
	private JButton _jbTitlePage;
	private JTextField _userInfo;
	private JTextField _titleName;
	private JTextField _titleText;
	private JTextField _mp4Choice;
	private JTextField _mp4Display;
	private JTextField _fontSelection;
	private JTextField _fontSizeSelection;
	private JComboBox _fonts;
	private JComboBox _fontSize;
	private String _fontName;
	private String _fontSizeString;
	protected File _titleInputFile;
	SpringLayout _layout = new SpringLayout();
	
	
	
	
	
	
	public void insertTitlePageTab(final JTabbedPane pane){
		JPanel titlePagePanel = new JPanel();
		pane.addTab("Add Title", titlePagePanel);
		setTitlePageFeatures(titlePagePanel);
	}

	private void setTitlePageFeatures(final JPanel panel){	
		panel.setLayout(_layout);
		addVideoChooser(panel);
		addPanelFeatures(panel);
		addFontOptions(panel);
		addFontSizeOptions(panel);
		
		_jbPreview = new JButton("Show Preview");
		_jbTitlePage = new JButton("Set Title Page");
		panel.add(_jbTitlePage);
		panel.add(_jbPreview);

		//move the text box containing user file choice button to its location
		
		_layout.putConstraint(SpringLayout.NORTH, _jbPreview, 110, SpringLayout.NORTH, _mp4Choice);
		_layout.putConstraint(SpringLayout.WEST, _jbPreview, 15, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH, _jbTitlePage, 110, SpringLayout.NORTH, _mp4Choice);
		_layout.putConstraint(SpringLayout.WEST, _jbTitlePage, 160, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH,_fonts,80,SpringLayout.NORTH,_mp4Choice);
		_layout.putConstraint(SpringLayout.WEST,_fonts,105,SpringLayout.WEST,panel);
		_layout.putConstraint(SpringLayout.NORTH,_fontSelection,83,SpringLayout.NORTH,_mp4Choice);
		_layout.putConstraint(SpringLayout.WEST, _fontSelection, 15, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH,_fontSize,80,SpringLayout.NORTH,_mp4Choice);
		_layout.putConstraint(SpringLayout.WEST, _fontSize, 300, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH, _fontSizeSelection, 83, SpringLayout.NORTH, _mp4Choice);
		_layout.putConstraint(SpringLayout.WEST,_fontSizeSelection,187,SpringLayout.WEST,panel);


		_jbTitlePage.addActionListener(new ActionListener() {

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
		_fonts = new JComboBox(fontOptions);
		panel.add(_fonts);
		_fontSelection = new JTextField();
		_fontSelection.setText("Select Font: ");
		_fontSelection.setEditable(false);
		panel.add(_fontSelection);
		_fonts.setSelectedIndex(0);
		_fonts.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
				_fontName = (String)cb.getSelectedItem();
			}
			
		});
		
	}
	
	private void addFontSizeOptions(JPanel panel){
		String[] fontSizeOptions = {"8","10","12","14","16","18","20"};
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
			String file = _titleInputFile.getAbsolutePath();
			String[] cmd = {"avconv","-i",file,"-vframes","1","-an","-s","800x444","-ss","30","TitleScreenShot.jpg"};
			ProcessBuilder builder = new ProcessBuilder(cmd);
			Process process = builder.start();
			System.out.println("doing");

			return null;
			
		}


	}

	private void addPanelFeatures(final JPanel panel){
		//add text box requesting user to input an output name
		_titleText = new JTextField();
		_titleText.setText("Output file name :");
		_titleText.setPreferredSize(new Dimension(115,20));
		_titleText.setEditable(false);
		panel.add(_titleText);
		//move the title text to its location on the screen
		_layout.putConstraint(SpringLayout.WEST, _titleText, 15, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH, _titleText, 55, SpringLayout.NORTH, panel);

		//add the user input box for the title of the output file
		_titleName = new JTextField();
		_titleName.setPreferredSize(new Dimension(115,20));
		panel.add(_titleName);
		//move the mp3 output file name to its location on the screen
		_layout.putConstraint(SpringLayout.WEST, _titleName, 125, SpringLayout.WEST, _titleText);
		_layout.putConstraint(SpringLayout.NORTH, _titleName, 55, SpringLayout.NORTH, panel);


		_mp4Display = new JTextField();
		_mp4Display.setText(".mp4");
		_mp4Display.setEditable(false);
		_titleName.setPreferredSize(new Dimension(115,20));
		panel.add(_mp4Display);
		//move the choose file button to its location
		_layout.putConstraint(SpringLayout.WEST, _mp4Display, 115
				, SpringLayout.WEST, _titleName);
		_layout.putConstraint(SpringLayout.NORTH, _mp4Display, 55, SpringLayout.NORTH, panel);

		//add textbox for error messages to be conveyed to the user
		_userInfo = new JTextField();
		_userInfo.setPreferredSize(new Dimension(275,20));
		_userInfo.setEditable(false);

		//move the text box containing user file choice button to its location
		_layout.putConstraint(SpringLayout.WEST, _userInfo, 15, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH, _userInfo, 55, SpringLayout.NORTH, _jbChoose);
		panel.add(_userInfo);

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
					_titleInputFile = jfile.getSelectedFile();
					String chosenFile = jfile.getSelectedFile().toString();
					_mp4Choice.setText(chosenFile);
				}
				jfile.setVisible(true);
			}
		});
		panel.add(_jbChoose);


		//move the choose file button to its location
		_layout.putConstraint(SpringLayout.WEST, _jbChoose, 15, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH, _jbChoose, 25, SpringLayout.NORTH, panel);

		//creates text field to store which file the user input
		_mp4Choice = new JTextField();
		_mp4Choice.setPreferredSize(new Dimension(150,20));
		_mp4Choice.setEditable(false);
		panel.add(_mp4Choice);

		//move the text box containing user file choice button to its location
		_layout.putConstraint(SpringLayout.WEST, _mp4Choice, 125, SpringLayout.WEST, _jbChoose);
		_layout.putConstraint(SpringLayout.NORTH, _mp4Choice, 27, SpringLayout.NORTH, panel);



	}
	
	private void startTitleEditing(String chosenFile){
		
	}
}

