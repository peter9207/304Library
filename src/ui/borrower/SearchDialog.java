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

	private static final int TEXT_BOX_HEIGHT = 30;
	private static final int TEXT_BOX_WIDTH = 80;
	
	
	public SearchDialog(Frame owner){
		super(owner,true);
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
		textField.setPreferredSize(new Dimension(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT));
		
		String[] searchStrings = {"Title", "Author", "Subject"};
		JComboBox picker = new JComboBox(searchStrings);
		picker.setSelectedIndex(0);
		this.add(picker);
		
		JButton searchButton = new JButton();
		this.add(searchButton);
		searchButton.setText("Search");
		
		
	}
}
