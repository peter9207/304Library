package main;

import java.io.IOException;
import java.io.PrintStream;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.table.DefaultTableModel;

import ui.ErrorDialog;

public class DatabaseHandler {
	private static OracleConnection con;
	public DatabaseHandler(){

		con = new OracleConnection();
	}
	public OracleConnection getConnection(){
		return con;
	}
	public void addBook(int callNumber, int isbn, String title, String mainAuthor, String publisher, int year, boolean copy){
		PreparedStatement ps;
		try
		{
			if (!copy) {
				ps = con.con
						.prepareStatement("INSERT INTO book VALUES (?,?,?,?,?,?)");
				ps.setInt(1, callNumber);
				ps.setInt(2, isbn);
				ps.setString(3, title);
				ps.setString(4, mainAuthor);
				ps.setString(5, publisher);
				ps.setInt(6, year);
				ps.executeUpdate();
				// commit work 
				con.con.commit();
				ps.close();
			}
			else
			{
				ps = con.con
						.prepareStatement("INSERT INTO BookCopy VALUES (?,copyNo_sequence.nextval,?)");
				ps.setInt(1, callNumber);
				ps.setString(2, "in");
				ps.executeUpdate();
				// commit work 
				con.con.commit();
				ps.close();
			}
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
			try 
			{
				// undo the insert
				con.con.rollback();	
			}
			catch (SQLException ex2)
			{
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}
	}
	public Vector<Object[]> showBooks(String searchTerms, String searchParameters){
		int callNumber, isbn, year;
		String title, mainAuthor, publisher;
		Statement  stmt;
		ResultSet  rs;
		Vector<Object[]> books = new Vector<Object[]>();
		int counter = 0;

		try
		{
			stmt = con.con.createStatement();

			if (searchTerms.isEmpty()){
				rs = stmt.executeQuery("select * from book b, (select callNumber, count(copyNo) as \"IN\" FROM bookcopy GROUP BY callNumber) c where b.callNumber=c.callNumber");
			}
			else rs = stmt.executeQuery("SELECT * FROM book b,bookcopy bc WHERE b.UPPER("+searchParameters.toUpperCase()+") LIKE " +"'%"+searchTerms.toUpperCase().trim()+"%' AND b.callNumber = bc.callNumber");

			// get info on ResultSet
			ResultSetMetaData rsmd = rs.getMetaData();

			// get number of columns
			int numCols = rsmd.getColumnCount();
			//display column names;
			for (int i = 0; i < numCols; i++)
			{
				// get column name and print it

				System.out.printf("%-15.15s", rsmd.getColumnName(i+1));
			}
			System.out.println(" ");

			while(rs.next())
			{
				// for display purposes get everything from Oracle 
				// as a string

				// simplified output formatting; truncation may occur

				callNumber = rs.getInt("CALLNUMBER");
				isbn = rs.getInt("ISBN");
				title = rs.getString("title");
				mainAuthor = rs.getString("mainAuthor");
				publisher = rs.getString("publisher");		   
				year = rs.getInt("year");

				System.out.printf("%-15.15s", callNumber);
				System.out.printf("%-15.15s", isbn);
				System.out.printf("%-15.15s", title);
				System.out.printf("%-15.15s", mainAuthor);
				System.out.printf("%-15.15s", publisher);
				System.out.printf("%-15.15s\n", year);

				books.add(counter, new Object[6]);

				books.get(counter)[0]=callNumber;
				books.get(counter)[1]=isbn;
				books.get(counter)[2]=title;
				books.get(counter)[3]=mainAuthor;
				books.get(counter)[4]=publisher;
				books.get(counter)[5]=year;

				counter++;




				//		      bphone = rs.getString("branch_phone");
				//		      if (rs.wasNull())
				//		      {
				//		    	  System.out.printf("%-15.15s\n", " ");
				//	              }
				//		      else
				//		      {
				//		    	  System.out.printf("%-15.15s\n", bphone);
				//		      }      
			}

			// close the statement; 
			// the ResultSet will also be closed
			stmt.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
		}
		for (int i=0; i<books.size();i++){
			System.out.println(i+" - "+books.get(i)[0]+"\n");
		}
		return books;	
	}

	public void addBorrower(String password, String name,
			String address, String email, int sinOrStNo, Date sqlDate,
			String type) {
		PreparedStatement ps;
		try
		{
			ps = con.con.prepareStatement("INSERT INTO borrower VALUES (bid_sequence.nextval,?,?,?,?,?,?,?)");


			ps.setString(1, password);
			ps.setString(2, name);
			if(address.length()==0){
				ps.setString(3, null);
			}
			else
			{
				ps.setString(3, address);
			}
			if(email.length()==0){
				ps.setString(4, null);
			}
			else
			{
				ps.setString(4, email);
			}
			ps.setInt(5, sinOrStNo);
			ps.setDate(6, sqlDate);
			ps.setString(7, type);

			ps.executeUpdate();

			// commit work 
			con.con.commit();

			ps.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
			try 
			{
				// undo the insert
				con.con.rollback();	
			}
			catch (SQLException ex2)
			{
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}
	}
	public void placeHold(int bid, int callNumber) {

		PreparedStatement ps;
		try
		{
			ps = con.con.prepareStatement("INSERT INTO HoldRequest VALUES (hid_sequence.nextval,?,?,?)");
			java.util.Date today = new java.util.Date();
			java.sql.Date todaysql = new java.sql.Date(today.getTime());

			ps.setInt(1, bid);
			ps.setInt(2, callNumber);
			ps.setDate(3, todaysql);
			
			

			ps.executeUpdate();

			// commit work 
			con.con.commit();

			System.out.println("hold request placed");
			ps.close();

		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
			try 
			{
				// undo the insert
				con.con.rollback();	
			}
			catch (SQLException ex2)
			{
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}
	}
	public void checkOut(Vector<Integer> callNumbers, int bid) {
		PreparedStatement ps = null;
		Statement stmt;
		ResultSet rs,rs2,rs3;
		int copyNumber;
		try
		{
			stmt = con.con.createStatement();

			rs = stmt.executeQuery("select bid from fine f,borrowing b where b.borid = f.borid AND bid="+bid);


			// get info on ResultSet
			if (!rs.next()){
				rs2 = stmt.executeQuery("select bookTimeLimit from borrower b, borrower_type bt where bt.type LIKE b.type AND b.bid = "+bid);
				if (rs2.next()) {
					long limit = rs2.getLong("bookTimeLimit");
					for (int i = 0; i < callNumbers.size(); i++) {
						System.out.println(i);
						rs3 = stmt
								.executeQuery("select * from bookcopy where callNumber = "
										+ callNumbers.get(i).toString()
										+ " AND status LIKE 'in'");
						System.out.println("query ran");
						if (rs3.next()) {
							copyNumber = rs3.getInt("copyNo");
							System.out.println(copyNumber);
							ps = con.con
									.prepareStatement("INSERT INTO borrowing VALUES (borid_sequence.nextval,?,?,?,?,?)");
							java.util.Date today = new java.util.Date();
							java.sql.Date todaysql2 = new java.sql.Date(
									today.getTime());
							java.sql.Date inDate2 = new java.sql.Date(
									today.getTime() + limit);
							ps.setInt(1, bid);
							ps.setInt(2, callNumbers.get(i));
							System.out.println(callNumbers.get(i));

							System.out.println(copyNumber);
							ps.setInt(3, copyNumber);
							ps.setDate(4, todaysql2);
							ps.setDate(5, inDate2);
							System.out.println(inDate2.toString());
							ps.executeUpdate();
							ps.close();

							System.out.println(callNumbers.get(i).toString()
									+ " checked out!\n");
							ps = con.con.prepareStatement("UPDATE bookcopy SET status='out' WHERE callNumber = ? AND copyNo = ?");
							ps.setInt(1, callNumbers.get(i));
							ps.setInt(2, copyNumber);
							ps.executeUpdate();
							ps.close();

							System.out.println("executed update");

						} else {
							ErrorDialog error = new ErrorDialog(null,"No more copies available! Unable to check out.");
						}
					}
					con.con.commit();
					
					ps.close();
				}
				else{
					System.out.println("Borrower does not exist.");
				}
			}
		} catch (SQLException e) {
			ErrorDialog error = new ErrorDialog(null, "Something went wrong somewhere in the Database Handler, method: check out. Damn.");
			e.printStackTrace();

		}
	}
	public void returnBook(int callNumber, int copyNumber) {
		PreparedStatement ps;
		Statement stmt = null;
		ResultSet rs;
		
		try {
			ps = con.con.prepareStatement("DELETE FROM borrowing WHERE callNumber = ? AND copyNo = ?");
			ps.setInt(1, callNumber);
			ps.setInt(2, copyNumber);
			ps.executeUpdate();
			ps.close();
			stmt = con.con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM holdrequest WHERE callNumber = "+callNumber);
			if(rs.next()){
				
				ps = con.con.prepareStatement("UPDATE bookcopy SET status = 'on-hold' where callNumber = ? AND copyNo = ?");
				ps.setInt(1, callNumber);
				ps.setInt(2, copyNumber);
				ps.executeUpdate();
				ps.close();
System.out.println("asd");
				ps = con.con.prepareStatement("DELETE FROM holdrequest WHERE hid = 2");
				System.out.println(rs.getInt("hid"));
				ps.executeUpdate();
				ps.close();

			}
			else
			{
			ps = con.con.prepareStatement("UPDATE bookcopy SET status = 'in' where callNumber = ? AND copyNo = ?");
			ps.setInt(1, callNumber);
			ps.setInt(2, copyNumber);
			ps.executeUpdate();
			}
			
			con.con.commit();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}



