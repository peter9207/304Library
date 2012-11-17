package ui.clerk;

import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JFrame;

public class CheckOverdueDialog extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7718138370777865661L;

	public CheckOverdueDialog(Frame owner){
		super(owner,true);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setTitle("Overdue Books");
		initComponents();
		
	}
	
	private void initComponents(){
		
	}

}
