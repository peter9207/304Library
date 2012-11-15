package ui.borrower;

import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JFrame;

public class PlaceHoldDialog extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4041902911499010716L;

	public PlaceHoldDialog(Frame f){
		super(f);
		
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setTitle("Place Hold");
	}
}
