package ui.clerk;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import main.MainLibrary;

public class CheckOverdueDialog extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7718138370777865661L;

	public CheckOverdueDialog(Frame owner){
		super(owner,true);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setSize(new Dimension(565,300));
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
		books.addColumn("Checked out since");
		JTable items = new JTable(books);
		items.getColumn("Title").setPreferredWidth(100);
		items.getColumn("#").setPreferredWidth(20);
		items.getColumn("Checked out by").setPreferredWidth(150);

		items.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		items.setSize(new Dimension(200,200));

		JScrollPane listScroller = new JScrollPane(items);

		listScroller.setPreferredSize(new Dimension(565, 200));
		listViewer.add(listScroller);
		this.add(listViewer);

		books.setRowCount(0);
		Vector<Object[]> books2 = MainLibrary.databaseHandler.getBooks("", 0, MainLibrary.databaseHandler.OVERDUE_SEARCH);
		for(int j=0; j<books2.size(); j++){
			books.addRow(books2.get(j));
		}
	}

}
