package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	public TopMenuBar (){
		super();
		addFileMenuItem();
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
		
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.setEnabled(true);
		exitItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Insert action for exit
				System.exit(0);
				
			}
			
		});
		
	}
	

}
