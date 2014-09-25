import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingWorker;


public class Download extends JPanel{




	boolean _isCanceled = false;
	boolean _wgetFinished = false;
	private	JButton _jbCancel;
	private	JCheckBox _trueCheck ;
	private	JTextField _urlInput ;
	private	JTextField _jtextInput;

	private	JTextField _userInfo ;
	private JProgressBar _pb = new JProgressBar();
	int _prog = 0;

	private JButton _jbDownload;

	private SpringLayout _lay;


	private void addButtonToPane(JPanel panel, JButton button){
		panel.add(button);
		panel.validate();
	}



	private void addTextFields(final JPanel panel1){

		_jtextInput = new JTextField("Input URL :");
		_jtextInput.setEditable(false);
		panel1.add(_jtextInput);

		//set location of the text field labeling the user input
		_lay.putConstraint(SpringLayout.WEST, _jtextInput, 15, SpringLayout.WEST, panel1);
		_lay.putConstraint(SpringLayout.NORTH, _jtextInput, 25, SpringLayout.NORTH, panel1);


		_urlInput = new JTextField("",15);
		panel1.add(_urlInput);

		//set location of the user input field for the url
		_lay.putConstraint(SpringLayout.WEST, _urlInput, 7, SpringLayout.EAST, _jtextInput);
		_lay.putConstraint(SpringLayout.NORTH, _urlInput, 25, SpringLayout.NORTH, panel1);		

		_trueCheck = new JCheckBox("Open Source", false);
		panel1.add(_trueCheck);

		//set the location of the open source check box
		_lay.putConstraint(SpringLayout.WEST, _trueCheck, 15, SpringLayout.WEST, panel1);
		_lay.putConstraint(SpringLayout.NORTH, _trueCheck, 25, SpringLayout.NORTH, _jtextInput);

		_userInfo = new JTextField("",50);
		_userInfo.setEditable(false);
		panel1.add(_userInfo);
		//set the location of the open source check box
		_lay.putConstraint(SpringLayout.WEST, _userInfo, 15, SpringLayout.WEST, panel1);
		_lay.putConstraint(SpringLayout.NORTH, _userInfo, 25, SpringLayout.NORTH, _trueCheck);



	}


	private void addProgressBar(final JPanel panel1){
		//add progress bar and hide it
		panel1.add(_pb);
		_pb.setVisible(false);
		//set the location of the progress bar
		_lay.putConstraint(SpringLayout.WEST, _pb, 15, SpringLayout.WEST, panel1);
		_lay.putConstraint(SpringLayout.NORTH, _pb, 30, SpringLayout.NORTH, _jbDownload);

	}

	private void createDownloadButton(final JPanel panel1){
		_jbDownload = new JButton("Download");
		_jbDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if(!_trueCheck.isSelected()){
					_userInfo.setText("Please only download open source!");
					return;
				}
				String outputName = null;
				String input = _urlInput.getText();
				System.out.print(input);
				if(input.length() == 0){
					_userInfo.setText("Please enter a URL");
					return;
				}
				if (input.length() == 0){

				}
				else {
					//to get the filename from the url a process is used
					ProcessBuilder builder = new ProcessBuilder("basename", _urlInput.getText());
					Process process = null;

					try {
						process = builder.start();
					} catch (IOException e1) {

						e1.printStackTrace();
					}
					InputStream stdout = process.getInputStream();
					process.getErrorStream();
					BufferedReader stdoutBuffered = new BufferedReader(new InputStreamReader(stdout));
					try {
						outputName = stdoutBuffered.readLine();

					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

				String home = System.getProperty("user.home");
				File audioFile = new File(home + File.separator + outputName);

				String[] ocButtons = {"Overwrite", "Quit"};
				if(audioFile.exists()) {
					int select = JOptionPane.showOptionDialog(null, "File already exists" + System.lineSeparator() 
							+ "Do you wish to overwrite or quit?",
							"File Exists", JOptionPane.WARNING_MESSAGE, 0, null, ocButtons, ocButtons[0]);
					if(select == JOptionPane.YES_OPTION) {
						audioFile.delete();
						//set the size of the progress bar
						_pb.setPreferredSize(new Dimension(250,20));	
						//make the progress bar appear on screen
						_pb.setVisible(true);
						//make the cancel button appear
						_jbCancel.setVisible(true);
						//set isCancwlled to false
						_isCanceled = false;
						//else file does not exist and we can download
						DownloadSwingWorker sw = new DownloadSwingWorker();
						sw.execute();
					} else if (select == JOptionPane.NO_OPTION) {
						return;
					}
				}

				else {
					//set the size of the progress bar
					_pb.setPreferredSize(new Dimension(250,20));	
					//make the progress bar appear on screen
					_pb.setVisible(true);
					//make the cancel button appear
					_jbCancel.setVisible(true);
					//set isCancwlled to false
					_isCanceled = false;
					//else file does not exist and we can download
					DownloadSwingWorker sw = new DownloadSwingWorker();
					sw.execute();

				} // end else
			}
		});
		panel1.add(_jbDownload);
		//set the location of the download button
		_lay.putConstraint(SpringLayout.WEST, _jbDownload, 15, SpringLayout.WEST, panel1);
		_lay.putConstraint(SpringLayout.NORTH, _jbDownload, 25, SpringLayout.NORTH, _userInfo);

	}

	private void createCancelButton(final JPanel panel1){
		_jbCancel = new JButton("Cancel");
		_jbCancel.setVisible(false);
		//if cancel is pressed, then the download halts
		_jbCancel.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				_isCanceled = true;

			}


		});
		panel1.add(_jbCancel);
		//set the location of the download button
		_lay.putConstraint(SpringLayout.WEST, _jbCancel, 25, SpringLayout.WEST, panel1);
		_lay.putConstraint(SpringLayout.NORTH, _jbCancel, 25, SpringLayout.NORTH, _userInfo);

	}

	public void insertGUIFeatures(final JFrame frame,final JPanel panel1, final JTabbedPane pane2,final JTabbedPane pane3, final JTabbedPane pane){
		//add button to top
		JButton jbDownload = new JButton("Download");
		JPanel darkBluePanel = new JPanel();
		darkBluePanel.setOpaque(true);

		darkBluePanel.setBackground(new Color(66,107,122));
		darkBluePanel.setPreferredSize(new Dimension(250, 40));
		addButtonToPane(darkBluePanel,jbDownload);	
		frame.getContentPane().add(darkBluePanel, BorderLayout.EAST);

		pane.removeAll();
		panel1.removeAll();
		pane.setPreferredSize(new Dimension(200,200));
		pane.addTab("Download",panel1);
		_lay = new SpringLayout();
		panel1.setLayout(_lay);

		addTextFields(panel1);
		createDownloadButton(panel1);
		createCancelButton(panel1);
		addProgressBar(panel1);

		// ActionListener on the download button that checks whether or not the tab for
		// Download or any other tabs, such as editting and playback is open or not,
		// and adds and removes the tabs accordingly. 
		jbDownload.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {

				if (pane2.isVisible()){
					frame.getContentPane().remove(pane2);
					pane2.setVisible(false);
					frame.getContentPane().add(pane, BorderLayout.SOUTH);
					pane.setVisible(true);

				}
				else if (pane3.isVisible()){
					frame.getContentPane().remove(pane3);
					pane3.setVisible(false);
					frame.getContentPane().add(pane, BorderLayout.SOUTH);
					pane.setVisible(true);
				}

				else if (pane.isVisible() == false){
					pane.setVisible(true);
					frame.getContentPane().add(pane, BorderLayout.SOUTH);
				}
				else if (pane.isVisible()){
					pane.setVisible(false);
					frame.getContentPane().remove(pane);
				}		

				frame.pack();
				frame.repaint();
				frame.validate();

			}


		});
	}

	private class DownloadSwingWorker extends SwingWorker<Integer,String>{

		protected void done(){


			if (_isCanceled) {
				_jbDownload.setVisible(true);
				_userInfo.setText("Download was cancelled");
				_pb.setVisible(false);
				_pb.setBackground(null);
				_jbCancel.setVisible(false);
				return;
			}
			else if (_wgetFinished){
				_jbDownload.setVisible(true);
				_userInfo.setText("Download has completed!");
				_pb.setVisible(false);
				_jbCancel.setVisible(false);

			}else{
				_jbDownload.setVisible(true);
				_pb.setVisible(false);
				_jbCancel.setVisible(false);
			}



		}


		@Override
		protected Integer doInBackground() throws Exception {
			_jbCancel.setVisible(true);
			_jbDownload.setVisible(false);
			String URLname = _urlInput.getText();
			_wgetFinished = false;
			//String URLname = "http://ccmixter.org/content/_ghost/_ghost_-_Reverie_(small_theme).mp3";
			_userInfo.setText("Download in progress");
			_prog = 0;
			String homePath = System.getProperty("user.home");
			//creates the process for wget
			ProcessBuilder builder = new ProcessBuilder("wget","-P",homePath, "--progress=dot",URLname);
			Process process = builder.start();

			process.getInputStream();
			InputStream stderr = process.getErrorStream();
			BufferedReader stdoutBuffered = new BufferedReader(new InputStreamReader(stderr));
			String line = null;

			//sends the dot progress bar to the process function for processing
			while ((line = stdoutBuffered.readLine()) != null ) {	

				if (_isCanceled) {
					process.destroy();
				}
				publish(line);
			}


			if(process.waitFor() == 0) {
				_wgetFinished = true;
				return null;
			}else if(process.waitFor() == 1) {								
				_userInfo.setText( "An error has been encounted");
			} else if(process.waitFor() == 2) {
				_userInfo.setText("Parse Error");
			} else if(process.waitFor() == 3) {
				_userInfo.setText("File I/O Error");
			} else if(process.waitFor() == 4) {
				_userInfo.setText( "Network Failure");
			} else if(process.waitFor() == 5) {
				_userInfo.setText("SSL Verification Failure");
			} else if(process.waitFor() == 6) {
				_userInfo.setText("Username/password authentication failure");
			} else if(process.waitFor() == 7) {
				_userInfo.setText("Protocol Error");
			} else if(process.waitFor() == 8) {
				_userInfo.setText("Server Issued an error response");
			}
			return null;



		}

		@Override
		protected void process(List<String> chunks) {
			//updates progress bar for each dot 
			for(String chu:chunks){
				_prog++;
				_pb.setValue(_prog);
			}



		}

	}
}
