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




public class editAudio {
	private JButton _jbChoose;
	private JButton _jbRemove;
	private JTextField _userInfo;
	private JTextField _fileChoice;
	private JTextField _titleName;
	private JTextField _mp3Display;
	private JTextField _titleText;
	SpringLayout _layout = new SpringLayout();
	private File _inputFile;
	
	
	//method to add tab to the tabbed pane 
	public void insertRemoveAudio(final JTabbedPane pane){
		//add tabs at the top of the tabbed pane
		JPanel removePanel = new JPanel();
		pane.addTab( "Remove Audio", removePanel);
		setRemovePanelFeatures(removePanel);
	}

	private void setRemovePanelFeatures(final JPanel panel){	
		panel.setLayout(_layout);
		addFileChooser(panel);
		addPanelFeatures(panel);



		_jbRemove = new JButton("Strip audio off file");
		panel.add(_jbRemove);

		//move the text box containing user file choice button to its location
		_layout.putConstraint(SpringLayout.WEST, _jbRemove, 15, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH, _jbRemove, 80, SpringLayout.NORTH, _fileChoice);

		_jbRemove.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String outputName = _titleName.getText() + ".mp3";

				File f = new File(outputName);
				if(f.exists() && !f.isDirectory()) { 
					_userInfo.setText(outputName +" already exists!");
					return;
				} else if(_fileChoice.getText().equals("")){
					//System.out.print("hi");
					_userInfo.setText("Please choose an input file!");
					return;
				}
				else {
					//else file does not exist and we can download
					removeSwingWorker sw = new removeSwingWorker();
					sw.execute();
				} // end else

			}//end action performed
		}); // end add action listener
	}


	private class removeSwingWorker extends SwingWorker<Integer,String>{

		protected void done(){
			_userInfo.setText("Stripping of audio has been completed!");	
		}


		@Override
		protected Integer doInBackground() throws Exception {		
			String file = _inputFile.getAbsolutePath();
			String outputName = _titleName.getText() + ".mp3";

			//creates the process for avconv

			//ProcessBuilder builder = new ProcessBuilder("avconv", "-i", inputVideo, "-i" inputmusic, "-map", "0:0", "-map", "1:0", "-codec", "copy", "-shortest" outputfile);

			_userInfo.setText("Stripping of audio is in progress...");
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


		_mp3Display = new JTextField();
		_mp3Display.setText(".mp3");
		_mp3Display.setEditable(false);
		_titleName.setPreferredSize(new Dimension(115,20));
		panel.add(_mp3Display);
		//move the choose file button to its location
		_layout.putConstraint(SpringLayout.WEST, _mp3Display, 115
				, SpringLayout.WEST, _titleName);
		_layout.putConstraint(SpringLayout.NORTH, _mp3Display, 55, SpringLayout.NORTH, panel);

		//add textbox for error messages to be conveyed to the user
		_userInfo = new JTextField();
		_userInfo.setPreferredSize(new Dimension(275,20));
		_userInfo.setEditable(false);
		//move the text box containing user file choice button to its location
		_layout.putConstraint(SpringLayout.WEST, _userInfo, 15, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH, _userInfo, 55, SpringLayout.NORTH, _jbChoose);
		panel.add(_userInfo);

	}
	private void addFileChooser(final JPanel panel){
		//create and add functionality for file choosing button
		_jbChoose = new JButton("Choose File");
		JFileChooser jfile = null;
		_jbChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Opens JFileChooser when button pressed
				JFileChooser jfile = new JFileChooser();
				int response = jfile.showOpenDialog(null);
				if (response == JFileChooser.APPROVE_OPTION) {
					_inputFile = jfile.getSelectedFile();
					String chosenFile = jfile.getSelectedFile().toString();
					_fileChoice.setText(chosenFile);
				}
				jfile.setVisible(true);
			}
		});
		panel.add(_jbChoose);


		//move the choose file button to its location
		_layout.putConstraint(SpringLayout.WEST, _jbChoose, 15, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH, _jbChoose, 25, SpringLayout.NORTH, panel);

		//creates text field to store which file the user input
		_fileChoice = new JTextField();
		_fileChoice.setPreferredSize(new Dimension(150,20));
		_fileChoice.setEditable(false);
		panel.add(_fileChoice);

		//move the text box containing user file choice button to its location
		_layout.putConstraint(SpringLayout.WEST, _fileChoice, 125, SpringLayout.WEST, _jbChoose);
		_layout.putConstraint(SpringLayout.NORTH, _fileChoice, 27, SpringLayout.NORTH, panel);



	}
	public void insertReplaceAudio(final JTabbedPane pane){
		//add tabs at the top of the tabbed pane
		JPanel replacePanel = new JPanel();
		replacePanel.setLayout(_layout);
		pane.addTab( "Replace Audio", replacePanel);
		addFileChooser(replacePanel);
		addPanelFeatures(replacePanel);


	}


	private class ReplaceSwingWorker extends SwingWorker<Integer,String>{

		protected void done(){
			_userInfo.setText("Replacement of audio has been completed!");	
		}


		@Override
		protected Integer doInBackground() throws Exception {		
			String file = _inputFile.getAbsolutePath();
			String outputName = _titleName.getText() + ".mp3";

			//creates the process for avconv

			ProcessBuilder builder = new ProcessBuilder("avconv", "-i", file, "-acodec", "libmp3lame", outputName);

			_userInfo.setText("Replacement of audio is in progress...");
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

	public void insertOverlayAudio(final JTabbedPane pane){
		//add tabs at the top of the tabbed pane
		JPanel overlayPanel = new JPanel();
		overlayPanel.setLayout(_layout);
		pane.addTab( "Overlay Audio", overlayPanel);
		addFileChooser(overlayPanel);
		addPanelFeatures(overlayPanel);
	}

}


