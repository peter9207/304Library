package ui;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import ui.borrower.AccountInfoDialog;
import ui.borrower.PayFineDialog;
import ui.borrower.PlaceHoldDialog;
import ui.borrower.SearchDialog;
import ui.clerk.AddBorrowerDialog;
import ui.clerk.CheckOutDialog;
import ui.clerk.CheckOverdueDialog;
import ui.clerk.ReturnDialog;
import ui.librarian.AddBookDialog;
import ui.librarian.CheckOutReportDialog;
import ui.librarian.MostPopularDialog;
/**
 * 
 * @author peter9207
 *
 *
 * class for the Menu Bar at the top of the window,
 * all the menu item configuration should be added here
 */
public class TopMenuBar extends JMenuBar {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8419770343363543192L;
	private Frame owner;
	
	public TopMenuBar (Frame f){
		super();
		owner = f;
		addFileMenuItem();
		addLibraryMenuItem();
		addBorrowerMenuItem();
		addClerkMenuItem();
		addLibrarianMenuItem();
		
	}
	private void addFileMenuItem(){
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic('F');
		
		JMenuItem aboutItem = new JMenuItem("About...");
		aboutItem.setEnabled(true);
		aboutItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				new NotificationDialog(null, "304 Project Library System", "Authors: Shawn Lim, Zhang WeiZhong, Aleksander Arsovski. ");
			}
		});
		fileMenu.add(aboutItem);
		

		
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.setEnabled(true);
		exitItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Insert action for exit
				System.exit(0);
				
			}
			
		});
		fileMenu.add(exitItem);
		
		this.add(fileMenu);
	}
	private void addLibraryMenuItem(){
		JMenu libMenu = new JMenu("Library");
		libMenu.setMnemonic('L');
		
		JMenuItem addItem = new JMenuItem("Reset Library");
		addItem.setEnabled(true);
		addItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
			}
			
		});
		libMenu.add(addItem);
	}
		
		
	
	private void addBorrowerMenuItem(){
		JMenu bMenu = new JMenu("Borrower");
		
		JMenuItem search = new JMenuItem("Search");
		search.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Search button Pressed");
				
				JDialog searchDialog = new SearchDialog(owner);
				searchDialog.setVisible(true);
				
				
			}
			
		});
		bMenu.add(search);
		
		JMenuItem accountInfo = new JMenuItem("Account Info");
		accountInfo.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Account Info Button Pressed");
				JDialog accountInfo = new AccountInfoDialog(owner);
				accountInfo.setVisible(true);
			}
			
		});
		bMenu.add(accountInfo);
		
		JMenuItem hold = new JMenuItem("Place Hold");
		hold.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Place Hold Button Pressed");
				JDialog placeHold = new PlaceHoldDialog(owner);
				placeHold.setVisible(true);
			}
			
		});
		bMenu.add(hold);
		
		JMenuItem payFine = new JMenuItem("Pay Fine");
		payFine.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Pay Fine Button Pressed");
				JDialog payFine = new PayFineDialog(owner);
				payFine.setVisible(true);
			}
			
		});
		bMenu.add(payFine);
		
		this.add(bMenu);
		
	}
	private void addClerkMenuItem(){
		JMenu cMenu = new JMenu("Clerk");
		
		JMenuItem addBorrower = new JMenuItem("Add new borrower...");
		addBorrower.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Add borrower button Pressed");
				
				JDialog AddBorrowerDialog = new AddBorrowerDialog(owner);
				AddBorrowerDialog.setVisible(true);
				
				
			}
			
		});
		cMenu.add(addBorrower);
		
		JMenuItem checkOut = new JMenuItem("Check out item");
		checkOut.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Check Out Button Pressed");
				JDialog CheckOutDialog = new CheckOutDialog(owner);
				CheckOutDialog.setVisible(true);
			}
			
		});
		cMenu.add(checkOut);
		
		JMenuItem processReturn = new JMenuItem("Process Return");
		processReturn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Process return button Pressed");
				JDialog ReturnDialog = new ReturnDialog(owner);
				ReturnDialog.setVisible(true);
			}
			
		});
		cMenu.add(processReturn);
		
		JMenuItem checkOverdue = new JMenuItem("Check Overdue");
		checkOverdue.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Check overdue Button Pressed");
				JDialog CheckOverdueDialog = new CheckOverdueDialog(owner);
				CheckOverdueDialog.setVisible(true);
			}
			
		});
		cMenu.add(checkOverdue);
		
		this.add(cMenu);
		
	}
	private void addLibrarianMenuItem(){
		JMenu lMenu = new JMenu("Librarian");
		
		JMenuItem addBook = new JMenuItem("Add new book...");
		addBook.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Add book button Pressed");
				
				JDialog AddBookDialog = new AddBookDialog(owner);
				AddBookDialog.setVisible(true);
				
				
			}
			
		});
		lMenu.add(addBook);
		
		JMenuItem checkOutReport = new JMenuItem("Generate checked-out items report");
		checkOutReport.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Check-Out report Button Pressed");
				JDialog CheckOutReportDialog = new CheckOutReportDialog(owner);
				CheckOutReportDialog.setVisible(true);
			}
			
		});
		lMenu.add(checkOutReport);
		
		JMenuItem mostPopular = new JMenuItem("Most popular books...");
		mostPopular.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Process return button Pressed");
				JDialog MostPopularDialog = new MostPopularDialog(owner);
				MostPopularDialog.setVisible(true);
			}
			
		});
		lMenu.add(mostPopular);
		
		this.add(lMenu);
		
	}

}
