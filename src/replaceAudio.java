import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;




public class replaceAudio {
	private JButton _jbChooseVid;
	private JButton _jbChooseAud;
	private JButton _jbReplace;

	private JTextField _userInfo;
	private JTextField _inputVideo;
	private JTextField _titleName;
	private JTextField _mp4Display;
	private JTextField _titleText;
	private JTextField _inputAudio;
	
	private File _inputVideoFile;
	private File _inputAudioFile;

	SpringLayout _layout = new SpringLayout();
	private	int _exitStatus;

	//method to add tab to the tabbed pane 
	public void insertReplaceAudio(final JTabbedPane pane){
		//add tabs at the top of the tabbed pane
		JPanel removePanel = new JPanel();
		pane.addTab( "Replace Audio", removePanel);
		setReplacePanelFeatures(removePanel);
	}

	private void setReplacePanelFeatures(final JPanel panel){	
		panel.setLayout(_layout);
		addVidFileChooser(panel,"Choose Video File");
		addAudFileChooser(panel, "Choose Audio File");
		addPanelFeatures(panel);

		_jbReplace = new JButton("Replace Audio");
		panel.add(_jbReplace);

		//move the text box containing user file choice button to its location
		_layout.putConstraint(SpringLayout.WEST, _jbReplace, 15, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH, _jbReplace, 25, SpringLayout.NORTH, _userInfo);

		_jbReplace.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String outputName = _titleName.getText() + ".mp3";
				//checks if the files exists and that all fields are filled in
				File f = new File(outputName);
				if(f.exists() && !f.isDirectory()) { 
					_userInfo.setText(outputName +" already exists!");
					return;
				} else if(_inputVideo.getText().equals("")){
					_userInfo.setText("Please choose an video file!");
					return;
				}else if(_inputAudio.getText().equals("")){
					_userInfo.setText("Please choose an audio file!");
					return;
				}else if(_titleName.getText().equals("")){
					_userInfo.setText("Please choose a title!");
					return;
				}
				else {
					//else file does not exist and we can download
					replaceSwingWorker sw = new replaceSwingWorker();
					sw.execute();
				} 

			}
		}); 
	}


	private class replaceSwingWorker extends SwingWorker<Integer,String>{

		protected void done(){
			//if the exit status is more than 0, it means an error has occured
			if(_exitStatus == 0){
				_userInfo.setText("Replacement of audio has been completed!");	
			}else if(_exitStatus >0){
				_userInfo.setText("An error in the replacement of the audio has occured.");	
			}
		}


		@Override
		protected Integer doInBackground() throws Exception {		
			String _inputVideo = _inputVideoFile.getAbsolutePath();
			String outputName = _titleName.getText() + ".mp4";
			String _inputAudio = _inputAudioFile.getAbsolutePath();

			//creates the process for avconv
			ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", "avconv -i \"" + _inputVideo + "\" -strict experimental -i \"" + _inputAudio + "\" -strict experimental -map 0:v -map 1:a " +"-c:v copy -c:a copy -y \"" + outputName + "\"");
			_userInfo.setText("Replacement of audio is in progress...");
			Process process = builder.start();
			//get the exit status of the process
			_exitStatus = process.waitFor();
			return null;
		}


	}

	private void addPanelFeatures(final JPanel panel){
		//add text box requesting user to input an output name
		_titleText = new JTextField();
		_titleText.setText("Output file name :");
		_titleText.setBorder(null);
		_titleText.setPreferredSize(new Dimension(115,20));
		_titleText.setEditable(false);
		panel.add(_titleText);
		//move the title text to its location on the screen
		_layout.putConstraint(SpringLayout.WEST, _titleText, 15, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH, _titleText, 30, SpringLayout.NORTH, _jbChooseAud);

		//add the user input box for the title of the output file
		_titleName = new JTextField();
		_titleName.setPreferredSize(new Dimension(266,20));
		panel.add(_titleName);

		//move the mp3 output file name to its location on the screen
		_layout.putConstraint(SpringLayout.WEST, _titleName, 7, SpringLayout.EAST, _titleText);
		_layout.putConstraint(SpringLayout.NORTH, _titleName, 30, SpringLayout.NORTH, _jbChooseAud);

		_mp4Display = new JTextField();
		_mp4Display.setText(".mp4");
		_mp4Display.setEditable(false);
		_mp4Display.setBorder(null);
		panel.add(_mp4Display);
		//move the choose file button to its location
		_layout.putConstraint(SpringLayout.WEST, _mp4Display, 2, SpringLayout.EAST, _titleName);
		_layout.putConstraint(SpringLayout.NORTH, _mp4Display, 30, SpringLayout.NORTH, _jbChooseAud);

		//add textbox for error messages to be conveyed to the user
		_userInfo = new JTextField();
		_userInfo.setPreferredSize(new Dimension(422,20));
		_userInfo.setBackground(new Color(245,245,245));
		_userInfo.setEditable(false);

		//move the text box containing user information 
		_layout.putConstraint(SpringLayout.WEST, _userInfo, 15, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH, _userInfo, 25, SpringLayout.NORTH, _titleText);
		panel.add(_userInfo);

	}

	private void addVidFileChooser(final JPanel panel,String text){
		//create and add functionality for file choosing button
		_jbChooseVid = new JButton(text);
		_jbChooseVid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Opens JFileChooser when button pressed
				JFileChooser jfile = new JFileChooser();
				// remove the accept all filter.
				jfile.setAcceptAllFileFilterUsed(false);
				// add mp4 as filter.
				jfile.addChoosableFileFilter(new FileNameExtensionFilter("MPEG-4", "mp4"));
				int response = jfile.showOpenDialog(null);
				if (response == JFileChooser.APPROVE_OPTION) {
					_inputVideoFile = jfile.getSelectedFile();
					String chosenFile = jfile.getSelectedFile().toString();
					_inputVideo.setText(chosenFile);
				}
				jfile.setVisible(true);
			}
		});
		panel.add(_jbChooseVid);


		//move the choose video file button to its location
		_layout.putConstraint(SpringLayout.WEST, _jbChooseVid, 15, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH, _jbChooseVid, 15, SpringLayout.NORTH, panel);

		//creates text field to store which file the user input
		_inputVideo = new JTextField();
		_inputVideo.setPreferredSize(new Dimension(248,20));
		_inputVideo.setBackground(new Color(245,245,245));
		_inputVideo.setEditable(false);
		panel.add(_inputVideo);

		//move the text box containing video file choice button to its location
		_layout.putConstraint(SpringLayout.WEST, _inputVideo, 15, SpringLayout.EAST, _jbChooseVid);
		_layout.putConstraint(SpringLayout.NORTH, _inputVideo, 3, SpringLayout.NORTH, _jbChooseVid);



	}

	private void addAudFileChooser(final JPanel panel,String text){
		//create and add functionality for file choosing button
		_jbChooseAud = new JButton(text);
		_jbChooseAud.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Opens JFileChooser when button pressed
				JFileChooser jfile = new JFileChooser();
				// remove the accept all filter
				jfile.setAcceptAllFileFilterUsed(false);
				// filters results so only audio files can be added
				jfile.setFileFilter(new FileNameExtensionFilter("mp3 & wav Images", "wav", "mp3"));
				//adds the user selection of file to the gui for revision by the user
				int response = jfile.showOpenDialog(null);
				if (response == JFileChooser.APPROVE_OPTION) {
					_inputAudioFile = jfile.getSelectedFile();
					String chosenFile = jfile.getSelectedFile().toString();
					_inputAudio.setText(chosenFile);
				}
				jfile.setVisible(true);
			}
		});
		panel.add(_jbChooseAud);


		//move the choose audio file button to its location
		_layout.putConstraint(SpringLayout.WEST, _jbChooseAud, 15, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH, _jbChooseAud, 30, SpringLayout.NORTH, _jbChooseVid);

		//creates text field to store which audio file the user has selected
		_inputAudio = new JTextField();
		_inputAudio.setPreferredSize(new Dimension(248,20));
		_inputAudio.setEditable(false);
		_inputAudio.setBackground(new Color(245,245,245));
		panel.add(_inputAudio);

		//move the text box containing audio file choice to its location
		_layout.putConstraint(SpringLayout.WEST, _inputAudio, 15, SpringLayout.EAST, _jbChooseAud);
		_layout.putConstraint(SpringLayout.NORTH, _inputAudio, 3, SpringLayout.NORTH, _jbChooseAud);

	}
}
