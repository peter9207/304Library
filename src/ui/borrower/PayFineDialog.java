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
import javax.swing.WindowConstants;

import main.MainLibrary;

import ui.NotificationDialog;

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
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		
		initComponents();
	
		this.setSize(new Dimension(200,300));
		pack();
	}
	
	private void initComponents(){
		
		JLabel bidLabel= new JLabel("BID: ");
		this.add(bidLabel);
		
		final JTextField bid = new JTextField();
		this.add(bid);
		bid.setPreferredSize(new Dimension(80, 30));
		
		JLabel amtLabel= new JLabel("Amount: ");
		this.add(amtLabel);
		
		final JTextField text = new JTextField();
		this.add(text);
		text.setPreferredSize(new Dimension(80, 30));
		
		JButton payButton = new JButton("Pay");
		payButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String s = text.getText();
				int fine;
				int bidInt = 0;
				
				try {
					bidInt = Integer.parseInt(bid.getText());
				} catch (NumberFormatException e1) {
					new NotificationDialog(owner, "ERROR!" , "BID is not entered correctly. It should be a number.");
				}
				
				try {
					fine = Integer.parseInt(s);
					if(fine%5!=0){
						new NotificationDialog(owner,"ERROR!","Please enter an amount in multiples of 5. We do not allow for payments less than one fine amount.");
						return;
					}
					MainLibrary.databaseHandler.payFine(bidInt,fine);
				} catch (NumberFormatException e) {
					new NotificationDialog(owner,"ERROR!","The amount to pay should be in numerals.");
				}
				
				
			}
			
		});
		this.add(payButton);
		
	}
}
