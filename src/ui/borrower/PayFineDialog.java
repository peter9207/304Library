package ui.borrower;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import ui.ErrorDialog;

public class PayFineDialog extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 232798071628847902L;

	private Frame owner;
	public PayFineDialog(Frame f){
		super(f, true);
		owner = f;
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
		
		final JTextField text = new JTextField();
		this.add(text);
		text.setPreferredSize(new Dimension(80, 30));
		
		JButton payButton = new JButton("pay");
		payButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String s = text.getText();
				double fine;
				
				try {
					fine = Double.parseDouble(s);
				} catch (NumberFormatException e) {
					new ErrorDialog(owner,"Please insert a number");
				}
				
				
			}
			
		});
		this.add(payButton);
		
	}
}
