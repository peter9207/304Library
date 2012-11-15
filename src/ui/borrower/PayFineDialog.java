package ui.borrower;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class PayFineDialog extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 232798071628847902L;

	public PayFineDialog(Frame f){
		super(f, true);
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setTitle("Pay Fine");
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		initComponents();
	
		this.setSize(new Dimension(200,300));
		pack();
	}
	
	private void initComponents(){
		
		JLabel amtLabel= new JLabel("Amount: ");
		this.add(amtLabel);
		
		JTextField text = new JTextField();
		this.add(text);
		text.setPreferredSize(new Dimension(80, 30));
		
		JButton payButton = new JButton("pay");
		this.add(payButton);
		
	}
}
