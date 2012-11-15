package ui.borrower;

import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JFrame;

public class AccountInfoDialog extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7718138370777865661L;

	public AccountInfoDialog(Frame owner){
		super(owner,true);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setTitle("Account Information");
		initComponents();
		
	}
	
	private void initComponents(){
		
	}

}
