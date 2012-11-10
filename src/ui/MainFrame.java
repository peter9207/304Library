package ui;

import java.awt.Container;

import javax.swing.*;
/**
 * 
 * @author peter9207
 *
 * Class for the main window of the library program
 */
public class MainFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String FRAME_TITLE = "Library Console";
	
	public MainFrame (){
		super(FRAME_TITLE);
		Container container = getContentPane();
		

		setJMenuBar(new TopMenuBar());
		
		
	}

}
