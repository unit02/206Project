import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;


public class Playback {

	private EmbeddedMediaPlayerComponent mediaPlayerComponent;
	private EmbeddedMediaPlayer inputMedia;

	private void addButtonToPane(JPanel panel, JButton button){
		panel.add(button);
		panel.validate();
	}

	private void onClick(JButton jbPlay, final JFrame frame){


		jbPlay.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel panel = new JPanel();
				JTabbedPane pane = new JTabbedPane();
				pane.setPreferredSize(new Dimension(200,200));
				frame.getContentPane().add(pane, BorderLayout.SOUTH);
				System.out.print("HEL");

			}


		});


		jbPlay.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
	}

	public void insertGUIFeatures(final JFrame frame){	
		//add button to top
		JButton jbPlay = new JButton("Playback");
		JPanel swampGreenPanel = new JPanel();
		swampGreenPanel.setOpaque(true);
		swampGreenPanel.setBackground(new Color(73,142,99));
		swampGreenPanel.setPreferredSize(new Dimension(200, 40));
		addButtonToPane(swampGreenPanel,jbPlay);
		frame.getContentPane().add(swampGreenPanel, BorderLayout.WEST);



		//pane.setVisible(false);
		//onClick(jbPlay, frame);


		jbPlay.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {

				JPanel panel = new JPanel();
				final JTabbedPane pane = new JTabbedPane();
				pane.setPreferredSize(new Dimension(200,200));
				pane.addTab( "Pagehhh 1", panel);
				frame.getContentPane().add(pane, BorderLayout.SOUTH);
				frame.pack();
				frame.repaint();
				frame.validate();
			}


		});
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
