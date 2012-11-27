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

import ui.NotificationDialog;

import main.DatabaseHandler;
import main.MainLibrary;

public class MostPopularDialog extends JDialog{

	/**
	 * Default Generated serial ID
	 */
	private static final long serialVersionUID = 739901659522673107L;

	private static final int TEXT_BOX_HEIGHT = 30;
	private static final int TEXT_BOX_WIDTH = 80;
	
	
	public MostPopularDialog(Frame owner){
		super(owner,true);
		this.setLayout(new FlowLayout(FlowLayout.LEADING));
		this.setTitle("Popularity Report");
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		
		this.setSize(new Dimension(635, 400));
		initComponents();
	}
	
	private void initComponents(){
		//Input Panel
		
		JPanel input = new JPanel();
		input.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel yearLabel = new JLabel("Year: ");
		input.add(yearLabel);
		
		final JTextField yearField = new JTextField();
		input.add(yearField);
		yearField.setPreferredSize(new Dimension(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT));
		
		JLabel numLabel = new JLabel("Top number of books: ");
		input.add(numLabel);
		
		final JTextField numField = new JTextField();
		input.add(numField);
		numField.setPreferredSize(new Dimension(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT));
		
		JButton searchButton = new JButton();
		input.add(searchButton);
		searchButton.setText("Generate Report");
		this.add(input);
		
		//ReportPanel
		
		final JPanel listViewer = new JPanel();

		final DefaultTableModel books = new DefaultTableModel();
		books.addColumn("Call Number");
		books.addColumn("ISBN");
		books.addColumn("Title");
		books.addColumn("Main Author");
		books.addColumn("Publisher");
		books.addColumn("Year");
		books.addColumn("Borrowed Count");
		
		JTable items = new JTable(books);
		items.getColumn("Title").setPreferredWidth(100);
		items.getColumn("Borrowed Count").setPreferredWidth(100);
		items.setEnabled(false);
		items.setAutoCreateRowSorter(false);
		items.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		items.setSize(new Dimension(250,200));

		JScrollPane listScroller = new JScrollPane(items);

		listScroller.setPreferredSize(new Dimension(600, 300));
		listViewer.add(listScroller);
		this.add(listViewer);
		searchButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					int searchParameters = Integer.parseInt(numField.getText());
					Integer.parseInt(yearField.getText());
					if (yearField.getText().length()!=4){
						new NotificationDialog(null, "ERROR","Please ensure the year is entered in a YYYY format.");
						return;
					}
					books.setRowCount(0);
					String searchTerms = yearField.getText().toString();
					Vector<Object[]> books2 = MainLibrary.databaseHandler.getBooks(searchTerms, searchParameters, DatabaseHandler.MOST_POPULAR_REPORT);
					for(int j=0; j<books2.size(); j++){
						books.addRow(books2.get(j));
					}

					return;
				} catch (NumberFormatException e) {
					new NotificationDialog(null, "ERROR", "The year field and number field requires only numbers to be entered.");
				}
				
			}
			
		});
		
		
	}
}
