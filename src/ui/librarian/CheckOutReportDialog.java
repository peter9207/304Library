package ui.librarian;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class CheckOutReportDialog extends JDialog{

	/**
	 * Default Generated serial ID
	 */
	private static final long serialVersionUID = 739901659522673107L;

	
	
	public CheckOutReportDialog(Frame owner){
		super(owner,true);
		this.setLayout(new FlowLayout(FlowLayout.LEADING));
		this.setTitle("Report");
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		this.setSize(new Dimension(400, 300));
		initComponents();
	}
	
	private void initComponents(){
		
		
		
	}
}
