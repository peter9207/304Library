package ui;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
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
	
	
	private static final int HEIGHT = 800;
	private static final int WIDTH = 600;
	public MainFrame (){
		super(FRAME_TITLE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container container = getContentPane();
		container.setPreferredSize(new Dimension(HEIGHT, WIDTH));

		setJMenuBar(new TopMenuBar(this));
		
	}
	

}
