package ui.librarian;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;

import ui.NotificationDialog;

import main.MainLibrary;

public class AddBookDialog extends JDialog{

	/**
	 * Default Generated serial ID
	 */
	private static final long serialVersionUID = 739901659522673107L;

	private static final int TEXT_BOX_HEIGHT = 25;
	private static final int TEXT_BOX_WIDTH = 80;
	private Frame owner;

	public AddBookDialog(Frame owner){
		super(owner,false);
		this.owner = owner;
		this.setLayout(new GridLayout(0,2));
		this.setTitle("Add new book");
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

		this.setSize(new Dimension(500, 350));
		Dimension d = this.getToolkit().getScreenSize();
		Rectangle r = this.getBounds();
		this.setLocation( (d.width - r.width)/4, (d.height - r.height)/4 );
		initComponents();
	}

	private void initComponents(){
		JPanel column0 = new JPanel();
		column0.setLayout(new GridLayout(0,1));

		JPanel column1 = new JPanel();
		column1.setLayout(new BoxLayout(column1, BoxLayout.PAGE_AXIS));

		final DefaultListModel<String> subjects = new DefaultListModel<String>();
		JList<String> subjectList = new JList<String>(subjects);

		final DefaultListModel<String> authors = new DefaultListModel<String>();
		JList<String> authorList = new JList<String>(authors);

		JPanel listViewer1 = new JPanel();
		JPanel listViewer2 = new JPanel();

		JScrollPane listScroller1 = new JScrollPane(subjectList);
		JScrollPane listScroller2 = new JScrollPane(authorList);

		subjectList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		subjectList.setLayoutOrientation(JList.VERTICAL);
		subjectList.setVisibleRowCount(-1);
		listScroller1.setPreferredSize(new Dimension(185, 80));
		subjectList.setSize(new Dimension(200,200));
		
		authorList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		authorList.setLayoutOrientation(JList.VERTICAL);
		authorList.setVisibleRowCount(-1);
		listScroller2.setPreferredSize(new Dimension(185, 80));
		authorList.setSize(new Dimension(200,200));
		
		listViewer1.add(listScroller1);
		listViewer2.add(listScroller2);

		final JTextField subjectField = new JTextField();
		subjectField.setSize(new Dimension(120,30));;
		subjectField.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				if (arg0.getKeyCode()==KeyEvent.VK_ENTER){
					final String sub = subjectField.getText().trim();
					if(!sub.isEmpty()){
						if(!subjects.contains(sub)) {
							subjects.addElement(sub);
							subjectField.setText("");
						}
					}
				}
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		final JTextField authorField = new JTextField();
		authorField.setSize(new Dimension(120,30));
		authorField.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				if (arg0.getKeyCode()==KeyEvent.VK_ENTER){
					final String author = authorField.getText().trim();
					if(!author.isEmpty()){
						if(!authors.contains(author)) {
							authors.addElement(author);
							authorField.setText("");
						}
					}
				}
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		subjectList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				JList list = (JList)evt.getSource();
				if (evt.getClickCount() == 2) {
					int index = list.locationToIndex(evt.getPoint());
					try {
						subjects.remove(index);
					} catch (Exception e) {

					}
				}

			}
		});
		authorList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				JList list = (JList)evt.getSource();
				if (evt.getClickCount() == 2) {
					int index = list.locationToIndex(evt.getPoint());
					try {
						authors.remove(index);
					} catch (Exception e) {

					}
				}

			}
		});
		JButton addSubject = new JButton();
		addSubject.setText("Add Subject");
		addSubject.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Add book button Pressed");
				final String sub = subjectField.getText().trim();
				if(!sub.isEmpty()){
					if(!subjects.contains(sub)) {
						subjects.addElement(sub);
						subjectField.setText("");
					}
				}
			}
		});
		JButton addAuthor = new JButton();
		addAuthor.setText("Add Author");
		addAuthor.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Add book button Pressed");
				final String author = authorField.getText().trim();
				if(!author.isEmpty()){
					if(!authors.contains(author)) {
						authors.addElement(author);
						authorField.setText("");
					}
				}
			}
		});
		column1.add(subjectField);
		column1.add(addSubject);
		column1.add(authorField);
		column1.add(addAuthor);

		column1.add(listViewer1);
		column1.add(listViewer2);

		//Panel - copy
		JPanel copyPanel = new JPanel();
		copyPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		JLabel copyLabel = new JLabel("Copies (Default: 1 copy): ");
		copyPanel.add(copyLabel);

		final JTextField copyBox = new JTextField();
		copyBox.setPreferredSize(new Dimension(50, TEXT_BOX_HEIGHT));
		copyPanel.add(copyBox);

		column0.add(copyPanel);

		//Panel - Call Number
		JPanel CallNumberPanel = new JPanel();
		CallNumberPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));

		JLabel callNumberLabel = new JLabel("CallNumber: ");
		CallNumberPanel.add(callNumberLabel);

		final JTextField callNumberField = new JTextField();
		CallNumberPanel.add(callNumberField);
		callNumberField.setPreferredSize(new Dimension(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT));

		column0.add(CallNumberPanel);


		//Panel - ISBN
		JPanel ISBNPanel = new JPanel();
		ISBNPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));

		JLabel ISBNLabel = new JLabel("ISBN:");
		ISBNPanel.add(ISBNLabel);

		final JTextField ISBNField = new JTextField();
		ISBNPanel.add(ISBNField);
		ISBNField.setPreferredSize(new Dimension(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT));

		column0.add(ISBNPanel);


		//Panel - title
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new FlowLayout(FlowLayout.TRAILING));

		JLabel titleLabel = new JLabel("Title:");
		titlePanel.add(titleLabel);

		final JTextField titleField = new JTextField();
		titlePanel.add(titleField);
		titleField.setPreferredSize(new Dimension(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT));

		column0.add(titlePanel);

		//Panel - mainAuthor
		JPanel mainAuthorPanel = new JPanel();
		mainAuthorPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));

		JLabel mainAuthorLabel = new JLabel("Main Author:");
		mainAuthorPanel.add(mainAuthorLabel);

		final JTextField mainAuthorField = new JTextField();
		mainAuthorPanel.add(mainAuthorField);
		mainAuthorField.setPreferredSize(new Dimension(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT));

		column0.add(mainAuthorPanel);

		//Panel - publisher
		JPanel publisherPanel = new JPanel();
		publisherPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));

		JLabel publisherLabel = new JLabel("Publisher:");
		publisherPanel.add(publisherLabel);

		final JTextField publisherField = new JTextField();
		publisherPanel.add(publisherField);
		publisherField.setPreferredSize(new Dimension(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT));

		column0.add(publisherPanel);

		//Panel - year
		JPanel yearPanel = new JPanel();
		yearPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));

		JLabel yearLabel = new JLabel("Year:");
		yearPanel.add(yearLabel);

		final JTextField yearField = new JTextField();
		yearPanel.add(yearField);
		yearField.setPreferredSize(new Dimension(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT));

		column0.add(yearPanel);

		//		String[] searchStrings = {"Title", "Author", "Subject"};
		//		JComboBox picker = new JComboBox(searchStrings);
		//		picker.setSelectedIndex(0);
		//		this.add(picker);

		JButton addButton = new JButton();
		column1.add(addButton);
		this.add(column0);
		this.add(column1);
		addButton.setText("Add");
		addButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!titleField.getText().toString().isEmpty() && !mainAuthorField.getText().toString().isEmpty()&&!publisherField.getText().toString().isEmpty()) {
					try {
						int callNumber, isbn, year;
						String title, mainAuthor, publisher;
						callNumber = Integer.parseInt(callNumberField.getText()
								.toString());
						isbn = Integer.parseInt(ISBNField.getText().toString());
						year = Integer.parseInt(yearField.getText().toString());
						title = titleField.getText().toString();
						mainAuthor = mainAuthorField.getText().toString();
						publisher = publisherField.getText().toString();
						int copy = 1;
						if(!copyBox.getText().isEmpty()){
							copy = Integer.parseInt(copyBox.getText().toString());
						}
						
						Vector<String> subVector = new Vector<String>();
						Vector<String> authorVector = new Vector<String>();

						for (int j = 0; j < subjects.getSize(); j++) {
							subVector.add((String) subjects.getElementAt(j));
						}	
						for (int j = 0; j < authors.getSize(); j++) {
							authorVector.add((String) authors.getElementAt(j));
						}	
						authors.clear();
						subjects.clear();
						callNumberField.setText("");
						copyBox.setText("");
						authorField.setText("");
						subjectField.setText("");
						ISBNField.setText("");
						mainAuthorField.setText("");
						publisherField.setText("");
						titleField.setText("");
						yearField.setText("");
						MainLibrary.databaseHandler.addBook(callNumber, isbn,
								title, mainAuthor, publisher, year, copy, subVector, authorVector);
					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						new NotificationDialog(owner, "ERROR!","Call Number, ISBN, and year must be in a number format.");
					}
				}
				else
				{
					new NotificationDialog(owner, "ERROR!","Some fields are empty. Please check your inputs again.");
				}
			}
		});

	}
}
