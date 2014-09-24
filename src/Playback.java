

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
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
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;



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
		//create colored background
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
		text.setPreferredSize(new Dimension(400,20));
	}

	private void addTimer(){
		//create timer 
		timer = new JTextField();
		timer.setEditable(false);
		timer.setPreferredSize(new Dimension(100,20));
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

		addButtonToPane(panel,jbChoose);

	}



	private void startMediaPlayer(String chosenfile){

		//video player code from http://www.capricasoftware.co.uk/projects/vlcj/tutorial2.html with minor edits
		mediaFrame = new JFrame();
		if(isPlaying){
			return;
		}
		isPlaying = true;
		mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
		
		JPanel panel = new JPanel();
		BorderLayout lay = new BorderLayout();	
		panel.setLayout(lay);
		video = mediaPlayerComponent.getMediaPlayer();
		panel.add(mediaPlayerComponent,BorderLayout.CENTER);
		mediaFrame.setContentPane(panel);
		//mediaPlayerComponent.setSize(450, 550);
	
		//sets location for the video frame to appear
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
		
		
		//set up layout and add tool panel

		toolPanel = new JPanel();
		//toolPanel.setPreferredSize(new Dimension(50,30));
		addBackButton();
		addPauseButton();
		addPlayButton();
		addFFButton();
		addMuteButton();
		addVolumeControl();
		//mediaFrame.add(toolPanel);

		mediaFrame.setLayout(lay);
		panel.add(toolPanel,BorderLayout.SOUTH);	
		//mediaFrame.setResizable(false);
		//changed file for ease of testing, 
		//TODO change back to chosen file for submission and type checking
		//video.playMedia(chosenfile);
		video.playMedia("bbb.mp4");

		addTimer();
	}

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


	private class FFSwingWorker extends SwingWorker<Integer,String>{

		protected void done(){
			jbFast.setEnabled(true);

		}


		@Override
		protected Integer doInBackground() throws Exception {
			while(isFastForwarding){
				video.skip(50);
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
				video.skip(-50);
			}
			return null;
		}
	}

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


	private class timerSwingWorker extends SwingWorker<Integer,String>{

		@Override
		protected Integer doInBackground() throws Exception {	

			long videoLength = video.getTime();
			timer.setText( "Time : " + videoLength);
			timer.setPreferredSize(new Dimension(50,20));
			return null;
		}


	}
}


