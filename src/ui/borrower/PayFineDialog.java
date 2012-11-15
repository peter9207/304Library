package ui.borrower;

import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JFrame;

public class PayFineDialog extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 232798071628847902L;

	public PayFineDialog(Frame f){
		super(f);
		this.setTitle("Pay Fine");
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		
	}
}
