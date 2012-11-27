package ui.clerk;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import ui.NotificationDialog;

import main.MainLibrary;

public class ReturnDialog extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4041902911499010716L;

	private static final int TEXT_BOX_HEIGHT = 25;
	private static final int TEXT_BOX_WIDTH = 120;
	
	public ReturnDialog(Frame f){
		super(f,true);
		
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		this.setTitle("Returns");
		this.setSize(300, 200);
		initComponents();
		
	}
	
	private void initComponents(){
		
		this.setLayout(new GridLayout(0,1));
		
		JPanel panel1 = new JPanel();
		panel1.setLayout(new FlowLayout(FlowLayout.LEADING));
		JLabel label = new JLabel("Call Number");
		panel1.add(label);
		
		final JTextField text = new JTextField();
		text.setPreferredSize(new Dimension(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT));
		panel1.add(text);
		this.add(panel1);

		JPanel panel2 = new JPanel();
		panel2.setLayout(new FlowLayout(FlowLayout.LEADING));
		JLabel cpn = new JLabel("Copy Number");
		panel2.add(cpn);
		
		final JTextField cpn2 = new JTextField();
		cpn2.setPreferredSize(new Dimension(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT));
		panel2.add(cpn2);
		this.add(panel2);
		JButton button = new JButton("Return");
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					MainLibrary.databaseHandler.returnBook(Integer.parseInt(text.getText()),Integer.parseInt(cpn2.getText()));
					text.setText("");
					cpn2.setText("");
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					new NotificationDialog (null, "ERROR!", "Please ensure the call number and copy number provided is numerical and without spaces.");
				}
			}
			
		});
		this.add(button);
	}
}
