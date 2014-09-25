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
import javax.swing.filechooser.FileNameExtensionFilter;




public class replaceAudio {
	private JButton jbChooseVid;
	private JButton jbChooseAud;
	private JButton jbReplace;

	private JTextField userInfo;
	private JTextField inputVideo;
	private JTextField titleName;
	private JTextField mp4Display;
	private JTextField titleText;
	private JTextField inputAudio;
	SpringLayout layout = new SpringLayout();
	private File inputVideoFile;
	private File inputAudioFile;

	//method to add tab to the tabbed pane 
	public void insertReplaceAudio(final JTabbedPane pane){
		//add tabs at the top of the tabbed pane
		JPanel removePanel = new JPanel();
		pane.addTab( "Replace Audio", removePanel);
		setReplacePanelFeatures(removePanel);
	}

	private void setReplacePanelFeatures(final JPanel panel){	
		panel.setLayout(layout);
		addVidFileChooser(panel,"Choose Video File");
		addAudFileChooser(panel, "Choose Audio File");
		addPanelFeatures(panel);



		jbReplace = new JButton("Replace audio of file");
		panel.add(jbReplace);

		//move the text box containing user file choice button to its location
		layout.putConstraint(SpringLayout.WEST, jbReplace, 15, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, jbReplace, 25, SpringLayout.NORTH, userInfo);

		jbReplace.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String outputName = titleName.getText() + ".mp3";

				File f = new File(outputName);
				if(f.exists() && !f.isDirectory()) { 
					userInfo.setText(outputName +" already exists!");
					return;
				} else if(inputVideo.getText().equals("")){
					userInfo.setText("Please choose an video file!");
					return;
				}else if(inputAudio.getText().equals("")){
					userInfo.setText("Please choose an audio file!");
					return;
				}else if(titleName.getText().equals("")){
					userInfo.setText("Please choose a title!");
					return;
				}
				else {
					//else file does not exist and we can download
					replaceSwingWorker sw = new replaceSwingWorker();
					sw.execute();
				} // end else

			}//end action performed
		}); // end add action listener
	}


	private class replaceSwingWorker extends SwingWorker<Integer,String>{

		protected void done(){
			userInfo.setText("Replacement of audio has been completed!");	
		}


		@Override
		protected Integer doInBackground() throws Exception {		
			String inputVideo = inputVideoFile.getAbsolutePath();
			String outputName = titleName.getText() + ".mp4";
			String inputAudio = inputAudioFile.getAbsolutePath();
			
			

			//creates the process for avconv
			ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", "avconv -i \"" + inputVideo + "\" -i \"" + inputAudio + "\" -map 0:v -map 1:a " +"-c:v copy -c:a copy -y \"" + outputName + "\"");
			userInfo.setText("Replacement of audio is in progress...");
			Process process = builder.start();

			//redirects input and error streams
			InputStream stdout = process.getInputStream();
			InputStream stderr = process.getErrorStream();
		
			process.waitFor();
			
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
		titleName.setPreferredSize(new Dimension(270,20));
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
		layout.putConstraint(SpringLayout.WEST, mp4Display, 2, SpringLayout.EAST, titleName);
		layout.putConstraint(SpringLayout.NORTH, mp4Display, 30, SpringLayout.NORTH, jbChooseAud);

		//add textbox for error messages to be conveyed to the user
		userInfo = new JTextField();
		userInfo.setPreferredSize(new Dimension(325,20));
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
				
				// remove the accept all filter.
				jfile.setAcceptAllFileFilterUsed(false);
				// add mp4 as filter.
				jfile.addChoosableFileFilter(new FileNameExtensionFilter("MPEG-4", "mp4"));
				
				int response = jfile.showOpenDialog(null);
				if (response == JFileChooser.APPROVE_OPTION) {
					inputVideoFile = jfile.getSelectedFile();
					String chosenFile = jfile.getSelectedFile().toString();
					inputVideo.setText(chosenFile);
				}
				jfile.setVisible(true);
			}
		});
		panel.add(jbChooseVid);


		//move the choose file button to its location
		layout.putConstraint(SpringLayout.WEST, jbChooseVid, 15, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, jbChooseVid, 15, SpringLayout.NORTH, panel);

		//creates text field to store which file the user input
		inputVideo = new JTextField();
		inputVideo.setPreferredSize(new Dimension(150,20));
		inputVideo.setEditable(false);
		panel.add(inputVideo);

		//move the text box containing user file choice button to its location
		layout.putConstraint(SpringLayout.WEST, inputVideo, 15, SpringLayout.EAST, jbChooseVid);
		layout.putConstraint(SpringLayout.NORTH, inputVideo, 3, SpringLayout.NORTH, jbChooseVid);



	}

	private void addAudFileChooser(final JPanel panel,String text){
		//create and add functionality for file choosing button
		jbChooseAud = new JButton(text);
		JFileChooser jfile = null;
		jbChooseAud.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Opens JFileChooser when button pressed
				JFileChooser jfile = new JFileChooser();
				
				// remove the accept all filter
				jfile.setAcceptAllFileFilterUsed(false);
				// filters results so only audio files can be added
				jfile.setFileFilter(new FileNameExtensionFilter("mp3 & wav Images", "wav", "mp3"));
			      
				
				int response = jfile.showOpenDialog(null);
				if (response == JFileChooser.APPROVE_OPTION) {
					inputAudioFile = jfile.getSelectedFile();
					String chosenFile = jfile.getSelectedFile().toString();
					inputAudio.setText(chosenFile);
				}
				jfile.setVisible(true);
			}
		});
		panel.add(jbChooseAud);


		//move the choose file button to its location
		layout.putConstraint(SpringLayout.WEST, jbChooseAud, 15, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, jbChooseAud, 30, SpringLayout.NORTH, jbChooseVid);

		//creates text field to store which file the user input for the
		inputAudio = new JTextField();
		inputAudio.setPreferredSize(new Dimension(150,20));
		inputAudio.setEditable(false);
		panel.add(inputAudio);

		//move the text box containing user file choice button to its location
		layout.putConstraint(SpringLayout.WEST, inputAudio, 15, SpringLayout.EAST, jbChooseAud);
		layout.putConstraint(SpringLayout.NORTH, inputAudio, 3, SpringLayout.NORTH, jbChooseAud);

	}
}
