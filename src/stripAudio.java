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

	private JButton jbChoose;
	private JButton jbRemove;
	private JTextField userInfo;
	private JTextField fileChoice;
	private JTextField titleName;
	private JTextField mp3Display;
	private JTextField titleText;
	private JTextField jtextStart;
	private JTextField jtextEnd;

	private JCheckBox trueCheck;

	private JFormattedTextField startTime;
	private JFormattedTextField timeInterval;

	private File inputFile;
	private int exitStatus;
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
		jbRemove = new JButton("Extract audio");
		panel.add(jbRemove);

		//move the button to its location
		layout.putConstraint(SpringLayout.WEST, jbRemove, 15, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, jbRemove, 25, SpringLayout.NORTH, userInfo);

		//add functionality to the button
		jbRemove.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String outputName = titleName.getText() + ".mp3";

				File f = new File(outputName);
				if(f.exists() && !f.isDirectory()) { 
					userInfo.setText(outputName +" already exists!");
					return;
				} else if(fileChoice.getText().equals("")){
					userInfo.setText("Please choose an input file!");
					return;
				} else if(!trueCheck.isSelected()){
					if(timeInterval.getText().equals("00:00:00")){
						userInfo.setText("Please choose a a time interval!");
						return;
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
		titleText = new JTextField();
		titleText.setText("Output file name :");
		titleText.setFont(new Font("Verdana", Font.BOLD, 13));
		titleText.setPreferredSize(new Dimension(145,20));
		titleText.setEditable(false);
		panel.add(titleText);
		//move the title text to its location on the screen
		layout.putConstraint(SpringLayout.WEST, titleText, 15, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, titleText, 55, SpringLayout.NORTH, panel);

		addTimeInputs(panel);

		//add the user input box for the title of the output file
		titleName = new JTextField();
		titleName.setPreferredSize(new Dimension(180,20));
		panel.add(titleName);
		//move the mp3 output file name to its location on the screen
		layout.putConstraint(SpringLayout.WEST, titleName, 2, SpringLayout.EAST, titleText);
		layout.putConstraint(SpringLayout.NORTH, titleName, 55, SpringLayout.NORTH, panel);


		mp3Display = new JTextField();
		mp3Display.setText(".mp3");
		mp3Display.setEditable(false);
		titleName.setPreferredSize(new Dimension(115,20));
		panel.add(mp3Display);
		//move the choose file button to its location
		layout.putConstraint(SpringLayout.WEST, mp3Display, 2, SpringLayout.EAST, titleName);
		layout.putConstraint(SpringLayout.NORTH, mp3Display, 55, SpringLayout.NORTH, panel);

		//add textbox for error messages to be conveyed to the user
		userInfo = new JTextField();
		userInfo.setPreferredSize(new Dimension(320,20));
		userInfo.setEditable(false);
		//move the text box containing user file choice button to its location
		layout.putConstraint(SpringLayout.WEST, userInfo, 15, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, userInfo, 25, SpringLayout.NORTH, jtextStart);
		panel.add(userInfo);

	}

	private void addTimeInputs(final JPanel panel){

		jtextStart = new JTextField("Start Time :");
		jtextStart.setEditable(false);
		jtextStart.setFont(new Font("Verdana", Font.BOLD, 13));
		panel.add(jtextStart);

		//set location of the text field labeling start time
		layout.putConstraint(SpringLayout.WEST, jtextStart, 15, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, jtextStart, 25, SpringLayout.NORTH, titleText);

		//creates place for the user to input start time
		startTime = new JFormattedTextField(createFormatText());
		startTime.setText("000000");
		startTime.setPreferredSize(new Dimension(62,20));
		panel.add(startTime);

		startTime.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent fEvt) {
				JTextField tField = (JTextField)fEvt.getSource();
				tField.selectAll();
			}
		});

		//set location of the user input field for the start time
		layout.putConstraint(SpringLayout.WEST, startTime, 7, SpringLayout.EAST, jtextStart);
		layout.putConstraint(SpringLayout.NORTH, startTime, 25, SpringLayout.NORTH, titleText);		

		jtextEnd = new JTextField("End Time :");
		jtextEnd.setEditable(false);
		jtextEnd.setFont(new Font("Verdana", Font.BOLD, 13));
		jtextEnd.setBorder(null);
		panel.add(jtextEnd);

		//set location of the text field labeling end time
		layout.putConstraint(SpringLayout.WEST, jtextEnd, 15, SpringLayout.EAST, startTime);
		layout.putConstraint(SpringLayout.NORTH, jtextEnd, 25, SpringLayout.NORTH, titleText);

		timeInterval = new JFormattedTextField(createFormatText());
		timeInterval.setText("000000");		
		timeInterval.setPreferredSize(new Dimension(62,20));
		panel.add(timeInterval);

		timeInterval.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent fEvt) {
				JTextField tField = (JTextField)fEvt.getSource();
				tField.selectAll();
			}
		});

		//set location of the user input field for the end time
		layout.putConstraint(SpringLayout.WEST, timeInterval,7, SpringLayout.EAST, jtextEnd);
		layout.putConstraint(SpringLayout.NORTH, timeInterval, 25, SpringLayout.NORTH, titleText);		


		trueCheck = new JCheckBox("Entire File", false);
		panel.add(trueCheck);
		//set location of the user input field for the check box
		layout.putConstraint(SpringLayout.WEST, trueCheck,10, SpringLayout.EAST, timeInterval);
		layout.putConstraint(SpringLayout.NORTH, trueCheck, 23, SpringLayout.NORTH, titleText);		

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
		jbChoose = new JButton("Choose File");
		jbChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Opens JFileChooser when button pressed
				JFileChooser jfile = new JFileChooser();
				// remove the accept all filter.
				jfile.setAcceptAllFileFilterUsed(false);
				// add mp4 as filter.
				jfile.addChoosableFileFilter(new FileNameExtensionFilter("MPEG-4", "mp4"));
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
		fileChoice.setPreferredSize(new Dimension(195,20));
		fileChoice.setEditable(false);
		panel.add(fileChoice);

		//move the text box containing user file choice button to its location
		layout.putConstraint(SpringLayout.WEST, fileChoice, 125, SpringLayout.WEST, jbChoose);
		layout.putConstraint(SpringLayout.NORTH, fileChoice, 27, SpringLayout.NORTH, panel);
	}

	//swingworker to extract the audio in the background to gui remains responsive
	private class removeSwingWorker extends SwingWorker<Integer,String>{

		protected void done(){

			//checks that the process was executed correctly
			if(exitStatus == 0){
				userInfo.setText("Stripping of audio has been completed!");
			} else if(exitStatus > 0){
				userInfo.setText("Stripping of audio has been failed.");	
			}
		}

		//system hello
		@Override
		protected Integer doInBackground() throws Exception {		
			String file = inputFile.getAbsolutePath();
			String outputName = titleName.getText() + ".mp3";
			String beginTime = startTime.getText();
			String length = timeInterval.getText();
			ProcessBuilder builder;
			//creates the process for avconv
			if (trueCheck.isSelected()){
				builder = new ProcessBuilder("avconv", "-i", file, "-acodec", "libmp3lame", outputName);	
			}else {
				builder = new ProcessBuilder("avconv","-i",file,"-ss",beginTime,"-t",length,"-ac","2","-vn","-y",outputName);
			}
			
			userInfo.setText("Stripping of audio is in progress...");
			Process process = builder.start();
			exitStatus = process.waitFor();
			return null;
		}


	}


}
