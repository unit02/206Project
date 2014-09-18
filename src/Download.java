import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;


public class Download {
	private void addButtonToPane(JPanel panel, JButton button){
		panel.add(button);
		panel.validate();
	}

	public void insertGUIFeatures(final JFrame frame, final JPanel panel1, final JTabbedPane pane){
		//add button to top
		JButton jbDownload = new JButton("Download");
		JPanel darkBluePanel = new JPanel();
		darkBluePanel.setOpaque(true);
		darkBluePanel.setBackground(new Color(66,107,122));
		darkBluePanel.setPreferredSize(new Dimension(200, 40));
		addButtonToPane(darkBluePanel,jbDownload);	
		frame.getContentPane().add(darkBluePanel, BorderLayout.EAST);

		jbDownload.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {

				pane.removeAll();
				pane.setPreferredSize(new Dimension(200,200));
				frame.getContentPane().add(pane, BorderLayout.SOUTH);			
				frame.pack();
				frame.repaint();
				frame.validate();
			}


		});
	}
}

