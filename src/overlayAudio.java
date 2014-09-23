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
	private JButton jbChoose;
	private JButton jbRemove;
	private JTextField userInfo;
	private JTextField fileChoice;
	private JTextField titleName;
	private JTextField mp3Display;
	private JTextField titleText;
	SpringLayout layout = new SpringLayout();
	private File inputFile;
	
	public void insertReplaceAudio(final JTabbedPane pane){
		//add tabs at the top of the tabbed pane
		JPanel replacePanel = new JPanel();
		replacePanel.setLayout(layout);
		pane.addTab( "Replace Audio", replacePanel);
		addFileChooser(replacePanel);
		addPanelFeatures(replacePanel);
		
	}

	
	private class OverlaySwingWorker extends SwingWorker<Integer,String>{

		protected void done(){
			userInfo.setText("Overlay of audio has been completed!");	
		}


		@Override
		protected Integer doInBackground() throws Exception {		
			String file = inputFile.getAbsolutePath();
			String outputName = titleName.getText() + ".mp3";

			//creates the process for avconv

			ProcessBuilder builder = new ProcessBuilder("avconv", "-i", file, "-acodec", "libmp3lame", outputName);

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
		layout.putConstraint(SpringLayout.NORTH, titleText, 55, SpringLayout.NORTH, panel);

		//add the user input box for the title of the output file
		titleName = new JTextField();
		titleName.setPreferredSize(new Dimension(115,20));
		panel.add(titleName);
		//move the mp3 output file name to its location on the screen
		layout.putConstraint(SpringLayout.WEST, titleName, 125, SpringLayout.WEST, titleText);
		layout.putConstraint(SpringLayout.NORTH, titleName, 55, SpringLayout.NORTH, panel);


		mp3Display = new JTextField();
		mp3Display.setText(".mp3");
		mp3Display.setEditable(false);
		titleName.setPreferredSize(new Dimension(115,20));
		panel.add(mp3Display);
		//move the choose file button to its location
		layout.putConstraint(SpringLayout.WEST, mp3Display, 115
				, SpringLayout.WEST, titleName);
		layout.putConstraint(SpringLayout.NORTH, mp3Display, 55, SpringLayout.NORTH, panel);

		//add textbox for error messages to be conveyed to the user
		userInfo = new JTextField();
		userInfo.setPreferredSize(new Dimension(275,20));
		userInfo.setEditable(false);
		//move the text box containing user file choice button to its location
		layout.putConstraint(SpringLayout.WEST, userInfo, 15, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, userInfo, 55, SpringLayout.NORTH, jbChoose);
		panel.add(userInfo);

	}

	private void addFileChooser(final JPanel panel){
		//create and add functionality for file choosing button
		jbChoose = new JButton("Choose File");
		JFileChooser jfile = null;
		jbChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Opens JFileChooser when button pressed
				JFileChooser jfile = new JFileChooser();
				int response = jfile.showOpenDialog(null);
				if (response == JFileChooser.APPROVE_OPTION) {
					inputFile = jfile.getSelectedFile();
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



	}

	public void insertOverlayAudio(final JTabbedPane pane){
		//add tabs at the top of the tabbed pane
		JPanel overlayPanel = new JPanel();
		overlayPanel.setLayout(layout);
		pane.addTab( "Overlay Audio", overlayPanel);
		addFileChooser(overlayPanel);
		jbChoose.setText("Choose Video File");
		layout.putConstraint(SpringLayout.WEST, fileChoice, 165, SpringLayout.WEST, jbChoose);
		
		addPanelFeatures(overlayPanel);
	}

}


