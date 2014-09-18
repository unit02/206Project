import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;


public class Playback {

	private EmbeddedMediaPlayerComponent mediaPlayerComponent;
	private EmbeddedMediaPlayer inputMedia;

	private void addButtonToPane(JPanel panel, JButton button){
		panel.add(button);
		panel.validate();
	}


	public void insertGUIFeatures(final JFrame frame, final JPanel panel1, final JTabbedPane pane){	
		//add button to top
		JButton jbPlay = new JButton("Playback");
		JPanel swampGreenPanel = new JPanel();
		swampGreenPanel.setOpaque(true);
		swampGreenPanel.setBackground(new Color(73,142,99));
		swampGreenPanel.setPreferredSize(new Dimension(200, 40));
		addButtonToPane(swampGreenPanel,jbPlay);
		frame.getContentPane().add(swampGreenPanel, BorderLayout.WEST);

		jbPlay.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {


				pane.removeAll();
				JPanel panel = new JPanel();
				
				pane.add(panel);
				SpringLayout layout = new SpringLayout();
				panel.setLayout(layout);
				
				//add text field to show which file chosen
				 JTextField text = new JTextField();
				panel.add(text);
				//move the text box to its location
				layout.putConstraint(SpringLayout.WEST, text, 15, SpringLayout.WEST, pane);
				layout.putConstraint(SpringLayout.NORTH, text, 25, SpringLayout.NORTH, pane);

				//add file chooser
				 setFileChoice(panel,text);
			
				text.setPreferredSize(new Dimension(200,25));
				//add begin button
				JButton jBegin = new JButton("Begin");
				panel.add(jBegin);

			
				////move the begin button to its location
				layout.putConstraint(SpringLayout.WEST, jBegin, 15, SpringLayout.WEST, pane);
				layout.putConstraint(SpringLayout.NORTH, jBegin, 25, SpringLayout.NORTH, text);
				
				pane.setPreferredSize(new Dimension(200,200));
				frame.getContentPane().add(pane, BorderLayout.SOUTH);			
				frame.pack();
				frame.repaint();
				frame.validate();
			}


		});
	}

	private void setFileChoice(JPanel panel,final JTextField text){
		JButton jbChoose = new JButton("Choose File");
		JFileChooser jfile = null;
		jbChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Opens JFileChooser when button pressed
				JFileChooser jfile = new JFileChooser();

				int response = jfile.showOpenDialog(null);
				if (response == JFileChooser.APPROVE_OPTION) {
					String chosenFile = jfile.getSelectedFile().toString();
					//inputMedia.playMedia(chosenfile);
					text.setText(chosenFile);
				}

				jfile.setVisible(true);
			}
		});
	
		panel.add(jbChoose);
		
	}



	private void addPlayer() {
		JFrame mediaFrame = new JFrame();
		JPanel videoPane = new JPanel(new BorderLayout());
		mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
		inputMedia = mediaPlayerComponent.getMediaPlayer();

		videoPane.add(mediaPlayerComponent, BorderLayout.CENTER);
		videoPane.setBounds(0, 0, 900, 400);
		videoPane.setVisible(true);

		mediaFrame.add(videoPane);
	}

}
