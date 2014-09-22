import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;



public class editVideo {
	private JButton jbTitlePage;
	private JButton jbCreditsPage;
	private JButton jbChoose;
	private JPanel panel;
	private JTextField text;
	SpringLayout layout = new SpringLayout();
	private JTextField userInfo;
	private JTextField fileChoice;

	
	private void addButtonToPane(JPanel panel, JButton button){
		panel.add(button);
		panel.validate();
	}
	
	
	public void insertTitlePageTab(final JTabbedPane pane){
		JPanel titlePagePanel = new JPanel();
		pane.addTab("Add Title", titlePagePanel);
		setTitlePageFeatures(titlePagePanel);
	}
	
	public void insertCreditPageTab(final JTabbedPane pane){
		JPanel creditPagePanel = new JPanel();
		pane.addTab("Add Credits", creditPagePanel);
		setCreditsPageFeatures(creditPagePanel);
	}
	
	private void addTextChoice(){
		text = new JTextField();
		text.setEditable(false);
		panel.add(text);
		text.setPreferredSize(new Dimension(400,30));
	}
	
	private void setTitlePageFeatures(final JPanel panel){
		panel.setLayout(layout);
		addVideoChooser(panel);
		
		jbTitlePage = new JButton("Strip audio off file");
		panel.add(jbTitlePage);

		//move the text box containing user file choice button to its location
		layout.putConstraint(SpringLayout.WEST, jbTitlePage, 15, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, jbTitlePage, 70, SpringLayout.NORTH, fileChoice);

		jbTitlePage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});

		
	}
	
	private void setCreditsPageFeatures (final JPanel panel){
		panel.setLayout(layout);
		addVideoChooser(panel);
		
		jbCreditsPage = new JButton("Strip audio off file");
		panel.add(jbCreditsPage);

		//move the text box containing user file choice button to its location
		layout.putConstraint(SpringLayout.WEST, jbCreditsPage, 15, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, jbCreditsPage, 70, SpringLayout.NORTH, fileChoice);

		jbCreditsPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
	}

	
	private void addVideoChooser(final JPanel panel){
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
}
