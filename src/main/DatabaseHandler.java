package main;

import java.io.IOException;
import java.io.PrintStream;
import java.sql.*;
import java.sql.Date;
import java.util.*;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.table.DefaultTableModel;

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
		Object[] book = new Object[6];
		int counter = 0;

		try
		{
			stmt = con.con.createStatement();

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

	public void addBorrower(int bid, String password, String name,
			String address, String email, int sinOrStNo, Date sqlDate,
			String type) {
		PreparedStatement ps;
		try
		{
			ps = con.con.prepareStatement("INSERT INTO borrower VALUES (?,?,?,?,?,?,?,?)");

			ps.setInt(1, bid);
			ps.setString(2, password);
			ps.setString(3, name);
			if(address.length()==0){
				ps.setString(4, null);
			}
			else
			{
				ps.setString(4, address);
			}
			if(email.length()==0){
				ps.setString(5, null);
			}
			else
			{
				ps.setString(5, email);
			}
			ps.setInt(6, sinOrStNo);
			ps.setDate(7, sqlDate);
			ps.setString(8, type);

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
//		PreparedStatement ps;
//		Statement stmt;
//		ResultSet rs;
//		try
//		{
//			stmt = MainLibrary.con.con.createStatement();
//
//			rs = stmt.executeQuery("select bid from fine f,borrowing b where b.borid = f.borid AND bid="+bid);
//
//			// get info on ResultSet
//			ResultSetMetaData rsmd = rs.getMetaData();
//			if (!rs.next()){
//				rs = stmt.executeQuery("select copyNo from borrowing where callNumber ="+callNumbers.get(i));
//				ps = MainLibrary.con.con.prepareStatement("INSERT INTO borrowing VALUES (borid_sequence.nextval,?,?,?.?,?)");
//				java.util.Date today = new java.util.Date();
//				java.sql.Date todaysql = new java.sql.Date(today.getTime());
//
//				ps.setInt(1, bid);
//				ps.setInt(2, callNumber);
//				ps.setInt(3, copyNumber);
//				ps.setDate(3, todaysql);
//
//
//				ps.executeUpdate();
//
//				// commit work 
//				MainLibrary.con.con.commit();
//
//				System.out.println("hold request placed");
//				ps.close();
//			}
//		}
	}
}

