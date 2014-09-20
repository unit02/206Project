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
				
				
				pane.removeAll();
				pane.setPreferredSize(new Dimension(200,200));
				
//				//add tabs at the top of the tabbed pane
//				JPanel removePanel = new JPanel();
//				pane.addTab( "Remove Audio", removePanel);
				editAudio ea = new editAudio();
				ea.insertRemoveAudio(pane);
				JPanel replacePanel = new JPanel();
				JPanel overlayPanel = new JPanel();
				JPanel titlePanel = new JPanel();
				JPanel creditsPanel = new JPanel();
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
	  

}
