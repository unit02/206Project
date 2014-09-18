import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;


public class Playback {
	
	public void insertGUIFeatures( final JFrame gui){	


	}
	public void addTabs(JFrame frame, JPanel panel1){
		JTabbedPane pane = new JTabbedPane();
		pane.addTab( "Page 1", panel1);
		frame.getContentPane().add(pane, BorderLayout.SOUTH);
	}
}
