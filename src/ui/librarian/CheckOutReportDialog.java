package ui.librarian;

import java.awt.Dimension;
import java.awt.FlowLayout;
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

import main.DatabaseHandler;
import main.MainLibrary;

public class CheckOutReportDialog extends JDialog{

	/**
	 * Default Generated serial ID
	 */
	private static final long serialVersionUID = 739901659522673107L;



	public CheckOutReportDialog(Frame owner){
		super(owner,true);
		this.setLayout(new FlowLayout(FlowLayout.LEADING));
		this.setSize(new Dimension(600,400));
		this.setTitle("Report");
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

		initComponents();
	}

	private void initComponents(){
		JLabel label = new JLabel("Subject: ");
		this.add(label);

		final JTextField textField = new JTextField();
		this.add(textField);
		textField.setPreferredSize(new Dimension(80, 25));
		JButton searchButton = new JButton();
		this.add(searchButton);
		final JPanel listViewer = new JPanel();

		final DefaultTableModel books = new DefaultTableModel();
		books.addColumn("Call Number");
		books.addColumn("ISBN");
		books.addColumn("Title");
		books.addColumn("#");
		books.addColumn("Flag");
		JTable items = new JTable(books);
		items.getColumn("Title").setPreferredWidth(100);
		items.getColumn("#").setPreferredWidth(20);
		items.setEnabled(false);

		items.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		items.setSize(new Dimension(200,200));

		JScrollPane listScroller = new JScrollPane(items);

		listScroller.setPreferredSize(new Dimension(565, 300));
		listViewer.add(listScroller);
		this.add(listViewer);

		searchButton.setText("Filter by subject");
		
		searchButton.addActionListener(new ActionListener(){
		

			@Override
			public void actionPerformed(ActionEvent e){
				books.setRowCount(0);
				String searchTerms = textField.getText().toString();
				Vector<Object[]> books2 = MainLibrary.databaseHandler.getBooks(searchTerms, 0, DatabaseHandler.CHECKED_OUT_REPORT);
				for(int j=0; j<books2.size(); j++){
					books.addRow(books2.get(j));
				}
				return;

			}
		});
		books.setRowCount(0);
		String searchTerms = textField.getText().toString();
		Vector<Object[]> books2 = MainLibrary.databaseHandler.getBooks(searchTerms, 0, DatabaseHandler.CHECKED_OUT_REPORT);
		for(int j=0; j<books2.size(); j++){
			books.addRow(books2.get(j));
		}
		return;
	}

}

