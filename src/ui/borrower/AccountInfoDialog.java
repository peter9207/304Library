package ui.borrower;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ui.ErrorDialog;

import main.MainLibrary;
import main.OracleConnection;

public class AccountInfoDialog extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7718138370777865661L;

	private Frame owner;
	private JPanel information;
	public AccountInfoDialog(Frame owner) {
		super(owner,true);
		this.owner = owner;
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setTitle("Account Information");
		this.setSize(new Dimension(600,300));
		initComponents();
	}
	
	private void initComponents(){
		JPanel inputPanel = new JPanel();
		
		JLabel sinOrSt = new JLabel();
		sinOrSt.setText("BorrID: ");
		inputPanel.add(sinOrSt);
		
		final JTextField inputField = new JTextField();
		inputField.setPreferredSize(new Dimension(80, 25));
		inputPanel.add(inputField);
		
		JButton displayInfo = new JButton();
		displayInfo.setText("Get Info");
		inputPanel.add(displayInfo);
		displayInfo.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				displayInfo(inputField.getText());
				
			}
			
		});
		this.add(inputPanel, BorderLayout.NORTH);
		
		information = new JPanel();
		this.add(information, BorderLayout.CENTER);
		information.setLayout(new GridLayout(7,2,20,20));
		
	}
	
	private void displayInfo(String bid){
		//clear all existing children
		information.removeAll();
		information.setSize(600, 200);
		OracleConnection conn = MainLibrary.databaseHandler.getConnection();
		PreparedStatement ps;
		ResultSet rs;
		
		int borrid = 0 ;
		String name = "" ;
		String addr = "" ;
		String email = "";
		int sNo = 0 ;
		Date expDate = null;
		String type = "";
		
		try {
			
			String query = 
					"SELECT * " +
					"FROM borrowing " +
					"WHERE bid =?";
			ps = conn.con.prepareStatement("Select * from borrowing where bid=?");
			ps.setInt(1, Integer.parseInt(bid));
			ps.execute();
			
			System.out.println("QUERY FINISHED");
			rs = ps.getResultSet();
			
			while (rs.next()) {
				borrid = rs.getInt("BID");
				name = rs.getString("NAME");
				addr = rs.getString("ADDRESS");
				email = rs.getString("EMAILADDRESS");
				sNo = rs.getInt("SINORSTNO");
				expDate = rs.getDate("EXPIRYDATE");
				type = rs.getString("TYPE");
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			new ErrorDialog(owner, "Please enter a valid borrower ID");
		} catch (NumberFormatException e){
			new ErrorDialog(owner, "Please enter a correct number");
		}
		
		System.out.println(borrid);
		
		
		information.add(new JLabel("BorrID: "));
		information.add(new JLabel(borrid+""));
		information.add(new JLabel("Name "));
		information.add(new JLabel(name));
		information.add(new JLabel("Address"));
		information.add(new JLabel(addr));
		information.add(new JLabel("Sin Or St #"));
		information.add(new JLabel(sNo+""));
		information.add(new JLabel("Expiration Date"));
		information.add(new JLabel(expDate.toString()));
		information.add(new JLabel("Type"));
		information.add(new JLabel(type));
		
		information.revalidate();
		
	}

}
