

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;



public class GUI {

	//method to add button to the panel
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

		JTabbedPane downloadPane = new JTabbedPane();	
		JTabbedPane editPane = new JTabbedPane();
		JTabbedPane playbackPane = new JTabbedPane();	


		// Create the three buttons, download, editing and playback 
		// Calls the methods that insert the GUI features into the main GUI
		Download dl = new Download();
		dl.insertGUIFeatures(frame,panel1,editPane,playbackPane,downloadPane);

		Editing edit = new Editing();
		edit.addGUIComponents(frame,downloadPane,playbackPane,editPane);

		Playback pb = new Playback();
		pb.insertGUIFeatures(frame, downloadPane,editPane,playbackPane);

		frame.pack();
		frame.setVisible(true);
	}

}