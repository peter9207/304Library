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
	public DatabaseHandler(){

	}
	public void addBook(int callNumber, int isbn, String title, String mainAuthor, String publisher, int year, boolean copy){
		PreparedStatement ps;
		try
		{
			if (!copy) {
				ps = MainLibrary.con.con
						.prepareStatement("INSERT INTO book VALUES (?,?,?,?,?,?)");
				ps.setInt(1, callNumber);
				ps.setInt(2, isbn);
				ps.setString(3, title);
				ps.setString(4, mainAuthor);
				ps.setString(5, publisher);
				ps.setInt(6, year);
				ps.executeUpdate();
				// commit work 
				MainLibrary.con.con.commit();
				ps.close();
			}
			else
			{
				ps = MainLibrary.con.con
						.prepareStatement("INSERT INTO BookCopy VALUES (?,copyNo_sequence.nextval,?)");
				ps.setInt(1, callNumber);
				ps.setString(2, "in");
				ps.executeUpdate();
				// commit work 
				MainLibrary.con.con.commit();
				ps.close();
			}
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
			try 
			{
				// undo the insert
				MainLibrary.con.con.rollback();	
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
		Vector<Object[]> books = new Vector<>();
		Object[] book = new Object[6];
		int counter = 0;

		try
		{
			stmt = MainLibrary.con.con.createStatement();

			if (searchTerms.isEmpty()){
				rs = stmt.executeQuery("SELECT * FROM book");
			}
			else rs = stmt.executeQuery("SELECT * FROM book WHERE UPPER("+searchParameters.toUpperCase()+") LIKE " +"'%"+searchTerms.toUpperCase().trim()+"%'");

			// get info on ResultSet
			ResultSetMetaData rsmd = rs.getMetaData();

			// get number of columns
			int numCols = rsmd.getColumnCount();

			System.out.println(" ");
			String[] col = new String[numCols];
			String format = "%1$-15s|%2$-15s|%3$-15s|%4$-15s|%5$-15s|%5$-15s|";
			String format2 = "|%1$-30s|%2$-40s|%3$-40s|%4$-40s|%5$-40s|%3$-20s|";
			//display column names;
			for (int i = 0; i < numCols; i++)
			{
				// get column name and print it

				System.out.printf("%-15.15s", rsmd.getColumnName(i+1));
			}
			//		  books.addElement(String.format(format, col[0],col[1],col[2],col[3],col[4],col[5]));

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
			ps = MainLibrary.con.con.prepareStatement("INSERT INTO borrower VALUES (bid_sequence.nextval,?,?,?,?,?,?,?)");

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
			MainLibrary.con.con.commit();

			ps.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
			try 
			{
				// undo the insert
				MainLibrary.con.con.rollback();	
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
			ps = MainLibrary.con.con.prepareStatement("INSERT INTO HoldRequest VALUES (hid_sequence.nextval,?,?,?)");
			java.util.Date today = new java.util.Date();
			java.sql.Date todaysql = new java.sql.Date(today.getTime());

			ps.setInt(1, bid);
			ps.setInt(2, callNumber);
			ps.setDate(3, todaysql);


			ps.executeUpdate();

			// commit work 
			MainLibrary.con.con.commit();

			System.out.println("hold request placed");
			ps.close();

		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
			try 
			{
				// undo the insert
				MainLibrary.con.con.rollback();	
			}
			catch (SQLException ex2)
			{
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}
	}
	public void checkOut(Vector<Integer> callNumbers, int bid) {
		PreparedStatement ps;
		Statement stmt;
		ResultSet rs,rs2,rs3;
		try
		{
			stmt = MainLibrary.con.con.createStatement();

			rs = stmt.executeQuery("select bid from fine f,borrowing b where b.borid = f.borid AND bid="+bid);


			// get info on ResultSet
			if (!rs.next()){
				System.out.println("works here fine check");

				rs2 = stmt.executeQuery("select bookTimeLimit from borrower b, borrower_type bt where bt.type LIKE b.type AND b.bid = "+bid);
				if (rs2.next()) {
					long limit = rs2.getLong("bookTimeLimit");
					System.out.println(limit);
					for (int i = 0; i < callNumbers.size(); i++) {
						rs3 = stmt
								.executeQuery("select * from bookcopy where callNumber ="
										+ callNumbers.get(i)
										+ "AND status LIKE 'in'");
						if (rs3.next()) {
							System.out.println("works herecopyno");
							int copyNumber = rs3.getInt("copyNo");
							System.out.println(copyNumber);
							ps = MainLibrary.con.con
									.prepareStatement("INSERT INTO borrowing VALUES (borid_sequence.nextval,?,?,?,?,?)");
							java.util.Date today = new java.util.Date();
							java.sql.Date todaysql2 = new java.sql.Date(
									today.getTime());
							java.sql.Date inDate2 = new java.sql.Date(
									today.getTime() + limit);
							ps.setInt(1, bid);
							ps.setInt(2, callNumbers.get(i));
							System.out.println(callNumbers.get(i));
							ps.setInt(3, copyNumber);
							ps.setDate(4, todaysql2);
							ps.setDate(5, inDate2);
							System.out.println(inDate2.toString());
							ps.executeUpdate();
							System.out.println(callNumbers.get(i).toString()
									+ " checked out!\n");
							ps = MainLibrary.con.con.prepareStatement("UPDATE bookcopy SET status='out' where callNumber=? AND copyNo = ?");
							ps.setInt(1, callNumbers.get(i));
							ps.setInt(2, copyNumber);
							ps.executeUpdate();
							MainLibrary.con.con.commit();
							
							ps.close();
						} else {
							System.out.println("NO COPIES from bookcopy");
						}
					}
				}
				else{
					System.out.println("Borrower does not exist.");
				}
			}
		} catch (SQLException e) {
			ErrorDialog error = new ErrorDialog("Something went wrong somewhere in the Database Handler, method: check out. Damn.");
			e.printStackTrace();

		}
	}
	public void returnBook(int parseInt) {
		// TODO Auto-generated method stub
		
	}

}



