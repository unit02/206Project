import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Download {
	private void addButtonToPane(JPanel panel, JButton button){
		panel.add(button);
		panel.validate();
	}
	
	public void insertGUIFeatures( final JFrame frame){	
		//add button to top
		JButton extract = new JButton("Download");
		JPanel darkBluePanel = new JPanel();
		darkBluePanel.setOpaque(true);
		darkBluePanel.setBackground(new Color(66,107,122));
		darkBluePanel.setPreferredSize(new Dimension(200, 40));
		addButtonToPane(darkBluePanel,extract);	
		frame.getContentPane().add(darkBluePanel, BorderLayout.EAST);
	}
}
