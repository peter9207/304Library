package ui.clerk;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import ui.ErrorDialog;

import main.MainLibrary;

public class AddBorrowerDialog extends JDialog{
	/**
	 * 
	 */

	private static final int TEXT_BOX_HEIGHT = 30;
	private static final int TEXT_BOX_WIDTH = 80;

	private static final long serialVersionUID = -7718138370777865661L;

	public AddBorrowerDialog(Frame owner){
		super(owner,false);
		Dimension d = this.getToolkit().getScreenSize();
		Rectangle r = this.getBounds();
		this.setLocation( (d.width - r.width)/4, (d.height - r.height)/4 );
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

		JLabel bidLabel = new JLabel("*BID: ");
		bidPanel.add(bidLabel);

		final JTextField bidField = new JTextField();
		bidPanel.add(bidField);
		bidField.setPreferredSize(new Dimension(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT));

		this.add(bidPanel);


		//Panel - password
		JPanel passwordPanel = new JPanel();
		passwordPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));

		JLabel passwordLabel = new JLabel("*Password:");
		passwordPanel.add(passwordLabel);

		final JTextField passwordField = new JTextField();
		passwordPanel.add(passwordField);

		passwordField.setPreferredSize(new Dimension(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT));

		this.add(passwordPanel);


		//Panel - name
		JPanel namePanel = new JPanel();
		namePanel.setLayout(new FlowLayout(FlowLayout.TRAILING));

		JLabel nameLabel = new JLabel("Name:");
		namePanel.add(nameLabel);

		final JTextField nameField = new JTextField();
		namePanel.add(nameField);
		nameField.setPreferredSize(new Dimension(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT));

		this.add(namePanel);

		//Panel - address
		JPanel addressPanel = new JPanel();
		addressPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));

		JLabel addressLabel = new JLabel("Address:");
		addressPanel.add(addressLabel);

		final JTextField addressField = new JTextField();
		addressPanel.add(addressField);
		addressField.setPreferredSize(new Dimension(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT));

		this.add(addressPanel);

		//Panel - emailAddress
		JPanel emailAddressPanel = new JPanel();
		emailAddressPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));

		JLabel emailAddressLabel = new JLabel("Email:");
		emailAddressPanel.add(emailAddressLabel);

		final JTextField emailAddressField = new JTextField();
		emailAddressPanel.add(emailAddressField);
		emailAddressField.setPreferredSize(new Dimension(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT));

		this.add(emailAddressPanel);

		//Panel - sinOrStNo
		JPanel sinOrStNoPanel = new JPanel();
		sinOrStNoPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));

		JLabel sinOrStNoLabel = new JLabel("*SIN or Student No:");
		sinOrStNoPanel.add(sinOrStNoLabel);

		final JTextField sinOrStNoField = new JTextField();
		sinOrStNoPanel.add(sinOrStNoField);
		sinOrStNoField.setPreferredSize(new Dimension(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT));

		this.add(sinOrStNoPanel);

		//Panel - expiryDate
		JPanel expiryDatePanel = new JPanel();
		expiryDatePanel.setLayout(new FlowLayout(FlowLayout.TRAILING));

		JLabel expiryDateLabel = new JLabel("Expires on (dd/MM/yy):");
		expiryDatePanel.add(expiryDateLabel);

		final JTextField expiryDateField = new JTextField();
		expiryDatePanel.add(expiryDateField);
		expiryDateField.setPreferredSize(new Dimension(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT));

		this.add(expiryDatePanel);

		//Panel - type
		JPanel typePanel = new JPanel();
		typePanel.setLayout(new FlowLayout(FlowLayout.TRAILING));

		JLabel typeLabel = new JLabel("Type:");
		typePanel.add(typeLabel);

		final JTextField typeField = new JTextField();
		typePanel.add(typeField);
		typeField.setPreferredSize(new Dimension(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT));

		this.add(typePanel);

		//				String[] searchStrings = {"Title", "Author", "Subject"};
		//				JComboBox picker = new JComboBox(searchStrings);
		//				picker.setSelectedIndex(0);
		//				this.add(picker);

		JButton addButton = new JButton();
		this.add(addButton);
		addButton.setText("Add");
		addButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {

				if (!bidField.getText().isEmpty() && !passwordField.getText().isEmpty() && !sinOrStNoField.getText().isEmpty()) {
					System.out.println("Add button Clicked");
					int bid, sinOrStNo;
					String name, password, address, email, type, expiry = null;
					bid = Integer.parseInt(bidField.getText().toString());
					sinOrStNo = Integer.parseInt(sinOrStNoField.getText()
							.toString());
					name = nameField.getText().toString();
					address = addressField.getText().toString();
					email = emailAddressField.getText().toString();
					type = typeField.getText().toString();
					password = passwordField.getText().toString();
					expiry = expiryDateField.getText().toString();
					SimpleDateFormat fm = new SimpleDateFormat("dd/MM/yy");
					java.util.Date utilDate = null;
					try {
						utilDate = fm.parse(expiry);
					} catch (ParseException e1) {
						ErrorDialog error = new ErrorDialog("Please input the date in the following format: dd/MM/yy, e.g. 31/01/12");
					}
					java.sql.Date sqlDate = new java.sql.Date(
							utilDate.getTime());
					MainLibrary.databaseHandler.addBorrower(bid, password,
							name, address, email, sinOrStNo, sqlDate, type);
				}
				else
				{
					ErrorDialog error = new ErrorDialog("Please fill in all the required fields marked with (*)");
				}
			}
		});


	}
	
}

