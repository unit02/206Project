

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;


public class Playback {

	private EmbeddedMediaPlayerComponent mediaPlayerComponent;
	private JPanel panel;
	private JTextField text;
	private JFrame mediaFrame;
	private SpringLayout layout = new SpringLayout();

	private JPanel toolPanel;
	private JButton jbBegin;
	private JButton jbChoose;
	private JButton jbPause;
	private JButton jbFast;
	private JButton jbBack;
	private JButton jbMute;

	private JSlider volumeControl;

	boolean isFastForwarding = false;
	boolean isBackwards = false;


	private void addButtonToPane(JPanel panel, JButton button){
		panel.add(button);
		panel.validate();
	}

	//This method creates the gui features for the panel below the top
	public void insertGUIFeatures(final JFrame frame, final JPanel panel1, final JTabbedPane pane){	

		//add button to top
		JButton jbPlay = new JButton("Playback");
		JPanel swampGreenPanel = new JPanel();
		swampGreenPanel.setOpaque(true);
		swampGreenPanel.setBackground(new Color(73,142,99));
		swampGreenPanel.setPreferredSize(new Dimension(200, 40));
		addButtonToPane(swampGreenPanel,jbPlay);
		frame.getContentPane().add(swampGreenPanel, BorderLayout.WEST);

		//when the button is pressed, the panel below refreshes with the appropriate layout
		jbPlay.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {

				//remove all elements from previous tabs
				pane.removeAll();
				panel = new JPanel();		
				pane.add(panel);
				panel.setLayout(layout);

				//add file selection text box
				addTextBox();
				//add file chooser
				addFileChoice();
				//add start button
				addStartButton();

				//move the text box to its location
				layout.putConstraint(SpringLayout.WEST, text, 15, SpringLayout.WEST, pane);
				layout.putConstraint(SpringLayout.NORTH, text, 25, SpringLayout.NORTH, pane);

				////move the file choice button to its location
				layout.putConstraint(SpringLayout.WEST, jbChoose, 15, SpringLayout.WEST, pane);
				layout.putConstraint(SpringLayout.NORTH, jbChoose, 25, SpringLayout.NORTH, text);

				//move the start button to its location
				layout.putConstraint(SpringLayout.WEST, jbBegin, 35, SpringLayout.WEST, pane);
				layout.putConstraint(SpringLayout.NORTH, jbBegin, 50, SpringLayout.NORTH, jbChoose);


				pane.setPreferredSize(new Dimension(200,200));
				frame.getContentPane().add(pane, BorderLayout.SOUTH);			
				frame.pack();
				frame.repaint();
				frame.validate();
			}


		});
	}





	private void addStartButton(){
		jbBegin = new JButton("Start");
		panel.add(jbBegin);
		jbBegin.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String chosenfile = text.getText();
				startMediaPlayer(chosenfile);


			}


		});
	}






	private void addTextBox(){
		//add text field to show which file chosen
		text = new JTextField();
		text.setEditable(false);
		panel.add(text);
		text.setPreferredSize(new Dimension(400,30));
	}


	private void addFileChoice(){
		jbChoose = new JButton("Choose File");
		JFileChooser jfile = null;
		jbChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Opens JFileChooser when button pressed
				JFileChooser jfile = new JFileChooser();

				int response = jfile.showOpenDialog(null);
				if (response == JFileChooser.APPROVE_OPTION) {
					String chosenFile = jfile.getSelectedFile().toString();
					text.setText(chosenFile);
				}
				jfile.setVisible(true);
			}
		});
		panel.add(jbChoose);

	}



	private void startMediaPlayer(String chosenfile){
		//video player code from http://www.capricasoftware.co.uk/projects/vlcj/tutorial2.html with minor edits
		mediaFrame = new JFrame();
		mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
		mediaFrame.setContentPane(mediaPlayerComponent);
		mediaFrame.setLocation(100, 100);
		mediaFrame.setSize(1050, 600);
		mediaFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		mediaFrame.setVisible(true);

		//set up layout and add tool panel

		toolPanel = new JPanel();
		addBackButton();
		addPauseButton();
		addPlayButton();
		addFFButton();
		addMuteButton();
		addVolumeControl();
		BorderLayout lay = new BorderLayout();		
		mediaPlayerComponent.setLayout(lay);
		mediaPlayerComponent.add(toolPanel,BorderLayout.SOUTH);	
		//changed file for ease of testing, 
		//TODO change back to chosen file for submission and type checking
		mediaPlayerComponent.getMediaPlayer().playMedia("bbb.mp4");
	}

	private void addPauseButton(){
		jbPause = new JButton("||");

		jbPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isFastForwarding = false;
				isBackwards = false;
				mediaPlayerComponent.getMediaPlayer().pause();
				jbBegin.setVisible(true);
				jbPause.setVisible(false);
			}
		});
		jbPause.setVisible(true);
		toolPanel.add(jbPause);
	}

	private void addPlayButton(){
		jbBegin = new JButton(">");

		jbBegin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isFastForwarding = false;
				isBackwards = false;
				mediaPlayerComponent.getMediaPlayer().start();
				jbBegin.setVisible(false);
				jbPause.setVisible(true);

			}
		});
		jbBegin.setVisible(false);
		toolPanel.add(jbBegin);
	}

	private void addFFButton(){
		jbFast = new JButton(">>");

		jbFast.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isFastForwarding = true;
				jbBegin.setVisible(true);
				jbPause.setVisible(true);
				FFSwingWorker sw = new FFSwingWorker();
				sw.execute();
				jbFast.setEnabled(false);

			}
		});

		toolPanel.add(jbFast);

	}

	private void addBackButton(){
		jbBack = new JButton("<<");

		jbBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isBackwards = true;
				jbBegin.setVisible(true);
				jbPause.setVisible(true);
				BKSwingWorker sw = new BKSwingWorker();
				sw.execute();
				jbBack.setEnabled(false);
			}
		});
		toolPanel.add(jbBack);

	}


	private class FFSwingWorker extends SwingWorker<Integer,String>{

		protected void done(){
			jbFast.setEnabled(true);

		}


		@Override
		protected Integer doInBackground() throws Exception {
			while(isFastForwarding){
				mediaPlayerComponent.getMediaPlayer().skip(50);
			}
			return null;
		}
	}

	private class BKSwingWorker extends SwingWorker<Integer,String>{
		protected void done(){
			jbBack.setEnabled(true);
		}


		@Override
		protected Integer doInBackground() throws Exception {
			while(isBackwards){
				mediaPlayerComponent.getMediaPlayer().skip(-50);
			}
			return null;
		}
	}

	private void addMuteButton(){
		jbMute = new JButton("Mute");

		jbMute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(mediaPlayerComponent.getMediaPlayer().isMute()){
					mediaPlayerComponent.getMediaPlayer().mute(false);


				} else {
					mediaPlayerComponent.getMediaPlayer().mute(true);

				}
			}
		});
		toolPanel.add(jbMute);
	}
	private void addVolumeControl(){

		volumeControl = new JSlider(JSlider.HORIZONTAL,0,100,100);
		volumeControl.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int volumeLevel = volumeControl.getValue();
				mediaPlayerComponent.getMediaPlayer().setVolume(volumeLevel);

			}
		});
		toolPanel.add(volumeControl);

	}
}



