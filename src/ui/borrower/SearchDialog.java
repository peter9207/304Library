package ui.borrower;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class SearchDialog extends JDialog{

	/**
	 * Default Generated serial ID
	 */
	private static final long serialVersionUID = 739901659522673107L;

	public SearchDialog(Frame owner){
		super(owner);
		this.setLayout(new FlowLayout(FlowLayout.LEADING));
		this.setTitle("Search");
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		this.setSize(new Dimension(400, 300));
		initComponents();
	}
	
	private void initComponents(){
		JLabel label = new JLabel("Search: ");
		this.add(label);
		
		JTextField textField = new JTextField();
		this.add(textField);
		textField.setPreferredSize(new Dimension(80, 30));
		
		String[] searchStrings = {"Title", "Author", "Subject"};
		JComboBox picker = new JComboBox(searchStrings);
		picker.setSelectedIndex(0);
		this.add(picker);
		
		JButton searchButton = new JButton();
		this.add(searchButton);
		searchButton.setText("Search");
	}
}
