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

import ui.NotificationDialog;

public class DatabaseHandler {
	private static OracleConnection con;
	public DatabaseHandler(){

		con = new OracleConnection();
	}
	public void addBook(int callNumber, int isbn, String title, String mainAuthor, String publisher, int year, int copy, Vector<String> subVector, Vector<String> authorVector){
		PreparedStatement ps;
		try
		{
				ps = con.con
						.prepareStatement("INSERT INTO book VALUES (?,?,?,?,?,?)");
				ps.setInt(1, callNumber);
				ps.setInt(2, isbn);
				ps.setString(3, title);
				ps.setString(4, mainAuthor);
				ps.setString(5, publisher);
				ps.setInt(6, year);
				try {
					ps.executeUpdate();
				} catch (Exception e1) {
					new NotificationDialog(null, "ERROR!", "Original already exists. 1 or more copies will be created.");
				}
				
				ps = con.con.prepareStatement("INSERT INTO HASSUBJECT VALUES (?,?) ");
				
				if (!subVector.isEmpty()) {
					for (int i = 0; i < subVector.size(); i++) {
						ps.setInt(1, callNumber);
						ps.setString(2, subVector.get(i));
						try {
							ps.executeUpdate();
						} catch (Exception e) {
						}
					}
				}
				ps = con.con.prepareStatement("INSERT INTO HASAUTHOR VALUES (?,?) ");

				if (!authorVector.isEmpty()) {
					for (int i = 0; i < authorVector.size(); i++) {
						ps.setInt(1, callNumber);
						ps.setString(2, authorVector.get(i));
						try {
							ps.executeUpdate();
						} catch (Exception e) {

						}
					}
				}
				// commit work 
				con.con.commit();
				ps.close();
				
					for (int i = 0; i < copy; i++) {
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
		int callNumber, isbn, year, in, out;
		String title, mainAuthor, publisher;
		Statement  stmt;
		ResultSet  rs;
		Vector<Object[]> books = new Vector<Object[]>();
		int counter = 0;
		String defaultQuery =
				"SELECT callNumber, isbn, title, mainAuthor, publisher, year, innum, outnum " +
						"FROM " +
						"book b, " +
						"(SELECT * from" +
						"(SELECT callNumber inCall, count(copyNo) innum FROM bookcopy WHERE status LIKE 'in' GROUP BY callNumber)c " +
						"FULL JOIN	" +
						"(select callNumber outCall, count(copyNo) outnum FROM bookcopy WHERE status LIKE 'out' GROUP BY callNumber)d " +
						"ON c.inCall = d.outCall)cd " +
						"WHERE b.callNumber = cd.inCall OR b.callNumber = cd.outCall";
		String searchableQuery =
				"SELECT callNumber, isbn, title, mainAuthor, publisher, year, innum, outnum " +
						"FROM " +
						"book b, " +
						"(SELECT * from" +
						"(SELECT callNumber inCall, count(copyNo) innum FROM bookcopy WHERE status LIKE 'in' GROUP BY callNumber)c " +
						"FULL JOIN	" +
						"(select callNumber outCall, count(copyNo) outnum FROM bookcopy WHERE status LIKE 'out' GROUP BY callNumber)d " +
						"ON c.inCall = d.outCall)cd " +
						"WHERE (b.callNumber = cd.inCall OR b.callNumber = cd.outCall) " +
						"AND UPPER(b."+searchParameters+") LIKE " +"'%"+searchTerms.toUpperCase().trim()+"%'";
		try
		{
			stmt = con.con.createStatement();

			if (searchTerms.isEmpty()){
				rs = stmt.executeQuery(defaultQuery);
			}
			else rs = stmt.executeQuery(searchableQuery);

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
				in = rs.getInt("innum");
				out = rs.getInt("outnum");

				System.out.printf("%-15.15s", callNumber);
				System.out.printf("%-15.15s", isbn);
				System.out.printf("%-15.15s", title);
				System.out.printf("%-15.15s", mainAuthor);
				System.out.printf("%-15.15s", publisher);
				System.out.printf("%-15.15s\n", year);
				System.out.printf("%-15.15s", in);
				System.out.printf("%-15.15s\n", out);

				books.add(counter, new Object[8]);

				books.get(counter)[0]=callNumber;
				books.get(counter)[1]=isbn;
				books.get(counter)[2]=title;
				books.get(counter)[3]=mainAuthor;
				books.get(counter)[4]=publisher;
				books.get(counter)[5]=year;
				books.get(counter)[6]=in;
				books.get(counter)[7]=out;

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

		int c = 0;
		try {
			ResultSet rs,rs1,rs2;
			Statement stmt = con.con.createStatement();
			rs = stmt.executeQuery("Select * from book where callNumber = "+callNumber);
			if(!rs.next()){
				new NotificationDialog(null, "ERROR!", "This book does not exist. Unable to place hold.");
				return;
			}
			rs1 = stmt.executeQuery("Select * from borrower where bid = "+bid);
			if(!rs1.next()){
				new NotificationDialog(null, "ERROR!", "This account does not exist. Check your BID again.");
				return;
			}
			rs2 = stmt.executeQuery("Select f.paidDate from fine f, borrowing b where b.borid = f.borid AND b.bid = "+bid+" AND f.paidDate IS NULL" );
			while(rs2.next()){
				c++;
			}
			System.out.println(c);
			if(c>0){
				new NotificationDialog(null, "ERROR!", "Your account is frozen because of unpaid fines. Please pay your fines before placing holds.");
				return;
			}
		} catch (SQLException e) {
		}
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

			rs = stmt.executeQuery("select bid from fine f,borrowing b where b.borid = f.borid AND b.bid="+bid+" AND f.paidDate IS NULL");
			int c=0;
			while(rs.next()){
				c++;
			}
			System.out.println(c);
			// get info on ResultSet
			if (c==0){
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
									.prepareStatement("INSERT INTO borrowing VALUES (borid_sequence.nextval,?,?,?,?,null)");
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
							new NotificationDialog(null, "ERROR!","No more copies available! Unable to check out.");
						}
					}
					con.con.commit();

					ps.close();
				}
				else{
					System.out.println("Borrower does not exist.");
				}
			}
			else
			{
				new NotificationDialog (null, "ERROR!", "This borrower has outstanding fines. Unable to process any checkouts.");
			}
		} catch (SQLException e) {
			NotificationDialog error = new NotificationDialog(null, "ERROR!", "Something went wrong somewhere in the Database Handler, method: check out. Damn.");
			e.printStackTrace();

		}
	}
	public void returnBook(int callNumber, int copyNumber) {
		PreparedStatement ps;
		Statement stmt;
		ResultSet rs,rs2;
		int holdRequests = 0, booksOnHold = 0;
		try {
			ps = con.con.prepareStatement("UPDATE borrowing SET indate = sysdate WHERE callNumber = ? AND copyNo = ?");
			ps.setInt(1, callNumber);
			ps.setInt(2, copyNumber);
			ps.executeUpdate();
			ps.close();
			stmt = con.con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM holdRequest WHERE CALLNUMBER = "+callNumber);
			while(rs.next()){
				holdRequests++;
			}
			rs2 = stmt.executeQuery("SELECT * FROM bookCopy WHERE CALLNUMBER = "+callNumber+" AND status LIKE 'on-hold'");
			
			while (rs2.next()){
				booksOnHold++;
			}
			
			System.out.println(holdRequests);
			System.out.println(booksOnHold);
			//if (rs.next() && rs2.next()){
				if(holdRequests>booksOnHold){

					ps = con.con.prepareStatement("UPDATE bookcopy SET status = 'on-hold' where callNumber = ? AND copyNo = ?");
					ps.setInt(1, callNumber);
					ps.setInt(2, copyNumber);
					ps.executeUpdate();
					ps.close();
					System.out.println("asd");
					//				ps = con.con.prepareStatement("DELETE FROM holdrequest WHERE hid = 2");
					//				System.out.println(rs.getInt("hid"));
					//				ps.executeUpdate();
					//				ps.close();

				}
				else
				{
					ps = con.con.prepareStatement("UPDATE bookcopy SET status = 'in' where callNumber = ? AND copyNo = ?");
					ps.setInt(1, callNumber);
					ps.setInt(2, copyNumber);
					ps.executeUpdate();
				}
				ResultSet fineCheckSet;
				Statement statement = con.con.createStatement();
				
				String sql =
						"SELECT bc.borid, bc.outDate, t.bookTimeLimit " +
						"FROM borrower_type t, " +
						"(SELECT * " +
						"FROM borrower b, (" +
						"SELECT * " +
						"FROM borrowing " +
						"WHERE callNumber = "+ callNumber +
						"AND copyNo = " + copyNumber +")c " +
						"WHERE " +
						"b.bid = c.bid)bc " +
						"WHERE bc.type like t.type";
				
				fineCheckSet = statement.executeQuery(sql);
				if (fineCheckSet.next()) {
					long limit = fineCheckSet.getLong("bookTimeLimit");
					java.sql.Date outDate1 = fineCheckSet.getDate("outDate");
					java.util.Date date = new java.util.Date(outDate1.getTime() + limit);
					java.util.Date today = new java.util.Date();
					System.out.println(outDate1);
					System.out.println(date);
					System.out.println(today);
					System.out.print(date.before(today));
					
					if (date.before(today)) {

						int borid = fineCheckSet.getInt("borid");
						System.out.println(borid);
						ps = con.con
								.prepareStatement("INSERT INTO fine VALUES (fid_sequence.nextval,'5',sysdate,null,?)");
						ps.setInt(1, borid);
						ps.executeUpdate();
						new NotificationDialog(null, "Uhoh!", "Overdue book! Fine imposed.");
					}else new NotificationDialog(null, "ERROR!", "Something went wrong. A fine was not imposed, but the book is returned. ");
				}
				
				con.con.commit();
				ps.close();
				
				

		//	}
		//	else System.out.println("rs/rs2 empty! \n");
		} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}



	public OracleConnection getConnection() {
		return con;
	}
	public void payFine(int bid, int fine) {
		try {
			ResultSet rs;
			Statement st = con.con.createStatement();
			int fines = 0;
			ArrayList<Integer> borids = new ArrayList();
			String sql =
					"SELECT b.borid " +
					"FROM fine f, borrowing b " +
					"WHERE b.borid = f.borid " +
					"AND bid = " + bid + " " +
					"AND f.paidDate IS NULL";
			
			rs = st.executeQuery(sql);
			while(rs.next()){
				fines++;
				borids.add(rs.getInt("borid"));
			}
			if (fines==0){
				new NotificationDialog(null, "ERROR!", "You have no outstanding fines. Payment failed.");
			}
			else
			{
				int i=0;
				String update = 
						"UPDATE fine " +
						"SET paidDate = sysdate " +
						"WHERE " +
						"paidDate IS NULL " +
						"AND borid = ?";

					try {
						PreparedStatement ps = con.con.prepareStatement("UPDATE fine SET paidDate = sysdate WHERE paidDate IS NULL AND borid = ?");
						while (fine!=0)
						{
							if(borids.size()<=i){
								con.con.commit();
								return;
							}
						ps.setInt(1, borids.get(i++));
						fine = fine-5;
						ps.executeUpdate();
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				con.con.commit();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}





