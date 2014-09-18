import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;


public class Editing {
	
	private void addButtonToPane(JPanel panel, JButton button){
		panel.add(button);
		panel.validate();
	}
	
	public void addGUIComponents(JFrame frame,JPanel panel1){
		
	
		
	JButton jEdit = new JButton("Editing");
	JPanel lightGreenPanel = new JPanel();
	lightGreenPanel.setOpaque(true);
	lightGreenPanel.setBackground(new Color(141,178,92));
	lightGreenPanel.setPreferredSize(new Dimension(200, 40));
	addButtonToPane(lightGreenPanel,jEdit);
	frame.getContentPane().add(lightGreenPanel, BorderLayout.CENTER);
	//addTabs(frame, panel1 );		
	}
	
	public void addTabs(JFrame frame, JPanel panel1){
		JTabbedPane pane = new JTabbedPane();
		pane.addTab( "Editing", panel1);
		frame.getContentPane().add(pane, BorderLayout.SOUTH);
	}
}
