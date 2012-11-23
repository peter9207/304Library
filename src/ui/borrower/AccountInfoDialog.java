package ui.borrower;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import ui.NotificationDialog;

import main.MainLibrary;
import main.OracleConnection;

public class AccountInfoDialog extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7718138370777865661L;

	private Frame owner;
	public AccountInfoDialog(Frame owner) {
		super(owner,true);
		this.owner = owner;
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setTitle("Account Information");
		this.setSize(new Dimension(600,500));
		initComponents();
	}
	
	private void initComponents(){
		JPanel inputPanel = new JPanel();
		
		JLabel sinOrSt = new JLabel();
		sinOrSt.setText("BID: ");
		inputPanel.add(sinOrSt);
		
		final JTextField inputField = new JTextField();
		inputField.setPreferredSize(new Dimension(80, 25));
		inputPanel.add(inputField);
		
		JButton displayInfo = new JButton();
		displayInfo.setText("Get Info");
		inputPanel.add(displayInfo);
		

		final DefaultTableModel books = new DefaultTableModel();
		
		this.add(inputPanel, BorderLayout.NORTH);
		final JPanel listViewer = new JPanel();

		books.addColumn("Call Number");
		books.addColumn("ISBN");
		books.addColumn("Title");
		books.addColumn("Copy No");
		books.addColumn("Main Author");
		books.addColumn("Publisher");
		books.addColumn("Year");
		JTable items = new JTable(books);
		items.getColumn("Title").setPreferredWidth(100);
		items.setEnabled(false);

		items.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		items.setSize(new Dimension(200,200));

		JScrollPane listScroller = new JScrollPane(items);
		JLabel loans = new JLabel();
		loans.setText("Borrowed books:");
		listViewer.add(loans);
		listScroller.setPreferredSize(new Dimension(565, 100));
		listViewer.add(listScroller);
		this.add(listViewer);
		displayInfo.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					Vector<Object[]>books2 = MainLibrary.databaseHandler.getInfoBorrowedItems(Integer.parseInt(inputField.getText()));
					if (books2 == null){
						System.out.println("nope");
						return;
					}
					for(int j=0; j<books2.size(); j++){
						books.addRow(books2.get(j));
					}
					
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					new NotificationDialog (owner, "ERROR!", "Your BID must be a number.");
					e.printStackTrace();
				}
			}
			
		});


	}
	

}
