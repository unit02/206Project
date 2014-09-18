

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;



public class GUI {

	public static void addButtonToPane(JPanel panel, JButton button){
		panel.add(button);
		panel.validate();
	}


	protected static JComponent makeTextPanel(String text){
		JPanel panel = new JPanel(false);
		JLabel filler = new JLabel(text);
		filler.setHorizontalAlignment(JLabel.CENTER);;
		panel.setLayout(new GridLayout(1,1));
		panel.add(filler);
		return panel;
	}

	public static void main(String[] args) {

		// Create and set up the main JFrame
		JFrame frame = new JFrame("VAMIX");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel1 = new JPanel();
		panel1.setLayout( null );
		JLabel label1 = new JLabel( "haha");
		label1.setBounds( 10, 15, 150, 20 );
		panel1.add( label1 );
//		JTabbedPane pane = new JTabbedPane();
//		pane.setPreferredSize(new Dimension(200,200));
//		pane.addTab( "Pageee 1", panel1);
//		frame.getContentPane().add(pane, BorderLayout.SOUTH);


		// Create the three buttons, download, extract and history(log)

		Download dl = new Download();
		dl.insertGUIFeatures(frame);

		Editing edit = new Editing();
		edit.addGUIComponents(frame,panel1);

		Playback pb = new Playback();
		pb.insertGUIFeatures(frame);

		frame.pack();
		frame.setVisible(true);
	}

}