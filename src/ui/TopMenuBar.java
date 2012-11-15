package ui;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
/**
 * 
 * @author peter9207
 *
 *
 * class for the Menu Bar at the top of the window,
 * all the menu item configuration should be added here
 */
public class TopMenuBar extends JMenuBar {
	private Frame owner;
	
	public TopMenuBar (Frame f){
		super();
		owner = f;
		addFileMenuItem();
		addLibraryMenuItem();
		addBorrowerMenuItem();
		
	}
	private void addFileMenuItem(){
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic('F');
		
		JMenuItem aboutItem = new JMenuItem("About...");
		aboutItem.setEnabled(true);
		aboutItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("[INFO] About action Clicked");
				// TODO Insert action for about button item here
				
				
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
		
		JMenuItem addItem = new JMenuItem("Add...");
		addItem.setEnabled(true);
		addItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("Add Item button Pressed!");
				
			}
			
		});
		libMenu.add(addItem);
		
		
		JMenuItem removeItem = new JMenuItem("Remove...");
		removeItem.setEnabled(true);
		removeItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Remove button pressed");
				
			}
			
		});
		libMenu.add(removeItem);
		
		JMenuItem reportItem = new JMenuItem("Generate Report");
		reportItem.setEnabled(true);
		reportItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO generate report of all checked out items
				
				
			}
			
		});
		libMenu.add(reportItem);
		this.add(libMenu);
	}
	
	private void addBorrowerMenuItem(){
		JMenu bMenu = new JMenu("Borrower");
		
		JMenuItem search = new JMenuItem("Search");
		search.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
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
				// TODO Auto-generated method stub
				System.out.println("Account Info Button Pressed");
			}
			
		});
		bMenu.add(accountInfo);
		
		JMenuItem hold = new JMenuItem("Place Hold");
		hold.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Place Hold Button Pressed");
			}
			
		});
		bMenu.add(hold);
		
		JMenuItem payFine = new JMenuItem("Pay Fine");
		payFine.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Pay Fine Button Pressed");
			}
			
		});
		bMenu.add(payFine);
		
		this.add(bMenu);
		
	}
	

}
