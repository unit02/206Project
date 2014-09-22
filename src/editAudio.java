import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;


public class editAudio {
	private JButton jbChoose;
	private JButton jbRemove;
	private JTextField userInfo;
	private JTextField fileChoice;
	private JTextField titleName;
	private JTextField mp3Display;
	SpringLayout layout = new SpringLayout();

	//method to add tab to the tabbed pane 
	public void insertRemoveAudio(final JTabbedPane pane){
		//add tabs at the top of the tabbed pane
		JPanel removePanel = new JPanel();
		pane.addTab( "Remove Audio", removePanel);
		setRemovePanelFeatures(removePanel);
	}

	private void setRemovePanelFeatures(final JPanel panel){	
		panel.setLayout(layout);
		addFileChooser(panel);
		addTitleFeatures(panel);
		jbRemove = new JButton("Strip audio off file");
		panel.add(jbRemove);

		//move the text box containing user file choice button to its location
		layout.putConstraint(SpringLayout.WEST, jbRemove, 15, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, jbRemove, 70, SpringLayout.NORTH, fileChoice);

		jbRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});


	}

	private void addTitleFeatures(final JPanel panel){
		//add the user input box for the title of the output file
		titleName = new JTextField();
		titleName.setPreferredSize(new Dimension(200,20));
		panel.add(titleName);
		//move the mp3 output file name to its location on the screen
		layout.putConstraint(SpringLayout.WEST, titleName, 15, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, titleName, 55, SpringLayout.NORTH, panel);
		
		
		mp3Display = new JTextField();
		mp3Display.setText(".mp3");
		mp3Display.setEditable(false);
		titleName.setPreferredSize(new Dimension(35,20));
		panel.add(mp3Display);
		//move the choose file button to its location
		layout.putConstraint(SpringLayout.WEST, mp3Display, 85, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, mp3Display, 55, SpringLayout.NORTH, panel);
	
	}
	private void addFileChooser(final JPanel panel){
		//create and add functionality for file choosing button
		jbChoose = new JButton("Choose File");
		JFileChooser jfile = null;
		jbChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Opens JFileChooser when button pressed
				JFileChooser jfile = new JFileChooser();
				int response = jfile.showOpenDialog(null);
				if (response == JFileChooser.APPROVE_OPTION) {
					String chosenFile = jfile.getSelectedFile().toString();
					fileChoice.setText(chosenFile);
				}
				jfile.setVisible(true);
			}
		});
		panel.add(jbChoose);


		//move the choose file button to its location
		layout.putConstraint(SpringLayout.WEST, jbChoose, 15, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, jbChoose, 25, SpringLayout.NORTH, panel);

		//creates text field to store which file the user input
		fileChoice = new JTextField();
		fileChoice.setPreferredSize(new Dimension(150,20));
		fileChoice.setEditable(false);
		panel.add(fileChoice);

		//move the text box containing user file choice button to its location
		layout.putConstraint(SpringLayout.WEST, fileChoice, 125, SpringLayout.WEST, jbChoose);
		layout.putConstraint(SpringLayout.NORTH, fileChoice, 27, SpringLayout.NORTH, panel);

		//add textbox for error messages to be conveyed to the user
		userInfo = new JTextField();
		userInfo.setPreferredSize(new Dimension(150,20));
		userInfo.setEditable(false);
		//move the text box containing user file choice button to its location
		layout.putConstraint(SpringLayout.WEST, userInfo, 15, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, userInfo, 50, SpringLayout.NORTH, jbChoose);
		panel.add(userInfo);

	}







	public void insertReplaceAudio(final JTabbedPane pane){
		//add tabs at the top of the tabbed pane
		JPanel replacePanel = new JPanel();
		pane.addTab( "Replace Audio", replacePanel);

	}

	public void insertOverlayAudio(final JTabbedPane pane){
		//add tabs at the top of the tabbed pane
		JPanel overlayPanel = new JPanel();
		pane.addTab( "Overlay Audio", overlayPanel);
	}

}


