package ui.borrower;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AccountInfoDialog extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7718138370777865661L;

	public AccountInfoDialog(Frame owner){
		super(owner,true);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setLayout(new GridLayout(0,1));
		this.setTitle("Account Information");
		this.setSize(new Dimension(600,300));
		initComponents();
		
	}
	
	private void initComponents(){
		JPanel inputPanel = new JPanel();
		
		JLabel sinOrSt = new JLabel();
		sinOrSt.setText("SIN or St.No: ");
		inputPanel.add(sinOrSt);
		
		JTextField inputField = new JTextField();
		inputField.setPreferredSize(new Dimension(80, 25));
		inputPanel.add(inputField);
		
		JButton display = new JButton();
		display.setText("Get Info");
		inputPanel.add(display);
		
		this.add(inputPanel);
		
		JPanel Information = new JPanel();
	}

}
