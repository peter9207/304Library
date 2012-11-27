package ui.borrower;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import ui.NotificationDialog;

import main.MainLibrary;

public class AccountInfoDialog extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7718138370777865661L;

	private Frame owner;
	public AccountInfoDialog(Frame owner) {
		super(owner,true);
		this.owner = owner;
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
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
		
		//HoldRequests Table;
		
		final DefaultTableModel books2 = new DefaultTableModel();

		books2.addColumn("Call Number");
		books2.addColumn("ISBN");
		books2.addColumn("Title");
		books2.addColumn("Main Author");
		books2.addColumn("Publisher");
		books2.addColumn("Year");
		books2.addColumn("Requested on");
		JTable items2 = new JTable(books2);
		items2.getColumn("Title").setPreferredWidth(100);
		items2.getColumn("Year").setPreferredWidth(40);
		items2.setEnabled(false);

		items2.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		items2.setSize(new Dimension(200,200));

		JScrollPane listScroller2 = new JScrollPane(items2);
		JLabel hr = new JLabel();
		hr.setText("Requested holds on:");
		listViewer.add(hr);
		listScroller2.setPreferredSize(new Dimension(565, 100));
		listViewer.add(listScroller2);
		
		
		//outstanding fines table
		
		final DefaultTableModel books3 = new DefaultTableModel();

		books3.addColumn("Call Number");
		books3.addColumn("ISBN");
		books3.addColumn("Title");
		books3.addColumn("Copy No.");
		books3.addColumn("Amount owed : $");
		JTable items3 = new JTable(books3);
		items3.getColumn("Title").setPreferredWidth(100);
		items3.getColumn("Copy No.").setPreferredWidth(30);
		items3.getColumn("Amount owed : $").setPreferredWidth(40);
		items3.setEnabled(false);

		items3.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		items2.setSize(new Dimension(200,200));

		JScrollPane listScroller3 = new JScrollPane(items3);
		JLabel fi = new JLabel();
		fi.setText("Outstanding fines:");
		listViewer.add(fi);
		listScroller3.setPreferredSize(new Dimension(565, 100));
		listViewer.add(listScroller3);
		
		//
		
		
		displayInfo.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					books.setRowCount(0);
					books2.setRowCount(0);
					books3.setRowCount(0);
					Vector<Object[]>booksResult = MainLibrary.databaseHandler.getInfoBorrowedItems(Integer.parseInt(inputField.getText()));
					if (booksResult == null){
						System.out.println("nope");
						return;
					}
					for(int j=0; j<booksResult.size(); j++){
						books.addRow(booksResult.get(j));
					}
					
					Vector<Object[]>booksResult2 = MainLibrary.databaseHandler.getInfoHeldItems(Integer.parseInt(inputField.getText()));
					if (booksResult2 == null){
						System.out.println("nope");
						return;
					}
					for(int j=0; j<booksResult2.size(); j++){
						books2.addRow(booksResult2.get(j));
					}
					
					Vector<Object[]>booksResult3 = MainLibrary.databaseHandler.getInfoFines(Integer.parseInt(inputField.getText()));
					if (booksResult3 == null){
						System.out.println("nope");
						return;
					}
					for(int j=0; j<booksResult3.size(); j++){
						books3.addRow(booksResult3.get(j));
					}
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					new NotificationDialog (owner, "ERROR!", "Your BID must be a number.");

				}
			}
			
		});


	}
	

}
