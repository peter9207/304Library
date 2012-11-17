package ui.librarian;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddBookDialog extends JDialog{

	/**
	 * Default Generated serial ID
	 */
	private static final long serialVersionUID = 739901659522673107L;

	private static final int TEXT_BOX_HEIGHT = 30;
	private static final int TEXT_BOX_WIDTH = 80;
	
	
	public AddBookDialog(Frame owner){
		super(owner,true);
		this.setLayout(new GridLayout(0,1));
		this.setTitle("Add new book");
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		this.setSize(new Dimension(200, 300));
		initComponents();
	}
	
	private void initComponents(){
		//Panel - Call Number
		JPanel CallNumberPanel = new JPanel();
		CallNumberPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
		
		JLabel callNumberLabel = new JLabel("CallNumber: ");
		CallNumberPanel.add(callNumberLabel);
		
		JTextField callNumberField = new JTextField();
		CallNumberPanel.add(callNumberField);
		callNumberField.setPreferredSize(new Dimension(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT));
		
		this.add(CallNumberPanel);
		
		
		//Panel - ISBN
		JPanel ISBNPanel = new JPanel();
		ISBNPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));

		JLabel ISBNLabel = new JLabel("ISBN:");
		ISBNPanel.add(ISBNLabel);
		
		JTextField ISBNField = new JTextField();
		ISBNPanel.add(ISBNField);
		ISBNField.setPreferredSize(new Dimension(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT));
		
		this.add(ISBNPanel);
		
		
		//Panel - title
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new FlowLayout(FlowLayout.TRAILING));

		JLabel titleLabel = new JLabel("Title:");
		titlePanel.add(titleLabel);
		
		JTextField titleField = new JTextField();
		titlePanel.add(titleField);
		titleField.setPreferredSize(new Dimension(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT));
		
		this.add(titlePanel);

		//Panel - mainAuthor
		JPanel mainAuthorPanel = new JPanel();
		mainAuthorPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));

		JLabel mainAuthorLabel = new JLabel("Main Author:");
		mainAuthorPanel.add(mainAuthorLabel);
		
		JTextField mainAuthorField = new JTextField();
		mainAuthorPanel.add(mainAuthorField);
		mainAuthorField.setPreferredSize(new Dimension(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT));
		
		this.add(mainAuthorPanel);
		
		//Panel - publisher
		JPanel publisherPanel = new JPanel();
		publisherPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));

		JLabel publisherLabel = new JLabel("Publisher:");
		publisherPanel.add(publisherLabel);
		
		JTextField publisherField = new JTextField();
		publisherPanel.add(publisherField);
		publisherField.setPreferredSize(new Dimension(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT));
		
		this.add(publisherPanel);
		
		//Panel - year
		JPanel yearPanel = new JPanel();
		yearPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));

		JLabel yearLabel = new JLabel("Year:");
		yearPanel.add(yearLabel);
		
		JTextField yearField = new JTextField();
		yearPanel.add(yearField);
		yearField.setPreferredSize(new Dimension(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT));
		
		this.add(yearPanel);
		
//		String[] searchStrings = {"Title", "Author", "Subject"};
//		JComboBox picker = new JComboBox(searchStrings);
//		picker.setSelectedIndex(0);
//		this.add(picker);
		
		JButton searchButton = new JButton();
		this.add(searchButton);
		searchButton.setText("Add");
		
		
	}
}
