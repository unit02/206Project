import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingWorker;





public class overlayAudio {
	private JButton jbChooseVid;
	private JButton jbChooseAud;
	private JButton jbOverlay;

	private JTextField userInfo;
	private JTextField inputVidFile;
	private JTextField titleName;
	private JTextField mp4Display;
	private JTextField titleText;
	private JTextField inputAudioFile;
	SpringLayout layout = new SpringLayout();
	private File inputFile;

	//method to add tab to the tabbed pane 
	public void insertOverlayAudio(final JTabbedPane pane){
		//add tabs at the top of the tabbed pane
		JPanel overlayPanel = new JPanel();
		pane.addTab( "Overlay Audio", overlayPanel);
		setOverlayPanelFeatures(overlayPanel);
	}

	private void setOverlayPanelFeatures(final JPanel panel){	
		panel.setLayout(layout);
		addVidFileChooser(panel,"Choose Video File");
		addAudFileChooser(panel, "Choose Audio File");
		addPanelFeatures(panel);



		jbOverlay = new JButton("Replace audio of file");
		panel.add(jbOverlay);

		//move the text box containing user file choice button to its location
		layout.putConstraint(SpringLayout.WEST, jbOverlay, 15, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, jbOverlay, 25, SpringLayout.NORTH, userInfo);

		jbOverlay.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String outputName = titleName.getText() + ".mp3";

				File f = new File(outputName);
				if(f.exists() && !f.isDirectory()) { 
					userInfo.setText(outputName +" already exists!");
					return;
				} else if(inputVidFile.getText().equals("")){

					userInfo.setText("Please choose an input file!");
					return;
				}
				else {
					//else file does not exist and we can download
					overlaySwingWorker sw = new overlaySwingWorker();
					sw.execute();
				} // end else

			}//end action performed
		}); // end add action listener
	}


	private class overlaySwingWorker extends SwingWorker<Integer,String>{

		protected void done(){
			userInfo.setText("Stripping of audio has been completed!");	
		}


		@Override
		protected Integer doInBackground() throws Exception {		
			String videoInput = inputFile.getAbsolutePath();
			String videoOutput = titleName.getText() + ".mp3";

			//creates the process for avconv
			ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", "avconv -i \"" + videoInput + "\" -i \"" + audioInput + "\" -map 0:v -map 1:a " +"-c:v copy -c:a copy -y \"" + videoOutput + "\"");

			userInfo.setText("Replacement of audio is in progress...");
			Process process = builder.start();

			//redirects input and error streams
			InputStream stdout = process.getInputStream();
			InputStream stderr = process.getErrorStream();
			BufferedReader stdoutBuffered = new BufferedReader(new InputStreamReader(stdout));
			String line = null;

			//sends the dot progress bar to the process function for processing
			while ((line = stdoutBuffered.readLine()) != null ) {	
				publish(line);
			}
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
		layout.putConstraint(SpringLayout.NORTH, titleText, 30, SpringLayout.NORTH, jbChooseAud);

		//add the user input box for the title of the output file
		titleName = new JTextField();
		titleName.setPreferredSize(new Dimension(115,20));
		panel.add(titleName);
		//move the mp3 output file name to its location on the screen
		layout.putConstraint(SpringLayout.WEST, titleName, 7, SpringLayout.EAST, titleText);
		layout.putConstraint(SpringLayout.NORTH, titleName, 30, SpringLayout.NORTH, jbChooseAud);


		mp4Display = new JTextField();
		mp4Display.setText(".mp4");
		mp4Display.setEditable(false);
		titleName.setPreferredSize(new Dimension(115,20));
		panel.add(mp4Display);
		//move the choose file button to its location
		layout.putConstraint(SpringLayout.WEST, mp4Display, 115, SpringLayout.WEST, titleName);
		layout.putConstraint(SpringLayout.NORTH, mp4Display, 30, SpringLayout.NORTH, jbChooseAud);

		//add textbox for error messages to be conveyed to the user
		userInfo = new JTextField();
		userInfo.setPreferredSize(new Dimension(275,20));
		userInfo.setEditable(false);

		//move the text box containing user information 
		layout.putConstraint(SpringLayout.WEST, userInfo, 15, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, userInfo, 25, SpringLayout.NORTH, titleText);
		panel.add(userInfo);

	}

	private void addVidFileChooser(final JPanel panel,String text){
		//create and add functionality for file choosing button
		jbChooseVid = new JButton(text);
		JFileChooser jfile = null;
		jbChooseVid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Opens JFileChooser when button pressed
				JFileChooser jfile = new JFileChooser();
				int response = jfile.showOpenDialog(null);
				if (response == JFileChooser.APPROVE_OPTION) {
					inputFile = jfile.getSelectedFile();
					String chosenFile = jfile.getSelectedFile().toString();
					inputVidFile.setText(chosenFile);
				}
				jfile.setVisible(true);
			}
		});
		panel.add(jbChooseVid);


		//move the choose file button to its location
		layout.putConstraint(SpringLayout.WEST, jbChooseVid, 15, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, jbChooseVid, 15, SpringLayout.NORTH, panel);

		//creates text field to store which file the user input
		inputVidFile = new JTextField();
		inputVidFile.setPreferredSize(new Dimension(150,20));
		inputVidFile.setEditable(false);
		panel.add(inputVidFile);

		//move the text box containing user file choice button to its location
		layout.putConstraint(SpringLayout.WEST, inputVidFile, 15, SpringLayout.EAST, jbChooseVid);
		layout.putConstraint(SpringLayout.NORTH, inputVidFile, 3, SpringLayout.NORTH, jbChooseVid);



	}

	private void addAudFileChooser(final JPanel panel,String text){
		//create and add functionality for file choosing button
		jbChooseAud = new JButton(text);
		JFileChooser jfile = null;
		jbChooseAud.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Opens JFileChooser when button pressed
				JFileChooser jfile = new JFileChooser();
				int response = jfile.showOpenDialog(null);
				if (response == JFileChooser.APPROVE_OPTION) {
					inputFile = jfile.getSelectedFile();
					String chosenFile = jfile.getSelectedFile().toString();
					inputAudioFile.setText(chosenFile);
				}
				jfile.setVisible(true);
			}
		});
		panel.add(jbChooseAud);


		//move the choose file button to its location
		layout.putConstraint(SpringLayout.WEST, jbChooseAud, 15, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, jbChooseAud, 30, SpringLayout.NORTH, jbChooseVid);

		//creates text field to store which file the user input for the
		inputAudioFile = new JTextField();
		inputAudioFile.setPreferredSize(new Dimension(150,20));
		inputAudioFile.setEditable(false);
		panel.add(inputAudioFile);

		//move the text box containing user file choice button to its location
		layout.putConstraint(SpringLayout.WEST, inputAudioFile, 15, SpringLayout.EAST, jbChooseAud);
		layout.putConstraint(SpringLayout.NORTH, inputAudioFile, 3, SpringLayout.NORTH, jbChooseAud);

	}

}


