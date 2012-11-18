package ui.librarian;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import ui.ErrorDialog;

import main.MainLibrary;

public class AddBookDialog extends JDialog{

	/**
	 * Default Generated serial ID
	 */
	private static final long serialVersionUID = 739901659522673107L;

	private static final int TEXT_BOX_HEIGHT = 30;
	private static final int TEXT_BOX_WIDTH = 80;
	
	
	public AddBookDialog(Frame owner){
		super(owner,false);
		this.setLayout(new GridLayout(0,1));
		this.setTitle("Add new book");
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		this.setSize(new Dimension(200, 350));
		Dimension d = this.getToolkit().getScreenSize();
		Rectangle r = this.getBounds();
		this.setLocation( (d.width - r.width)/4, (d.height - r.height)/4 );
		initComponents();
	}
	
	private void initComponents(){
		//Panel - copy
		JPanel copyPanel = new JPanel();
		copyPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JLabel copyLabel = new JLabel("Copy? ");
		copyPanel.add(copyLabel);
		
		final JRadioButton copyBox = new JRadioButton();
		copyPanel.add(copyBox);
		
		this.add(copyPanel);
		
		//Panel - Call Number
		JPanel CallNumberPanel = new JPanel();
		CallNumberPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
		
		JLabel callNumberLabel = new JLabel("CallNumber: ");
		CallNumberPanel.add(callNumberLabel);
		
		final JTextField callNumberField = new JTextField();
		CallNumberPanel.add(callNumberField);
		callNumberField.setPreferredSize(new Dimension(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT));
		
		this.add(CallNumberPanel);
		
		
		//Panel - ISBN
		JPanel ISBNPanel = new JPanel();
		ISBNPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));

		JLabel ISBNLabel = new JLabel("ISBN:");
		ISBNPanel.add(ISBNLabel);
		
		final JTextField ISBNField = new JTextField();
		ISBNPanel.add(ISBNField);
		ISBNField.setPreferredSize(new Dimension(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT));
		
		this.add(ISBNPanel);
		
		
		//Panel - title
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new FlowLayout(FlowLayout.TRAILING));

		JLabel titleLabel = new JLabel("Title:");
		titlePanel.add(titleLabel);
		
		final JTextField titleField = new JTextField();
		titlePanel.add(titleField);
		titleField.setPreferredSize(new Dimension(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT));
		
		this.add(titlePanel);

		//Panel - mainAuthor
		JPanel mainAuthorPanel = new JPanel();
		mainAuthorPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));

		JLabel mainAuthorLabel = new JLabel("Main Author:");
		mainAuthorPanel.add(mainAuthorLabel);
		
		final JTextField mainAuthorField = new JTextField();
		mainAuthorPanel.add(mainAuthorField);
		mainAuthorField.setPreferredSize(new Dimension(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT));
		
		this.add(mainAuthorPanel);
		
		//Panel - publisher
		JPanel publisherPanel = new JPanel();
		publisherPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));

		JLabel publisherLabel = new JLabel("Publisher:");
		publisherPanel.add(publisherLabel);
		
		final JTextField publisherField = new JTextField();
		publisherPanel.add(publisherField);
		publisherField.setPreferredSize(new Dimension(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT));
		
		this.add(publisherPanel);
		
		//Panel - year
		JPanel yearPanel = new JPanel();
		yearPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));

		JLabel yearLabel = new JLabel("Year:");
		yearPanel.add(yearLabel);
		
		final JTextField yearField = new JTextField();
		yearPanel.add(yearField);
		yearField.setPreferredSize(new Dimension(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT));
		
		this.add(yearPanel);
		
//		String[] searchStrings = {"Title", "Author", "Subject"};
//		JComboBox picker = new JComboBox(searchStrings);
//		picker.setSelectedIndex(0);
//		this.add(picker);
		
		JButton addButton = new JButton();
		this.add(addButton);
		addButton.setText("Add");
		addButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!titleField.getText().toString().isEmpty() && !mainAuthorField.getText().toString().isEmpty()&&!publisherField.getText().toString().isEmpty()) {
					try {
						System.out.println("Add button Clicked");
						int callNumber, isbn, year;
						String title, mainAuthor, publisher;
						callNumber = Integer.parseInt(callNumberField.getText()
								.toString());
						isbn = Integer.parseInt(ISBNField.getText().toString());
						year = Integer.parseInt(yearField.getText().toString());
						title = titleField.getText().toString();
						mainAuthor = mainAuthorField.getText().toString();
						publisher = publisherField.getText().toString();
						MainLibrary.databaseHandler.addBook(callNumber, isbn,
								title, mainAuthor, publisher, year, copyBox.isSelected());
					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						ErrorDialog error = new ErrorDialog("Call Number, ISBN, and year must be in a number format.");
					}
				}
				else
				{
					ErrorDialog error = new ErrorDialog("Some fields are empty. Please check your inputs again.");
				}
			}
		});
		
	}
}
