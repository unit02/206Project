

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;


//Playback class is in charge of adding all features related to playback of audio and video to the gui, and of implementing functionality 
public class Playback {

	//define variables
	private EmbeddedMediaPlayerComponent mediaPlayerComponent;
	private JPanel panel;
	private JTextField text;
	private JFrame mediaFrame;
	private SpringLayout layout = new SpringLayout();
	private EmbeddedMediaPlayer video;

	private JFrame frame;
	private JTextField timer;
	private JPanel toolPanel;
	private JButton jbBegin;
	private JButton jbChoose;
	private JButton jbPause;
	private JButton jbFast;
	private JButton jbBack;
	private JButton jbMute;
	private JSlider volumeControl;

	//define booleans
	boolean isFastForwarding = false;
	boolean isBackwards = false;
	boolean isPlaying;

	//method to add button to panel
	private void addButtonToPane(JPanel panel, JButton button){
		panel.add(button);
		panel.validate();
	}

	//This method creates the gui features for the panel below the top
	public void insertGUIFeatures(final JFrame frame1, final JTabbedPane pane2, final JTabbedPane pane3, final JTabbedPane pane){	

		frame = frame1;
		//add button at the top
		JButton jbPlay = new JButton("Playback");
		//create colored background and add to frame
		JPanel swampGreenPanel = new JPanel();
		swampGreenPanel.setOpaque(true);
		swampGreenPanel.setBackground(new Color(73,142,99));
		swampGreenPanel.setPreferredSize(new Dimension(200, 40));
		addButtonToPane(swampGreenPanel,jbPlay);
		frame.getContentPane().add(swampGreenPanel, BorderLayout.WEST);

		//remove all elements from previous tabs
		pane.removeAll();
		panel = new JPanel();		
		pane.addTab("Play Files",panel);
		//set the layout of the panel as a Springlayout
		panel.setLayout(layout);

		//add file selection text box
		addTextBox();
		//add file chooser
		addFileChoice();
		//add start button
		addStartButton();

		//move the text box to its location
		layout.putConstraint(SpringLayout.WEST, text, 135, SpringLayout.WEST, pane);
		layout.putConstraint(SpringLayout.NORTH, text, 17, SpringLayout.NORTH, pane);

		//move the file choice button to its location
		layout.putConstraint(SpringLayout.WEST, jbChoose, 15, SpringLayout.WEST, pane);
		layout.putConstraint(SpringLayout.NORTH, jbChoose, 15, SpringLayout.NORTH, pane);

		//move the start button to its location
		layout.putConstraint(SpringLayout.WEST, jbBegin, 35, SpringLayout.WEST, pane);
		layout.putConstraint(SpringLayout.NORTH, jbBegin, 50, SpringLayout.NORTH, jbChoose);


		//when the button is pressed, the panel below refreshes with the appropriate layout
		jbPlay.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {


				pane.setPreferredSize(new Dimension(200,200));
				//these if/else statements relate to when the user clicks on a button which has already been pressed to hide the panel, or show it again
				{
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
					else if (pane.isVisible()){
						frame.getContentPane().remove(pane);
						frame.pack();
						frame.validate();
						pane.setVisible(false);
					}
					else if (pane.isVisible() == false){
						frame.getContentPane().add(pane, BorderLayout.SOUTH);			
						pane.setVisible(true);
						frame.pack();
						frame.validate();
					}
					frame.pack();
					frame.repaint();
					frame.validate();

				}
			}


		});

	}




	//adds start button to the gui
	private void addStartButton(){
		jbBegin = new JButton("Start");
		panel.add(jbBegin);
		jbBegin.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String chosenfile = text.getText();
				//creates media player and starts the media
				startMediaPlayer(chosenfile);


			}


		});
	}



	//adds text box to inform user which file they have chosen
	private void addTextBox(){
		//add text field to show which file chosen
		text = new JTextField();
		text.setEditable(false);
		panel.add(text);
		text.setPreferredSize(new Dimension(400,20));
	}
	//adds timer to the media frame
	private void addTimer(){
		//create timer 
		timer = new JTextField();
		timer.setEditable(false);
		timer.setPreferredSize(new Dimension(130,20));
		Timer time = new Timer(1000, new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				double time = (video.getTime()/1000.0);
				double length = video.getLength()/1000.0;
				timer.setText(time + "/" + length);
			}

		}
				);
		//start timer
		time.start();

		//add timer to the toolpanel
		toolPanel.add(timer);

	}

	//adds file choice button and implementation
	private void addFileChoice(){
		//creates new button
		jbChoose = new JButton("Choose File");
		JFileChooser jfile = null;

		//adds file choosing functionality to button
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

		addButtonToPane(panel,jbChoose);

	}


	//creates and starts media player
	private void startMediaPlayer(String chosenfile){
		//prevents another media player from opening if one is already open
		if(isPlaying){
			return;
		}

		//video player code from http://www.capricasoftware.co.uk/projects/vlcj/tutorial2.html and from Nasser's lecture with minor edits
		mediaFrame = new JFrame();
		isPlaying = true;
		//creates new video player
		mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
		//creates new jpanel to store the video player in it
		JPanel panel = new JPanel();
		//sets the panel layout as borderlayout
		BorderLayout lay = new BorderLayout();	
		panel.setLayout(lay);
		//initiate the variable video for ease of fetching
		video = mediaPlayerComponent.getMediaPlayer();
		//add the video player to the jpanel
		panel.add(mediaPlayerComponent,BorderLayout.CENTER);
		//add the panel to the jframe
		mediaFrame.setContentPane(panel);
		//set initial size of the video player to match that of the gui
		mediaPlayerComponent.setSize(450, 550);

		//sets location for the video frame to appear right next to the gui
		Point p = frame.getLocationOnScreen();
		mediaFrame.setLocation(p.x + 780, p.y -25);
		mediaFrame.setSize(700, 230);
		mediaFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mediaFrame.setVisible(true);


		//adds listener to listen for when the jframe is closed
		mediaFrame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				video.stop();
				isPlaying = false;
			}


		});


		//set up and add tool panel
		toolPanel = new JPanel();
		addBackButton();
		addPauseButton();
		addPlayButton();
		addFFButton();
		addMuteButton();
		addVolumeControl();
		addTimer();
		
		//add tool bar to the panel
		panel.add(toolPanel,BorderLayout.SOUTH);	

		//changed file for ease of testing, 
		//TODO change back to chosen file for submission and type checking
		//video.playMedia(chosenfile);
		video.playMedia("bbb.mp4");

		
	}

	//add pause button
	private void addPauseButton(){
		jbPause = new JButton("||");

		jbPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isFastForwarding = false;
				isBackwards = false;
				video.pause();
				jbBegin.setVisible(true);
				jbPause.setVisible(false);
			}
		});
		jbPause.setVisible(true);
		addButtonToPane(toolPanel,jbPause);
	}
	
//add play button
	private void addPlayButton(){
		jbBegin = new JButton(">");

		jbBegin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isFastForwarding = false;
				isBackwards = false;
				video.start();
				jbBegin.setVisible(false);
				jbPause.setVisible(true);

			}
		});
		jbBegin.setVisible(false);
		addButtonToPane(toolPanel,jbBegin);
	}

	//add fast forward button
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

		addButtonToPane(toolPanel,jbFast);

	}
//add reverse button
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
		addButtonToPane(toolPanel,jbBack);

	}

//creates the swingworker to allow for continuous fast forwarding
	private class FFSwingWorker extends SwingWorker<Integer,String>{

		protected void done(){
			jbFast.setEnabled(true);

		}


		@Override
		protected Integer doInBackground() throws Exception {
			while(isFastForwarding){
				video.skip(100);
			}
			return null;
		}
		
	}
	
	//creates the swingworker to allow for continuous rewinding
	private class BKSwingWorker extends SwingWorker<Integer,String>{
		protected void done(){
			jbBack.setEnabled(true);
		}


		@Override
		protected Integer doInBackground() throws Exception {
			while(isBackwards){
				video.skip(-100);
			}
			return null;
		}
	}
//add mute button
	private void addMuteButton(){
		jbMute = new JButton("Mute");

		jbMute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(video.isMute()){
					video.mute(false);
					jbMute.setText("Mute");

				} else {
					video.mute(true);
					jbMute.setText("Unmute");
				}
			}
		});
		toolPanel.add(jbMute);
	}
	
	//add volume control slider
	private void addVolumeControl(){

		volumeControl = new JSlider(JSlider.HORIZONTAL,0,100,100);
		volumeControl.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int volumeLevel = volumeControl.getValue();
				video.setVolume(volumeLevel);

			}
		});
		toolPanel.add(volumeControl);

	}


}


