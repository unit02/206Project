import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingWorker;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;


public class editVideo {
	private JButton jbTitlePage;
	private JButton jbCreditsPage;
	private JButton jbChoose;
	private JPanel panel;
	private JTextField text;
	SpringLayout layout = new SpringLayout();
	private JTextField userInfo;
	private JTextField fileChoice;

	
	private void addButtonToPane(JPanel panel, JButton button){
		panel.add(button);
		panel.validate();
	}
	
	
	public void insertTitlePageTab(final JTabbedPane pane){
		JPanel titlePagePanel = new JPanel();
		pane.addTab("Add Title", titlePagePanel);
		setTitlePageFeatures(titlePagePanel);
	}
	
	public void insertCreditPageTab(final JTabbedPane pane){
		JPanel creditPagePanel = new JPanel();
		pane.addTab("Add Credits", creditPagePanel);
		setCreditsPageFeatures(creditPagePanel);
	}
	
	private void addTextChoice(){
		text = new JTextField();
		text.setEditable(false);
		panel.add(text);
		text.setPreferredSize(new Dimension(400,30));
	}
	
	private void setTitlePageFeatures(final JPanel panel){
		panel.setLayout(layout);
		addVideoChooser(panel);
		
		jbTitlePage = new JButton("Set Title Page");
		panel.add(jbTitlePage);

		//move the text box containing user file choice button to its location
		layout.putConstraint(SpringLayout.WEST, jbTitlePage, 15, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, jbTitlePage, 70, SpringLayout.NORTH, fileChoice);

		jbTitlePage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String chosenFile = text.getText();
				startTitleEditting(chosenFile);
			}
		});
	}
	
	// Method is called after mp4 is chosen and "Set Title Page" button is pushed.
	private void startTitleEditting (String chosenFile){
		String[] cmd = {"avconv","-i","bbb.mp4","-vframes","1","-an","-s","800x444","-ss","30","TitleScreenShot.jpg"};
		// Calls processBuilder method
		processBuilder(cmd);
		
	}

	// This method handles processBuilder stuff
	private void processBuilder (String[] command){
		ProcessBuilder builder = new ProcessBuilder(command);
		try {
			Process process = builder.start();
			InputStream stdout = process.getInputStream();
			InputStream stderr = process.getErrorStream();
			BufferedReader stdoutBuffered = new BufferedReader(new InputStreamReader(stdout));
			process.getInputStream().close();
			process.getOutputStream().close();
			process.getErrorStream().close();
			process.destroy();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Called after the screenshot of mp4 is taken to show the image.
	private void showImage (String outputImage) {
		
	}
	
	private void setCreditsPageFeatures (final JPanel panel){
		panel.setLayout(layout);
		addVideoChooser(panel);
		
		jbCreditsPage = new JButton("Set Credits Page");
		panel.add(jbCreditsPage);

		//move the text box containing user file choice button to its location
		layout.putConstraint(SpringLayout.WEST, jbCreditsPage, 15, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, jbCreditsPage, 70, SpringLayout.NORTH, fileChoice);

		jbCreditsPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
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
					String chosenFile = jfile.getSelectedFile().toString();
					fileChoice.setText(chosenFile);
				}
				jfile.setVisible(true);
			}
		});
		panel.add(jbChoose);


		//move the choose file button to its location
		layout.putConstraint(SpringLayout.WEST, jbChoose, 15, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, jbChoose, 25, SpringLayout.NORTH, panel);

		//creates text field to store which file the user input
		fileChoice = new JTextField();
		fileChoice.setPreferredSize(new Dimension(150,20));
		fileChoice.setEditable(false);
		panel.add(fileChoice);

		//move the text box containing user file choice button to its location
		layout.putConstraint(SpringLayout.WEST, fileChoice, 125, SpringLayout.WEST, jbChoose);
		layout.putConstraint(SpringLayout.NORTH, fileChoice, 27, SpringLayout.NORTH, panel);

		//add textbox for error messages to be conveyed to the user
		userInfo = new JTextField();
		userInfo.setPreferredSize(new Dimension(150,20));
		userInfo.setEditable(false);
		//move the text box containing user file choice button to its location
		layout.putConstraint(SpringLayout.WEST, userInfo, 15, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, userInfo, 50, SpringLayout.NORTH, jbChoose);
		panel.add(userInfo);

	}
	
	
	private class editTitleSwingworker extends SwingWorker<Integer,String>{

		@Override
		protected Integer doInBackground() throws Exception {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
