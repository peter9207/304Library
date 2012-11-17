package ui.clerk;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddBorrowerDialog extends JDialog{
	/**
	 * 
	 */

	private static final int TEXT_BOX_HEIGHT = 30;
	private static final int TEXT_BOX_WIDTH = 80;

	private static final long serialVersionUID = -7718138370777865661L;

	public AddBorrowerDialog(Frame owner){
		super(owner,true);
		this.setLayout(new GridLayout(0,1));
		this.setSize(new Dimension(250,400));
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setTitle("Add Borrower");
		initComponents();

	}

	private void initComponents(){
		//Panel - BID
		JPanel bidPanel = new JPanel();
		bidPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));

		JLabel bidLabel = new JLabel("BID: ");
		bidPanel.add(bidLabel);

		JTextField bidField = new JTextField();
		bidPanel.add(bidField);
		bidField.setPreferredSize(new Dimension(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT));

		this.add(bidPanel);


		//Panel - password
		JPanel passwordPanel = new JPanel();
		passwordPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));

		JLabel passwordLabel = new JLabel("Password:");
		passwordPanel.add(passwordLabel);

		JTextField passwordField = new JTextField();
		passwordPanel.add(passwordField);
		passwordField.setPreferredSize(new Dimension(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT));

		this.add(passwordPanel);


		//Panel - name
		JPanel namePanel = new JPanel();
		namePanel.setLayout(new FlowLayout(FlowLayout.TRAILING));

		JLabel nameLabel = new JLabel("Name:");
		namePanel.add(nameLabel);

		JTextField nameField = new JTextField();
		namePanel.add(nameField);
		nameField.setPreferredSize(new Dimension(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT));

		this.add(namePanel);

		//Panel - address
		JPanel addressPanel = new JPanel();
		addressPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));

		JLabel addressLabel = new JLabel("Address:");
		addressPanel.add(addressLabel);

		JTextField addressField = new JTextField();
		addressPanel.add(addressField);
		addressField.setPreferredSize(new Dimension(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT));

		this.add(addressPanel);

		//Panel - emailAddress
		JPanel emailAddressPanel = new JPanel();
		emailAddressPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));

		JLabel emailAddressLabel = new JLabel("Email:");
		emailAddressPanel.add(emailAddressLabel);

		JTextField emailAddressField = new JTextField();
		emailAddressPanel.add(emailAddressField);
		emailAddressField.setPreferredSize(new Dimension(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT));

		this.add(emailAddressPanel);

		//Panel - sinOrStNo
		JPanel sinOrStNoPanel = new JPanel();
		sinOrStNoPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));

		JLabel sinOrStNoLabel = new JLabel("SIN or Student No:");
		sinOrStNoPanel.add(sinOrStNoLabel);

		JTextField sinOrStNoField = new JTextField();
		sinOrStNoPanel.add(sinOrStNoField);
		sinOrStNoField.setPreferredSize(new Dimension(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT));

		this.add(sinOrStNoPanel);

		//Panel - expiryDate
		JPanel expiryDatePanel = new JPanel();
		expiryDatePanel.setLayout(new FlowLayout(FlowLayout.TRAILING));

		JLabel expiryDateLabel = new JLabel("Expires on:");
		expiryDatePanel.add(expiryDateLabel);

		JTextField expiryDateField = new JTextField();
		expiryDatePanel.add(expiryDateField);
		expiryDateField.setPreferredSize(new Dimension(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT));

		this.add(expiryDatePanel);

		//Panel - type
		JPanel typePanel = new JPanel();
		typePanel.setLayout(new FlowLayout(FlowLayout.TRAILING));

		JLabel typeLabel = new JLabel("Type:");
		typePanel.add(typeLabel);

		JTextField typeField = new JTextField();
		typePanel.add(typeField);
		typeField.setPreferredSize(new Dimension(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT));

		this.add(typePanel);

		//				String[] searchStrings = {"Title", "Author", "Subject"};
		//				JComboBox picker = new JComboBox(searchStrings);
		//				picker.setSelectedIndex(0);
		//				this.add(picker);

		JButton searchButton = new JButton();
		this.add(searchButton);
		searchButton.setText("Add");


	}
}


