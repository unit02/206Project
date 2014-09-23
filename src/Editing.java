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
	
	public void addGUIComponents(final JFrame frame,final JTabbedPane pane2, final JTabbedPane pane3, final JTabbedPane pane){
		
		
	JButton jbEdit = new JButton("Editing");
	JPanel lightGreenPanel = new JPanel();
	lightGreenPanel.setOpaque(true);
	lightGreenPanel.setBackground(new Color(141,178,92));
	lightGreenPanel.setPreferredSize(new Dimension(200, 40));
	addButtonToPane(lightGreenPanel,jbEdit);
	frame.getContentPane().add(lightGreenPanel, BorderLayout.CENTER);
	pane.removeAll();
	pane.setPreferredSize(new Dimension(200,200));
	
	
	overlayAudio oa = new overlayAudio();
	stripAudio sa = new stripAudio();
	replaceAudio ra = new replaceAudio();

	sa.insertRemoveAudio(pane);
	oa.insertOverlayAudio(pane);
	ra.insertReplaceAudio(pane);
	
	editVideo ev = new editVideo();
	ev.insertTitlePageTab(pane);
	ev.insertCreditPageTab(pane);
	


	jbEdit.addActionListener(new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
			
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
				pane.setVisible(false);
				frame.getContentPane().remove(pane);
			}
			else if (pane.isVisible() == false){
				pane.setVisible(true);
				frame.getContentPane().add(pane, BorderLayout.SOUTH);
			}
			
			
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