package ui.clerk;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import ui.NotificationDialog;

import main.MainLibrary;

public class CheckOutDialog extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7718138370777865661L;

	private Frame owner;
	public CheckOutDialog(Frame owner){
		super(owner,false);
		this.owner = owner;
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		this.setSize(new Dimension(400,300));
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		this.setTitle("Check out items");
		initComponents();

	}

	private void initComponents(){
		
		final DefaultListModel books = new DefaultListModel();
		final JList items = new JList(books);
		
		
		JPanel bid = new JPanel();
		bid.setLayout(new FlowLayout(FlowLayout.LEADING));
		JLabel bidLabel = new JLabel();
		bidLabel.setText("BID: ");
		bid.add(bidLabel);
		
		final JTextField bidField = new JTextField();
		bidField.setPreferredSize(new Dimension(80,30));
		bid.add(bidField);
		this.add(bid);
		
		JPanel cnumber = new JPanel();
		JLabel cNoLabel = new JLabel();
		cNoLabel.setText("Call Number: ");
		cnumber.add(cNoLabel);
		
		final JTextField callNumber = new JTextField();
		callNumber.setPreferredSize(new Dimension(120,30));
		cnumber.add(callNumber);
		
		JButton addBook = new JButton();
		addBook.setText("Add book to check out");
		addBook.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JTextArea callNumberTextComp = new JTextArea();
				final String callNo = callNumber.getText();
				books.addElement(callNo);
			}
		});
				
		cnumber.add(addBook);
		this.add(cnumber);
		
		JPanel listViewer = new JPanel();
		
		JScrollPane listScroller = new JScrollPane(items);

		items.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		items.setLayoutOrientation(JList.VERTICAL);
		items.setVisibleRowCount(-1);
		listScroller.setPreferredSize(new Dimension(250, 80));
		items.setSize(new Dimension(200,200));

		listViewer.add(listScroller);
		
		items.addMouseListener(new MouseAdapter() {
		    @Override
			public void mouseClicked(MouseEvent evt) {
		        JList list = (JList)evt.getSource();
		        if (evt.getClickCount() == 2) {
		            int index = list.locationToIndex(evt.getPoint());
		            try {
						books.remove(index);
					} catch (Exception e) {
					}
		        }
		        
		    }
		});
		JPanel checkOutPanel = new JPanel();
		
		JButton checkOut = new JButton();
		checkOut.setText("Check Out Books");
		checkOut.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					Vector<Integer> callNumbers = new Vector<Integer>();
					for (int j = 0; j < books.getSize(); j++) {
						callNumbers.add(Integer.parseInt(books.getElementAt(j).toString()));
					}				
					MainLibrary.databaseHandler.checkOut(callNumbers, Integer.parseInt(bidField.getText().toString()));
					books.clear();
				} catch (NumberFormatException e) {
					new NotificationDialog(owner, "ERROR!","Please use only numbers for the Call Number field.");
					
				}
			}
			
		});
		checkOutPanel.add(checkOut);

		this.add(listViewer);
		this.add(checkOutPanel);
		

	}

}
