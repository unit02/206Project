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

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingWorker;


public class Download extends JPanel{
	private Process process;
	boolean isCanceled = false;
	boolean wgetFinished = false;
	private	JButton jbCancel;
	private	JCheckBox trueCheck ;
	private	JTextField urlInput ;
	private	JTextField jtextInput;

	private	JTextField userInfo ;
	private JProgressBar pb = new JProgressBar();
	int prog = 0;
	private JPanel panel;
	private JButton jbDownload;

	private SpringLayout lay;

	private void addButtonToPane(JPanel panel, JButton button){
		panel.add(button);
		panel.validate();
	}



	private void addTextFields(final JPanel panel1){

		jtextInput = new JTextField("Input URL :");
		jtextInput.setEditable(false);
		panel1.add(jtextInput);

		//set location of the text field labeling the user input
		lay.putConstraint(SpringLayout.WEST, jtextInput, 15, SpringLayout.WEST, panel1);
		lay.putConstraint(SpringLayout.NORTH, jtextInput, 25, SpringLayout.NORTH, panel1);


		urlInput = new JTextField("",15);
		panel1.add(urlInput);

		//set location of the user input field for the url
		lay.putConstraint(SpringLayout.WEST, urlInput, 7, SpringLayout.EAST, jtextInput);
		lay.putConstraint(SpringLayout.NORTH, urlInput, 25, SpringLayout.NORTH, panel1);		

		trueCheck = new JCheckBox("Open Source", false);
		panel1.add(trueCheck);

		//set the location of the open source check box
		lay.putConstraint(SpringLayout.WEST, trueCheck, 15, SpringLayout.WEST, panel1);
		lay.putConstraint(SpringLayout.NORTH, trueCheck, 25, SpringLayout.NORTH, jtextInput);

		userInfo = new JTextField("",50);
		userInfo.setEditable(false);
		panel1.add(userInfo);
		//set the location of the open source check box
		lay.putConstraint(SpringLayout.WEST, userInfo, 15, SpringLayout.WEST, panel1);
		lay.putConstraint(SpringLayout.NORTH, userInfo, 25, SpringLayout.NORTH, trueCheck);



	}


	private void addProgressBar(final JPanel panel1){
		//add progress bar and hide it
		panel1.add(pb);
		pb.setVisible(false);
		//set the location of the progress bar
		lay.putConstraint(SpringLayout.WEST, pb, 15, SpringLayout.WEST, panel1);
		lay.putConstraint(SpringLayout.NORTH, pb, 30, SpringLayout.NORTH, jbDownload);

	}

	private void createDownloadButton(final JPanel panel1){
		jbDownload = new JButton("Download");
		jbDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if(!trueCheck.isSelected()){
					userInfo.setText("Please only download open source!");
					return;
				}
				String outputName = null;
				String input = urlInput.getText();
				System.out.print(input);
				if(input.length() == 0){
					userInfo.setText("Please enter a URL");
					return;
				}
				if (input.length() == 0){

				}
				else {
					//to get the filename from the url a process is used
					ProcessBuilder builder = new ProcessBuilder("basename", urlInput.getText());
					Process process = null;

					try {
						process = builder.start();
					} catch (IOException e1) {

						e1.printStackTrace();
					}
					InputStream stdout = process.getInputStream();
					InputStream stderr = process.getErrorStream();
					BufferedReader stdoutBuffered = new BufferedReader(new InputStreamReader(stdout));
					String line = null;

					try {
						outputName = stdoutBuffered.readLine();

					} catch (IOException e1) {
						// TODO Auto-generated catch block
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
						pb.setPreferredSize(new Dimension(250,20));	
						//make the progress bar appear on screen
						pb.setVisible(true);
						//make the cancel button appear
						jbCancel.setVisible(true);
						//set isCancwlled to false
						isCanceled = false;
						//else file does not exist and we can download
						DownloadSwingWorker sw = new DownloadSwingWorker();
						sw.execute();
					} else if (select == JOptionPane.NO_OPTION) {
						return;
					}
				}

				else {
					//set the size of the progress bar
					pb.setPreferredSize(new Dimension(250,20));	
					//make the progress bar appear on screen
					pb.setVisible(true);
					//make the cancel button appear
					jbCancel.setVisible(true);
					//set isCancwlled to false
					isCanceled = false;
					//else file does not exist and we can download
					DownloadSwingWorker sw = new DownloadSwingWorker();
					sw.execute();

				} // end else
			}
		});
		panel1.add(jbDownload);
		//set the location of the download button
		lay.putConstraint(SpringLayout.WEST, jbDownload, 15, SpringLayout.WEST, panel1);
		lay.putConstraint(SpringLayout.NORTH, jbDownload, 25, SpringLayout.NORTH, userInfo);

	}

	private void createCancelButton(final JPanel panel1){
		jbCancel = new JButton("Cancel");
		jbCancel.setVisible(false);
		//if cancel is pressed, then the download halts
		jbCancel.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				isCanceled = true;

			}


		});
		panel1.add(jbCancel);
		//set the location of the download button
		lay.putConstraint(SpringLayout.WEST, jbCancel, 25, SpringLayout.WEST, panel1);
		lay.putConstraint(SpringLayout.NORTH, jbCancel, 25, SpringLayout.NORTH, userInfo);

	}

	public void insertGUIFeatures(final JFrame frame,final JPanel panel1, final JTabbedPane pane2,final JTabbedPane pane3, final JTabbedPane pane){
		//add button to top
		JButton jbDownload = new JButton("Download");
		JPanel darkBluePanel = new JPanel();
		darkBluePanel.setOpaque(true);

		darkBluePanel.setBackground(new Color(66,107,122));
		darkBluePanel.setPreferredSize(new Dimension(200, 40));
		addButtonToPane(darkBluePanel,jbDownload);	
		frame.getContentPane().add(darkBluePanel, BorderLayout.EAST);
		JPanel jp = new JPanel();
		//frame.getContentPane().add(jp,BorderLayout.EAST);
		pane.removeAll();
		panel1.removeAll();
		pane.setPreferredSize(new Dimension(200,200));
		pane.addTab("Download",panel1);
		lay = new SpringLayout();
		panel1.setLayout(lay);

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


			if (isCanceled) {
				jbDownload.setVisible(true);
				userInfo.setText("Download was cancelled");
				pb.setVisible(false);
				pb.setBackground(null);
				jbCancel.setVisible(false);
				return;
			}
			else if (wgetFinished){
				jbDownload.setVisible(true);
				userInfo.setText("Download has completed!");
				pb.setVisible(false);
				jbCancel.setVisible(false);

			}else{
				jbDownload.setVisible(true);
				pb.setVisible(false);
				jbCancel.setVisible(false);
			}



		}


		@Override
		protected Integer doInBackground() throws Exception {
			jbCancel.setVisible(true);
			jbDownload.setVisible(false);
			String URLname = urlInput.getText();
			wgetFinished = false;
			//String URLname = "http://ccmixter.org/content/_ghost/_ghost_-_Reverie_(small_theme).mp3";
			userInfo.setText("Download in progress");
			prog = 0;

			String homePath = System.getProperty("user.home") + "/Downloads/";
			//creates the process for wget
			ProcessBuilder builder = new ProcessBuilder("wget","-P",homePath, "--progress=dot",URLname);
			Process process = builder.start();

			//redirects input and error streams
			InputStream stdout = process.getInputStream();
			InputStream stderr = process.getErrorStream();
			BufferedReader stdoutBuffered = new BufferedReader(new InputStreamReader(stderr));
			String line = null;

			//sends the dot progress bar to the process function for processing
			while ((line = stdoutBuffered.readLine()) != null ) {	

				if (isCanceled) {
					process.destroy();
				}
				publish(line);
			}


			if(process.waitFor() == 0) {
				wgetFinished = true;
				return null;
			}else if(process.waitFor() == 1) {								
				userInfo.setText( "An error has been encounted");
			} else if(process.waitFor() == 2) {
				userInfo.setText("Parse Error");
			} else if(process.waitFor() == 3) {
				userInfo.setText("File I/O Error");
			} else if(process.waitFor() == 4) {
				userInfo.setText( "Network Failure");
			} else if(process.waitFor() == 5) {
				userInfo.setText("SSL Verification Failure");
			} else if(process.waitFor() == 6) {
				userInfo.setText("Username/password authentication failure");
			} else if(process.waitFor() == 7) {
				userInfo.setText("Protocol Error");
			} else if(process.waitFor() == 8) {
				userInfo.setText("Server Issued an error response");
			}
			return null;



		}

		@Override
		protected void process(List<String> chunks) {
			//updates progress bar for each dot 
			for(String chu:chunks){
				prog++;
				pb.setValue(prog);
			}



		}

	}
}
