package ui.borrower;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class PlaceHoldDialog extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4041902911499010716L;

	private static final int TEXT_BOX_HEIGHT = 30;
	private static final int TEXT_BOX_WIDTH = 80;
	
	public PlaceHoldDialog(Frame f){
		super(f,true);
		
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setTitle("Place Hold");
		this.setSize(300, 200);
		initComponents();
		
	}
	
	private void initComponents(){
		
		this.setLayout(new FlowLayout(FlowLayout.LEADING));
		
		JLabel label = new JLabel("Call Number");
		this.add(label);
		
		JTextField text = new JTextField();
		text.setPreferredSize(new Dimension(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT));
		this.add(text);
		
		JButton button = new JButton("Place Hold");
		this.add(button);
	}
}
