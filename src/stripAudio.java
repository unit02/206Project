import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.MaskFormatter;


public class stripAudio {

	private JButton _jbChoose;
	private JButton _jbRemove;
	private JTextField _userInfo;
	private JTextField _fileChoice;
	private JTextField _titleName;
	private JTextField _mp3Display;
	private JTextField _titleText;
	private JTextField _jtextStart;
	private JTextField _jtextEnd;

	private JCheckBox _trueCheck;

	private JFormattedTextField _startTime;
	private JFormattedTextField _timeInterval;


	private File _inputFile;
	private int _exitStatus;
	SpringLayout layout = new SpringLayout();

	//method to add tab to the tabbed pane 
	public void insertRemoveAudio(final JTabbedPane pane){
		//add tabs at the top of the tabbed pane
		JPanel removePanel = new JPanel();
		pane.addTab( "Extract Audio", removePanel);
		setRemovePanelFeatures(removePanel);
	}

	//method to add features to the panel in the jtabbedpane
	private void setRemovePanelFeatures(final JPanel panel){	
		panel.setLayout(layout);
		addFileChooser(panel);
		addPanelFeatures(panel);


		//creates the remove audio button
		_jbRemove = new JButton("Extract audio");
		panel.add(_jbRemove);

		//move the button to its location
		layout.putConstraint(SpringLayout.WEST, _jbRemove, 15, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, _jbRemove, 25, SpringLayout.NORTH, _userInfo);

		//add functionality to the button
		_jbRemove.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String outputName = _titleName.getText() + ".mp3";

				File f = new File(outputName);
				if(f.exists() && !f.isDirectory()) { 
					_userInfo.setText(outputName +" already exists!");
					return;
				} else if(_fileChoice.getText().equals("")){
					_userInfo.setText("Please choose an input file!");
					return;
				} 
				else if(!_trueCheck.isSelected()){
					if(_timeInterval.getText().equals("00:00:00")){
						_userInfo.setText("Please choose a a time interval!");
						return;
					} else{
						removeSwingWorker sw = new removeSwingWorker();
						sw.execute();
					}
				}

				else {

					//else file does not exist and we can download
					removeSwingWorker sw = new removeSwingWorker();
					sw.execute();
				} 
			}
		}); 
	}


	private void addPanelFeatures(final JPanel panel){
		//add text box requesting user to input an output name
		_titleText = new JTextField();
		_titleText.setText("Output file name :");
		_titleText.setBorder(null);
		_titleText.setFont(new Font("Verdana", Font.BOLD, 13));
		_titleText.setPreferredSize(new Dimension(145,20));
		_titleText.setEditable(false);
		panel.add(_titleText);
		//move the title text to its location on the screen
		layout.putConstraint(SpringLayout.WEST, _titleText, 15, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, _titleText, 55, SpringLayout.NORTH, panel);

		addTimeInputs(panel);

		//add the user input box for the title of the output file
		_titleName = new JTextField();
		_titleName.setPreferredSize(new Dimension(238,20));
		panel.add(_titleName);
		//move the mp3 output file name to its location on the screen
		layout.putConstraint(SpringLayout.WEST, _titleName, 2, SpringLayout.EAST, _titleText);
		layout.putConstraint(SpringLayout.NORTH, _titleName, 55, SpringLayout.NORTH, panel);


		_mp3Display = new JTextField();
		_mp3Display.setText(".mp3");
		_mp3Display.setEditable(false);
		_mp3Display.setBorder(null);
		panel.add(_mp3Display);
		//move the choose file button to its location
		layout.putConstraint(SpringLayout.WEST, _mp3Display, 2, SpringLayout.EAST, _titleName);
		layout.putConstraint(SpringLayout.NORTH, _mp3Display, 57, SpringLayout.NORTH, panel);

		//add textbox for error messages to be conveyed to the user
		_userInfo = new JTextField();
		_userInfo.setPreferredSize(new Dimension(422,20));
		_userInfo.setBackground(new Color(245,245,245));
		_userInfo.setEditable(false);
		//move the text box containing user file choice button to its location
		layout.putConstraint(SpringLayout.WEST, _userInfo, 15, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, _userInfo, 25, SpringLayout.NORTH, _jtextStart);
		panel.add(_userInfo);

	}

	private void addTimeInputs(final JPanel panel){

		_jtextStart = new JTextField("Start Time :");
		_jtextStart.setEditable(false);
		_jtextStart.setFont(new Font("Verdana", Font.BOLD, 13));
		_jtextStart.setBorder(null);
		panel.add(_jtextStart);

		//set location of the text field labeling start time
		layout.putConstraint(SpringLayout.WEST, _jtextStart, 15, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, _jtextStart, 25, SpringLayout.NORTH, _titleText);

		//creates place for the user to input start time
		_startTime = new JFormattedTextField(createFormatText());
		_startTime.setText("000000");
		_startTime.setPreferredSize(new Dimension(62,20));
		panel.add(_startTime);

		_startTime.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent fEvt) {
				JTextField tField = (JTextField)fEvt.getSource();
				tField.selectAll();
			}
		});

		//set location of the user input field for the start time
		layout.putConstraint(SpringLayout.WEST, _startTime, 7, SpringLayout.EAST, _jtextStart);
		layout.putConstraint(SpringLayout.NORTH, _startTime, 25, SpringLayout.NORTH, _titleText);		

		_jtextEnd = new JTextField("End Time :");
		_jtextEnd.setEditable(false);
		_jtextEnd.setFont(new Font("Verdana", Font.BOLD, 13));
		_jtextEnd.setBorder(null);
		panel.add(_jtextEnd);

		//set location of the text field labeling end time
		layout.putConstraint(SpringLayout.WEST, _jtextEnd, 15, SpringLayout.EAST, _startTime);
		layout.putConstraint(SpringLayout.NORTH, _jtextEnd, 25, SpringLayout.NORTH, _titleText);

		_timeInterval = new JFormattedTextField(createFormatText());
		_timeInterval.setText("000000");		
		_timeInterval.setPreferredSize(new Dimension(62,20));
		panel.add(_timeInterval);

		_timeInterval.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent fEvt) {
				JTextField tField = (JTextField)fEvt.getSource();
				tField.selectAll();
			}
		});

		//set location of the user input field for the end time
		layout.putConstraint(SpringLayout.WEST, _timeInterval,7, SpringLayout.EAST, _jtextEnd);
		layout.putConstraint(SpringLayout.NORTH, _timeInterval, 25, SpringLayout.NORTH, _titleText);		


		_trueCheck = new JCheckBox("Entire File");
		panel.add(_trueCheck);
		//set location of the user input field for the check box
		layout.putConstraint(SpringLayout.WEST, _trueCheck,10, SpringLayout.EAST, _timeInterval);
		layout.putConstraint(SpringLayout.NORTH, _trueCheck, 23, SpringLayout.NORTH, _titleText);		

	}

	private MaskFormatter createFormatText(){
		MaskFormatter mf1 = null;
		try {
			mf1 = new MaskFormatter("##:##:##");
		} catch (ParseException e1) {

			e1.printStackTrace();
		}
		return mf1;
	}


	private void addFileChooser(final JPanel panel){
		//create and add functionality for file choosing button
		_jbChoose = new JButton("Choose File");
		_jbChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Opens JFileChooser when button pressed
				JFileChooser jfile = new JFileChooser();
				// remove the accept all filter.
				jfile.setAcceptAllFileFilterUsed(false);
				// add mp4 as filter.
				jfile.addChoosableFileFilter(new FileNameExtensionFilter("MPEG-4", "mp4"));
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
		layout.putConstraint(SpringLayout.WEST, _jbChoose, 15, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, _jbChoose, 25, SpringLayout.NORTH, panel);

		//creates text field to store which file the user input
		_fileChoice = new JTextField();
		_fileChoice.setBackground(new Color(245,245,245));
		
		_fileChoice.setPreferredSize(new Dimension(302,20));
		_fileChoice.setEditable(false);
		panel.add(_fileChoice);

		//move the text box containing user file choice button to its location
		layout.putConstraint(SpringLayout.WEST, _fileChoice, 3, SpringLayout.EAST, _jbChoose);
		layout.putConstraint(SpringLayout.NORTH, _fileChoice, 27, SpringLayout.NORTH, panel);
	}

	//swingworker to extract the audio in the background to gui remains responsive
	private class removeSwingWorker extends SwingWorker<Integer,String>{

		protected void done(){
			//checks that the process was executed correctly
			if(_exitStatus == 0){
				_userInfo.setText("Stripping of audio has been completed!");
			} else if(_exitStatus > 0){
				_userInfo.setText("Stripping of audio has failed.");	
			}
		}

	
		@Override
		protected Integer doInBackground() throws Exception {		
			String file = _inputFile.getAbsolutePath();
			String outputName = _titleName.getText() + ".mp3";
			String beginTime = _startTime.getText();
			String length = _timeInterval.getText();
			ProcessBuilder builder ;

			//creates the process for avconv
			if (_trueCheck.isSelected()){

				builder = new ProcessBuilder("avconv", "-i", file, "-acodec", "libmp3lame", outputName);	
			} else {
				builder	= new ProcessBuilder("avconv","-i",file,"-ss",beginTime,"-t",length,"-ac","2","-vn","-y",outputName);

			}


			_userInfo.setText("Stripping of audio is in progress...");
			Process process = builder.start();
			_exitStatus = process.waitFor();
			return null;
		}


	}


}
