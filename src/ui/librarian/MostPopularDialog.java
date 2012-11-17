package ui.librarian;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MostPopularDialog extends JDialog{

	/**
	 * Default Generated serial ID
	 */
	private static final long serialVersionUID = 739901659522673107L;

	private static final int TEXT_BOX_HEIGHT = 30;
	private static final int TEXT_BOX_WIDTH = 80;
	
	
	public MostPopularDialog(Frame owner){
		super(owner,true);
		this.setLayout(new GridLayout(0,1));
		this.setTitle("Popularity Report");
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		this.setSize(new Dimension(400, 300));
		initComponents();
	}
	
	private void initComponents(){
		//Input Panel
		
		JPanel input = new JPanel();
		input.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel yearLabel = new JLabel("Year: ");
		input.add(yearLabel);
		
		JTextField yearField = new JTextField();
		input.add(yearField);
		yearField.setPreferredSize(new Dimension(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT));
		
		JLabel numLabel = new JLabel("Top number of books: ");
		input.add(numLabel);
		
		JTextField numField = new JTextField();
		input.add(numField);
		numField.setPreferredSize(new Dimension(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT));
		
		JButton searchButton = new JButton();
		input.add(searchButton);
		searchButton.setText("Generate Report");
		this.add(input);
		
		//ReportPanel
		
		JPanel report = new JPanel();
		

		
		
	}
}
