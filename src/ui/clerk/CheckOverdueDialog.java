package ui.clerk;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import ui.NotificationDialog;

import main.DatabaseHandler;
import main.MainLibrary;

public class CheckOverdueDialog extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7718138370777865661L;

	public CheckOverdueDialog(Frame owner){
		super(owner,true);
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		this.setLayout(new FlowLayout());
		this.setSize(new Dimension(600,300));
		this.setTitle("Overdue Books");
		initComponents();
		
	}
	
	private void initComponents(){
		final JPanel listViewer = new JPanel();

		final DefaultTableModel books = new DefaultTableModel();
		books.addColumn("Call Number");
		books.addColumn("ISBN");
		books.addColumn("Title");
		books.addColumn("#");
		books.addColumn("Checked out by");
		books.addColumn("Email: ");
		books.addColumn("Since");
		JTable items = new JTable(books){
			public boolean isCellEditable(int rowIndex, int colIndex) {
				  return false; //Disallow the editing of any cell
				  }
		};
		items.getColumn("Title").setPreferredWidth(100);
		items.getColumn("#").setPreferredWidth(20);
		items.getColumn("Checked out by").setPreferredWidth(150);

		items.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		items.setSize(new Dimension(200,200));
		items.addMouseListener(new MouseAdapter() {
		    @Override
			public void mouseClicked(MouseEvent evt) {
		        JTable list = (JTable)evt.getSource();
		        if (evt.getClickCount() == 2) {
		            int row = list.getSelectedRow();
		            try {
						new NotificationDialog(null, "Email sent","An email has been sent to "+books.getValueAt(row, 5));
					} catch (Exception e) {
					}
		        }
		        
		    }
		});
		JScrollPane listScroller = new JScrollPane(items);

		listScroller.setPreferredSize(new Dimension(565, 200));
		listViewer.add(listScroller);
		this.add(listViewer);

		books.setRowCount(0);
		Vector<Object[]> books2 = MainLibrary.databaseHandler.getBooks("", 0, DatabaseHandler.OVERDUE_SEARCH);
		for(int j=0; j<books2.size(); j++){
			books.addRow(books2.get(j));
		}
		JButton emailAll = new JButton();
		emailAll.setText("Email All");
		emailAll.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new NotificationDialog(null, "Emails sent", "Emails have been sent to all the borrowers of these books who have provided an email address.");
			}
		});
			
		this.add(emailAll);
	}

}
