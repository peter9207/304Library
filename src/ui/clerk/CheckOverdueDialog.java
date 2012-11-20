package ui.clerk;

import java.awt.Dimension;
import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class CheckOverdueDialog extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7718138370777865661L;

	public CheckOverdueDialog(Frame owner){
		super(owner,true);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setTitle("Overdue Books");
		initComponents();
		
	}
	
	private void initComponents(){
		final JPanel listViewer = new JPanel();

		final DefaultTableModel books = new DefaultTableModel();
		books.addColumn("Call Number");
		books.addColumn("ISBN");
		books.addColumn("Title");
		books.addColumn("Main Author");
		books.addColumn("Publisher");
		books.addColumn("Year");
		books.addColumn("Copies In");
		books.addColumn("Copies Out");
		JTable items = new JTable(books);


		items.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		items.setSize(new Dimension(200,200));

		JScrollPane listScroller = new JScrollPane(items);

		listScroller.setPreferredSize(new Dimension(565, 200));
		listViewer.add(listScroller);
		this.add(listViewer);
	}

}
