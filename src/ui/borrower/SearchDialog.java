package ui.borrower;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import main.MainLibrary;

public class SearchDialog extends JDialog{

	/**
	 * Default Generated serial ID
	 */
	private static final long serialVersionUID = 739901659522673107L;

	private static final int TEXT_BOX_HEIGHT = 30;
	private static final int TEXT_BOX_WIDTH = 80;


	public SearchDialog(Frame owner){
		super(owner,true);
		this.setLayout(new FlowLayout(FlowLayout.LEADING));
		this.setTitle("Search");
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		this.setSize(new Dimension(600, 300));
		initComponents();
	}

	private void initComponents(){
		JLabel label = new JLabel("Search: ");
		this.add(label);

		final JTextField textField = new JTextField();
		this.add(textField);
		textField.setPreferredSize(new Dimension(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT));

		final String[] searchStrings = {"Title", "Author", "Subject"};
		final JComboBox picker = new JComboBox(searchStrings);
		picker.setSelectedIndex(0);
		this.add(picker);


		JButton searchButton = new JButton();
		this.add(searchButton);
		final JPanel listViewer = new JPanel();

		final DefaultTableModel books = new DefaultTableModel();
		books.addColumn("Call Number");
		books.addColumn("ISBN");
		books.addColumn("Title");
		books.addColumn("Main Author");
		books.addColumn("Publisher");
		books.addColumn("Year");
		books.addColumn("Copies in");
		books.addColumn("Copies out");
		JTable items = new JTable(books);


		items.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		items.setSize(new Dimension(200,200));

		JScrollPane listScroller = new JScrollPane(items);

		listScroller.setPreferredSize(new Dimension(565, 200));
		listViewer.add(listScroller);
		this.add(listViewer);
		searchButton.setText("Search");
		searchButton.addActionListener(new ActionListener(){


			@Override
			public void actionPerformed(ActionEvent e){
				books.setRowCount(0);
				String searchTerms = textField.getText().toString();
				String searchParameters = searchStrings[picker.getSelectedIndex()];
				Vector<Object[]> books2 = MainLibrary.databaseHandler.showBooks(searchTerms, searchParameters);
				for(int j=0; j<books2.size(); j++){
					books.addRow(books2.get(j));
				}
				return;

			}
		});









	}
}

