
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
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
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingWorker;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;

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
	private JTextField _titleTextInformer;
	private JTextField _titleTextRaw;
	private JComboBox _fonts;
	private JComboBox _fontSize;
	private String _fontName;
	private String _fontSizeString;
	private String _selectedTitleText;
	private String _drawText = "\"drawtext=fontfile=/usr/share/fonts/truetype/freefont/Free";
	private String _drawText2 = ".ttf: text='";
	private Image preview;
	protected File _previewPicture;
	protected File _titleInputFile;
	SpringLayout _layout = new SpringLayout();
	
	
	
	
	
	
	public void insertTitlePageTab(final JTabbedPane pane){
		JPanel titlePagePanel = new JPanel();
		pane.addTab("Create Title Page", titlePagePanel);
		setTitlePageFeatures(titlePagePanel);
		_fontName = "Sans";
		_fontSizeString = "58";
	}

	private void setTitlePageFeatures(final JPanel panel){	
		panel.setLayout(_layout);
		addVideoChooser(panel);
		addPanelFeatures(panel);
		addFontOptions(panel);
		addFontSizeOptions(panel);
		
		_jbPreview = new JButton("Show Preview");
		_jbTitlePage = new JButton("Create Title Page");
		panel.add(_jbTitlePage);
		panel.add(_jbPreview);

		//move the text box containing user file choice button to its location
		
		_layout.putConstraint(SpringLayout.NORTH, _jbPreview, 130, SpringLayout.NORTH, _mp4Choice);
		_layout.putConstraint(SpringLayout.WEST, _jbPreview, 15, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH, _jbTitlePage, 130, SpringLayout.NORTH, _mp4Choice);
		_layout.putConstraint(SpringLayout.WEST, _jbTitlePage, 160, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH,_fonts,77,SpringLayout.NORTH,_mp4Choice);
		_layout.putConstraint(SpringLayout.WEST,_fonts,105,SpringLayout.WEST,panel);
		_layout.putConstraint(SpringLayout.NORTH,_fontSelection,80,SpringLayout.NORTH,_mp4Choice);
		_layout.putConstraint(SpringLayout.WEST, _fontSelection, 15, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH,_fontSize,77,SpringLayout.NORTH,_mp4Choice);
		_layout.putConstraint(SpringLayout.WEST, _fontSize, 300, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH, _fontSizeSelection, 80, SpringLayout.NORTH, _mp4Choice);
		_layout.putConstraint(SpringLayout.WEST,_fontSizeSelection,187,SpringLayout.WEST,panel);
		
		
		_jbPreview.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				getTitleScreenshotSW tSW = new getTitleScreenshotSW();
				try{
					tSW.doInBackground();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			
		});


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
				img = ImageIO.read(new File("/previewScreenShot.jpg"));
				System.out.println("donedone");
				JFrame f = new JFrame("Showing Preview");
				f.add(img);
			} catch(IOException e){
				
			}
			_userInfo.setText("Displaying Preview");	
		}


		@Override
		protected Integer doInBackground() throws Exception {
			_selectedTitleText = _titleTextRaw.getText();
			System.out.println("doing");
			String file = _titleInputFile.getAbsolutePath();
			System.out.println(file);
			String[] cmd = {"avconv","-i",file,"-vframes","1","-an","-s","800x444","-ss","30","TitleScreenShot.jpg"};
			ProcessBuilder builder = new ProcessBuilder(cmd);
			Process process = builder.start();
			_previewPicture = new File("TitleScreenShot.jpg");
			String newFile = _previewPicture.getAbsolutePath();
			System.out.println(_drawText+_fontName+_drawText2+_selectedTitleText+"'\"");
			String [] cmd2 = {"avconv","-i",newFile,"-vf",_drawText+_fontName+_drawText2+_selectedTitleText+"'\"","previewScreenShot.jpg"};
			ProcessBuilder builder2 = new ProcessBuilder(cmd2);
			Process process2 = builder2.start();			
			return null;
			
		}


	}
	private void getImage(){
		preview = new ImageIcon();
	}
	
	private class titleTextOverlaySW extends SwingWorker<Integer,String>{
		protected void done(){
			_userInfo.setText("Title text overlay finished!");
		}
		
		@Override
		protected Integer doInBackground() throws Exception {
			_selectedTitleText = _titleTextRaw.getText();
			String[] cmd = {"avconv","-i",file,"-vframes","1","-an","-s","800x444","-ss","30","TitleScreenShot.jpg"};
			ProcessBuilder builder = new ProcessBuilder(cmd);
			Process process = builder.start();
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
		_layout.putConstraint(SpringLayout.NORTH, _titleText, 42, SpringLayout.NORTH, panel);

		//add the user input box for the title of the output file
		_titleName = new JTextField();
		_titleName.setPreferredSize(new Dimension(115,20));
		panel.add(_titleName);
		//move the mp3 output file name to its location on the screen
		_layout.putConstraint(SpringLayout.WEST, _titleName, 125, SpringLayout.WEST, _titleText);
		_layout.putConstraint(SpringLayout.NORTH, _titleName, 42, SpringLayout.NORTH, panel);


		_mp4Display = new JTextField();
		_mp4Display.setText(".mp4");
		_mp4Display.setEditable(false);
		_titleName.setPreferredSize(new Dimension(115,20));
		panel.add(_mp4Display);
		//move the choose file button to its location
		_layout.putConstraint(SpringLayout.WEST, _mp4Display, 115, SpringLayout.WEST, _titleName);
		_layout.putConstraint(SpringLayout.NORTH, _mp4Display, 42, SpringLayout.NORTH, panel);

		//add textbox for error messages to be conveyed to the user
		_userInfo = new JTextField();
		_userInfo.setPreferredSize(new Dimension(275,20));
		_userInfo.setEditable(false);

		//move the text box containing user file choice button to its location
		_layout.putConstraint(SpringLayout.WEST, _userInfo, 15, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH, _userInfo, 55, SpringLayout.NORTH, _jbChoose);
		panel.add(_userInfo);
		
		_titleTextInformer = new JTextField();
		_titleTextInformer.setText("Please specify text to add to video: ");
		_titleTextInformer.setEditable(false);
		_layout.putConstraint(SpringLayout.NORTH, _titleTextInformer, 120,SpringLayout.NORTH, panel);
		_layout.putConstraint(SpringLayout.WEST, _titleTextInformer, 15, SpringLayout.WEST, panel);
		panel.add(_titleTextInformer);
		
		_titleTextRaw = new JTextField();
		_titleTextRaw.setPreferredSize(new Dimension (300,20));
		panel.add(_titleTextRaw);
		_layout.putConstraint(SpringLayout.NORTH, _titleTextRaw, 120,SpringLayout.NORTH, panel);
		_layout.putConstraint(SpringLayout.WEST, _titleTextRaw, 240, SpringLayout.WEST, _jbChoose);

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

