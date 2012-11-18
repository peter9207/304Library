package ui.clerk;

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

import main.MainLibrary;

public class ReturnDialog extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4041902911499010716L;

	private static final int TEXT_BOX_HEIGHT = 30;
	private static final int TEXT_BOX_WIDTH = 80;
	
	public ReturnDialog(Frame f){
		super(f,true);
		
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setTitle("Returns");
		this.setSize(300, 200);
		initComponents();
		
	}
	
	private void initComponents(){
		
		this.setLayout(new FlowLayout(FlowLayout.LEADING));
		
		JLabel label = new JLabel("Call Number");
		this.add(label);
		
		final JTextField text = new JTextField();
		text.setPreferredSize(new Dimension(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT));
		this.add(text);
		
		JButton button = new JButton("Return");
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				MainLibrary.databaseHandler.returnBook(Integer.parseInt(text.getText().toString()));
			}
			
		});
		this.add(button);
	}
}
