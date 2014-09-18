import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;


public class Editing {
	
	private void addButtonToPane(JPanel panel, JButton button){
		panel.add(button);
		panel.validate();
	}
	
	public void addGUIComponents(final JFrame frame,final JPanel panel1, final JTabbedPane pane){
		
		
	JButton jbEdit = new JButton("Editing");
	JPanel lightGreenPanel = new JPanel();
	lightGreenPanel.setOpaque(true);
	lightGreenPanel.setBackground(new Color(141,178,92));
	lightGreenPanel.setPreferredSize(new Dimension(200, 40));
	addButtonToPane(lightGreenPanel,jbEdit);
	frame.getContentPane().add(lightGreenPanel, BorderLayout.CENTER);


	jbEdit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().remove(panel1);
				
				JPanel removePanel = new JPanel();
				JPanel replacePanel = new JPanel();
				JPanel overlayPanel = new JPanel();
				JPanel titlePanel = new JPanel();
				JPanel creditsPanel = new JPanel();
				
				pane.removeAll();
				pane.setPreferredSize(new Dimension(200,200));
				pane.addTab( "Remove Audio", removePanel);
				pane.addTab( "Replace Audio", replacePanel);
				pane.addTab( "Overlay Audio", overlayPanel);
				pane.addTab( "Add Title", titlePanel);
				pane.addTab( "Add Credits", creditsPanel);
				frame.getContentPane().add(pane, BorderLayout.SOUTH);
				
				
				frame.pack();
				frame.repaint();
				frame.validate();
			
			}


		});
	
	}
	
	  public void addTab(JTabbedPane tabbedPane, String label) {
		    JButton button = new JButton(label);
		    tabbedPane.addTab(label, button);
		  }
	  
//	public void addTabs(JFrame frame, JPanel panel1){
//		JTabbedPane pane = new JTabbedPane();
//		pane.addTab( "Editing", panel1);
//		frame.getContentPane().add(pane, BorderLayout.SOUTH);
//	}
}
