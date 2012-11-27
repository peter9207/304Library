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
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import main.MainLibrary;

public class PlaceHoldDialog extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4041902911499010716L;

	private static final int TEXT_BOX_HEIGHT = 25;
	private static final int TEXT_BOX_WIDTH = 80;
	
	public PlaceHoldDialog(Frame f){
		super(f,true);
		
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		this.setTitle("Place Hold");
		this.setSize(300, 125);
		initComponents();
		
	}
	
	private void initComponents(){
		
		this.setLayout(new FlowLayout(FlowLayout.LEADING));
		
		JPanel input1 = new JPanel();
		
		JLabel bidLabel = new JLabel("BID");
		input1.add(bidLabel);
		
		final JTextField bidField = new JTextField();
		bidField.setPreferredSize(new Dimension(240, TEXT_BOX_HEIGHT));
		input1.add(bidField);
		
		JPanel input2 = new JPanel();
		
		JLabel callNumberLabel = new JLabel("Call Number");
		input2.add(callNumberLabel);
		
		final JTextField callNumberField = new JTextField();
		callNumberField.setPreferredSize(new Dimension(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT));
		input2.add(callNumberField);
		
		this.add(input1);
		this.add(input2);
		
		JButton button = new JButton("Place Hold");
		this.add(button);
		
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!bidField.getText().toString().isEmpty() && !callNumberField.getText().toString().isEmpty()) {
					int bid;
					int callNumber;
					bid = Integer.parseInt(bidField.getText().toString());
					callNumber = Integer.parseInt(callNumberField.getText()
							.toString());
					MainLibrary.databaseHandler.placeHold(bid, callNumber);
					
					callNumberField.setText("");
				}
				else
				{
					
				}
			}
			
		});
	}
}
