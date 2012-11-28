package main;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import ui.NotificationDialog;

public class DatabaseHandler {
	private static OracleConnection con;
	public static final int BORROWER_SEARCH = 0;
	public static final int OVERDUE_SEARCH = 1;
	public static final int CHECKED_OUT_REPORT = 2;
	public static final int MOST_POPULAR_REPORT = 3;
	public DatabaseHandler(){

		con = new OracleConnection();
	}
	public void addBook(int callNumber, int isbn, String title, String mainAuthor, String publisher, int year, int copy, Vector<String> subVector, Vector<String> authorVector){
		PreparedStatement ps;
		ResultSet rs;
		int copynumber = 0;
		try
		{

			Statement stm = con.con.createStatement();
			rs = stm.executeQuery("Select count(*) copynum from bookcopy where callnumber = "+callNumber);
			if (rs.next()){
				copynumber = rs.getInt("copynum") + 1;
			}
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
						.prepareStatement("INSERT INTO BookCopy VALUES (?,?,?)");
				ps.setInt(1, callNumber);
				ps.setInt(2, copynumber++);
				ps.setString(3, "in");
				ps.executeUpdate();
				// commit work 
				con.con.commit();
				ps.close();
			}
			if (copy == 1){
				new NotificationDialog(null, "Success", copy+" copy created.");
			}
			else
				new NotificationDialog(null, "Success", copy+" copies created.");

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
	public Vector<Object[]> getBooks(String searchTerms, int searchParameters, int searchType){
		
		Statement  stmt;
		ResultSet  rs;
		Vector<Object[]> books = new Vector<Object[]>();
		ArrayList<String> colNames = new ArrayList();
		String colName;
		int counter = 0;
		String defaultQuery = null;
		String filteredQuery = null;
		switch (searchType){
		case BORROWER_SEARCH:
			switch(searchParameters){
			case 0:
				String searchParam = "Title";
				defaultQuery =
						"SELECT b.callNumber, isbn, title, mainAuthor, publisher, year, innum, outnum FROM book b, (SELECT * from (SELECT callNumber inCall, count(copyNo) innum FROM bookcopy WHERE status LIKE 'in' GROUP BY callNumber)c FULL JOIN (select callNumber outCall, count(copyNo) outnum FROM bookcopy WHERE status LIKE 'out' GROUP BY callNumber)d ON c.inCall = d.outCall)cd WHERE (b.callNumber = cd.inCall OR b.callNumber = cd.outCall)";
				filteredQuery =
						"SELECT callNumber, isbn, title, mainAuthor, publisher, year, innum, outnum " +
								"FROM " +
								"book b, " +
								"(SELECT * from " +
								"(SELECT callNumber inCall, count(copyNo) innum FROM bookcopy WHERE status LIKE 'in' GROUP BY callNumber)c " +
								"FULL JOIN " +
								"(select callNumber outCall, count(copyNo) outnum FROM bookcopy WHERE status LIKE 'out' GROUP BY callNumber)d " +
								"ON c.inCall = d.outCall)cd " +
								"WHERE (b.callNumber = cd.inCall OR b.callNumber = cd.outCall) " +
								"AND UPPER(b."+searchParam+") LIKE " + "'%"+searchTerms.toUpperCase().trim()+"%'";
				break;
			case 1:
				defaultQuery =
				"SELECT callNumber, isbn, title, mainAuthor, publisher, year, innum, outnum " +
						"FROM " +
						"book b, " +
						"(SELECT * from " +
						"(SELECT callNumber inCall, count(copyNo) innum FROM bookcopy WHERE status LIKE 'in' GROUP BY callNumber)c " +
						"FULL JOIN " +
						"(select callNumber outCall, count(copyNo) outnum FROM bookcopy WHERE status LIKE 'out' GROUP BY callNumber)d " +
						"ON c.inCall = d.outCall)cd " +
						"WHERE b.callNumber = cd.inCall OR b.callNumber = cd.outCall";
				filteredQuery =
						"SELECT DISTINCT b.callNumber, isbn, title, mainAuthor, publisher, year, innum, outnum " +
								"FROM book b, hasauthor ha, " +
								"(SELECT * " +
								"FROM " +
								"(SELECT callNumber inCall, count(copyNo) innum " +
								"FROM bookcopy " +
								"WHERE status " +
								"LIKE 'in' " +
								"GROUP BY callNumber)c " +
								"FULL JOIN " +
								"(select callNumber outCall, count(copyNo) outnum " +
								"FROM bookcopy " +
								"WHERE status " +
								"LIKE 'out' " +
								"GROUP BY callNumber)d " +
								"ON c.inCall = d.outCall)cd " +
								"WHERE " +
								"(b.callNumber = cd.inCall OR b.callNumber = cd.outCall) " +
								"AND " +
								"((UPPER(b.mainAuthor) LIKE " +"'%"+searchTerms.toUpperCase().trim()+"%') " +
								"OR " +
								"(ha.callNumber = b.callNumber AND UPPER(ha.name) LIKE " +"'%"+searchTerms.toUpperCase().trim()+"%'))";
				break;
			case 2:
				defaultQuery =
				"SELECT callNumber, isbn, title, mainAuthor, publisher, year, innum, outnum " +
						"FROM " +
						"book b, " +
						"(SELECT * from " +
						"(SELECT callNumber inCall, count(copyNo) innum FROM bookcopy WHERE status LIKE 'in' GROUP BY callNumber)c " +
						"FULL JOIN " +
						"(select callNumber outCall, count(copyNo) outnum FROM bookcopy WHERE status LIKE 'out' GROUP BY callNumber)d " +
						"ON c.inCall = d.outCall)cd " +
						"WHERE b.callNumber = cd.inCall OR b.callNumber = cd.outCall";
				filteredQuery =
						"SELECT DISTINCT b.callNumber, isbn, title, mainAuthor, publisher, year, innum, outnum " +
								"FROM book b, hassubject hs, " +
								"(SELECT * " +
								"FROM " +
								"(SELECT callNumber inCall, count(copyNo) innum " +
								"FROM bookcopy " +
								"WHERE status " +
								"LIKE 'in' " +
								"GROUP BY callNumber)c " +
								"FULL JOIN " +
								"(select callNumber outCall, count(copyNo) outnum " +
								"FROM bookcopy " +
								"WHERE status " +
								"LIKE 'out' " +
								"GROUP BY callNumber)d " +
								"ON c.inCall = d.outCall)cd " +
								"WHERE " +
								"(b.callNumber = cd.inCall OR b.callNumber = cd.outCall) " +
								"AND " +
								"(hs.callNumber = b.callNumber AND UPPER(hs.subject) LIKE " +"'%"+searchTerms.toUpperCase().trim()+"%')";
				break;
			}
			break;
		case OVERDUE_SEARCH:
			defaultQuery =
			"SELECT book.callNumber, book.isbn, book.title, bor.copyno, btl.name, btl.emailaddress, (bor.outdate + btl.bookTimeLimit) " +
					"FROM BORROWING bor, book book, " +
					"(SELECT b.name, b.bid, b.emailaddress, bt.bookTimeLimit " +
					"FROM " +
					"BORROWER b, BORROWER_TYPE bt " +
					"WHERE " +
					"B.TYPE LIKE BT.TYPE " +
					")btl " +
					"WHERE " +
					"bor.bid = btl.bid " +
					"AND " +
					"bor.callNumber = book.callNumber " +
					"AND " +
					"INDATE IS NULL " +
					"AND OUTDATE < (SYSDATE - btl.bookTimeLimit)";

			break;
		case CHECKED_OUT_REPORT:
			defaultQuery =
			"SELECT book.callNumber, book.isbn, book.title, bor.copyno, (bor.outdate + btl.bookTimeLimit) FROM BORROWING bor, book book, (SELECT b.name, b.bid, bt.bookTimeLimit FROM BORROWER b, BORROWER_TYPE bt WHERE B.TYPE LIKE BT.TYPE)btl WHERE bor.bid = btl.bid AND bor.callNumber = book.callNumber AND INDATE IS NULL ORDER BY book.callNumber";

			filteredQuery =
					"SELECT distinct book.callNumber, book.isbn, book.title, bor.copyno, (bor.outdate + btl.bookTimeLimit) FROM BORROWING bor, book book, hassubject hs, (SELECT b.name, b.bid, bt.bookTimeLimit FROM BORROWER b, BORROWER_TYPE bt WHERE B.TYPE LIKE BT.TYPE)btl WHERE bor.bid = btl.bid AND bor.callNumber = book.callNumber AND INDATE IS NULL AND book.callNumber = hs.callNumber AND UPPER(hs.subject) LIKE '%"+searchTerms.toUpperCase().trim()+"%' ORDER BY book.callNumber";		
			break;
		
		case MOST_POPULAR_REPORT:
			filteredQuery = "select b.callNumber, isbn, title, mainauthor, publisher, year, c.borrowed from (select * from (select callnumber, count(callNumber) borrowed from borrowing where outdate >= to_date("+searchTerms+",'yyyy') AND outDate <  ADD_MONTHS(to_date("+searchTerms+",'yyyy'),12) group by callnumber ) where rownum <= "+searchParameters+" order by rownum)c, book b WHERE b.callNumber = c.callNumber order by borrowed desc";
		}
		try
		{
			stmt = con.con.createStatement();

			if (searchTerms.isEmpty()){
				rs = stmt.executeQuery(defaultQuery);
			}
			else rs = stmt.executeQuery(filteredQuery);

			// get info on ResultSet
			ResultSetMetaData rsmd = rs.getMetaData();

			// get number of columns
			int numCols = rsmd.getColumnCount();
			//display column names;
			for (int i = 0; i < numCols; i++)
			{
				colName = rsmd.getColumnName(i+1);
				colNames.add(colName);
			}
			System.out.println(" ");

			if (searchType != CHECKED_OUT_REPORT) {
				while (rs.next()) {
					books.add(counter, new Object[numCols]);
					for (int i = 0; i < numCols; i++) {
						books.get(counter)[i] = rs.getObject(i + 1);

					}
					counter++;
				}
			}
			else
			{

				String overdue = null;
				while (rs.next()) {
					books.add(counter, new Object[numCols]);
					for (int i = 0; i < numCols; i++) {
						if(i==numCols-1){
							overdue = null;
							java.util.Date today = new java.util.Date();
							java.util.Date dueDate = new java.util.Date((rs.getDate(i+1).getTime()));
							if(today.after(dueDate)){
								overdue = "Overdue";
							}
							books.get(counter)[i] = overdue;
						}
						else
							books.get(counter)[i] = rs.getObject(i + 1);

					}
					counter++;
				}				
				stmt.close();
			}
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
			ResultSet rs;
			Statement stm = con.con.createStatement();
			rs = stm.executeQuery("Select * from borrower order by bid desc");
			if(rs.next()){
				new NotificationDialog (null, "Acount created!", "Your BID is "+rs.getInt("bid")+".");
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
			rs1 = stmt.executeQuery("Select * from borrower where bid = "+bid+" AND expiryDate > sysdate");
			if(!rs1.next()){
				new NotificationDialog(null, "ERROR!", "This account does not exist, or it is expired.");
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

			new NotificationDialog(null, "Success", "Hold has been successfully placed. You will be emailed according to your position in the waiting list upon availability of this book.");
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

			rs = stmt.executeQuery("select * from (Select * from borrower where bid = "+bid+" AND expiryDate < sysdate) b FULL JOIN (select bid from fine f,borrowing b where b.borid = f.borid AND b.bid = "+bid+" AND f.paidDate IS NULL) c on b.bid = c.bid");
			int c=0;
			while(rs.next()){
				c++;
			}
			System.out.println(c);
			// get info on ResultSet
			if (c==0){
				rs2 = stmt.executeQuery("select bookTimeLimit from borrower b, borrower_type bt where bt.type LIKE b.type AND b.bid = "+bid);
				if (rs2.next()) {
					int limit = rs2.getInt("bookTimeLimit");
					for (int i = 0; i < callNumbers.size(); i++) {
						rs3 = null;
						Statement stm1 = con.con.createStatement();
						rs3 = stm1
								.executeQuery("select * from bookcopy where callNumber = "
										+ callNumbers.get(i).toString()
										+ " AND (status LIKE 'in' or status LIKE 'on-hold')");
												
						if (rs3.next()) {
							copyNumber = rs3.getInt("copyNo");
							ps = con.con
									.prepareStatement("INSERT INTO borrowing VALUES (borid_sequence.nextval,?,?,?,?,null)");
							java.util.Date today = new java.util.Date();
							java.sql.Date todaysql2 = new java.sql.Date(
									today.getTime());
							ps.setInt(1, bid);
							ps.setInt(2, callNumbers.get(i));
							System.out.println(callNumbers.get(i));

							System.out.println(copyNumber);
							ps.setInt(3, copyNumber);
							ps.setDate(4, todaysql2);
							ps.executeUpdate();
							ps.close();

							System.out.println(callNumbers.get(i).toString()
									+ " checked out!\n");
							ps = con.con.prepareStatement("UPDATE bookcopy SET status='out' WHERE callNumber = ? AND copyNo = ?");
							ps.setInt(1, callNumbers.get(i));
							ps.setInt(2, copyNumber);
							ps.executeUpdate();
							ps.close();
							con.con.commit();
							System.out.println("executed update");
							ResultSet returnTime;
							Statement stm = con.con.createStatement();
							returnTime = stm.executeQuery("SELECT bc.callNumber, (bc.outDate + t.bookTimeLimit)duedate FROM borrower_type t, (SELECT * FROM borrower b, (SELECT * FROM borrowing WHERE callNumber = "+callNumbers.get(i)+" AND copyNo = "+copyNumber+" AND INDATE IS NULL) c WHERE b.bid = c.bid) bc WHERE bc.type like t.type");
							if(returnTime.next()){
								new NotificationDialog (null, "Checked out!", "Book: "+returnTime.getInt("callNumber")+" Copy No: "+copyNumber+"\n Due: "+returnTime.getDate("duedate").toString());
							}
							rs3.close();
							stm.close();
							stm1.close();
							
						} else {
							new NotificationDialog(null, "ERROR!","No more copies available for Call Number "+callNumbers.get(i)+"! Unable to check out.");
						}
					}
					con.con.commit();

				}
				else{
					new NotificationDialog(null, "ERROR!", "The borrower does not exist, or his/her account is expired.");
				}
			}
			else
			{
				new NotificationDialog (null, "ERROR!", "This borrower has outstanding unpaid fines or his account is expired. ");
			}
		} catch (SQLException e) {
			new NotificationDialog(null, "ERROR!", "Something went wrong somewhere in the Database Handler, method: check out. Damn. "+e.getMessage());
			e.printStackTrace();
			System.out.println("Message: " + e.getMessage());
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
	public void returnBook(int callNumber, int copyNumber) {
		PreparedStatement ps;
		Statement stmt,checkstm;
		ResultSet rs,check;
		int holdRequests = 0;
		try {
			checkstm = con.con.createStatement();
			check = checkstm.executeQuery("Select * from borrowing where indate IS NULL AND callNumber = "+callNumber+" AND copyno = "+copyNumber);
			if (!check.next()){
				new NotificationDialog(null, "ERROR", "No books identified by the inputs above are checked out. Unable to process return");
				checkstm.close();
				return;
			}
			ResultSet fineCheckSet;
			Statement statement123 = con.con.createStatement();

			String sql = "SELECT bc.borid, bc.outDate, (bc.outDate + t.bookTimeLimit)duedate FROM borrower_type t, (SELECT * FROM borrower b, (SELECT * FROM borrowing WHERE callNumber = "+callNumber+" AND copyNo = "+copyNumber+" AND INDATE IS NULL) c WHERE b.bid = c.bid) bc WHERE bc.type like t.type";

				fineCheckSet = statement123.executeQuery(sql);
			if (fineCheckSet.next()) {
				java.util.Date duedate = new java.util.Date(fineCheckSet.getDate("duedate").getTime());
				java.util.Date today = new java.util.Date();
				if (duedate.before(today)) {

					int borid = fineCheckSet.getInt("borid");
					ps = con.con
							.prepareStatement("INSERT INTO fine VALUES (fid_sequence.nextval,'5',sysdate,null,?)");
					ps.setInt(1, borid);
					ps.executeUpdate();
					new NotificationDialog(null, "Uhoh!", "Overdue book! Fine imposed.");
				};
			}
			ps = con.con.prepareStatement("UPDATE borrowing SET indate = sysdate WHERE callNumber = ? AND copyNo = ?");
			ps.setInt(1, callNumber);
			ps.setInt(2, copyNumber);
			int returned = ps.executeUpdate();
			if (returned==0){
				new NotificationDialog(null, "ERROR!", "No books identified by the inputs are checked out. Unable to process return.");
				ps.close();
				return;
			}
			con.con.commit();
			ps.close();
			stmt = con.con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM holdRequest WHERE CALLNUMBER = "+callNumber);
			while(rs.next()){
				holdRequests++;
			}
			if(holdRequests>0){

				ps = con.con.prepareStatement("UPDATE bookcopy SET status = 'on-hold' where callNumber = ? AND copyNo = ?");
				ps.setInt(1, callNumber);
				ps.setInt(2, copyNumber);
				ps.executeUpdate();
				ps.close();
				rs.close();
				//rs2.close();
				
				ResultSet emails;
				Statement stm = con.con.createStatement();
				emails = stm.executeQuery("select b.bid, b.name, b.emailaddress from holdrequest hr, borrower b where hr.callNumber = "+callNumber+" AND hr.bid = b.bid ORDER BY hr.issuedDate ASC");
				int bidEmail = 0;
				String email;
				if(emails.next()){
					email = emails.getString("emailaddress");
					bidEmail = emails.getInt("bid");
					String nameEmail = emails.getString("name");
				if(!email.isEmpty()){
					new NotificationDialog (null, "Notified Holder", "An email has been sent to "+email+", the first in line to recieve a held copy.");
				}
				else new NotificationDialog(null, "Holder not notified", "The borrower next in line to recieve this held copy did not provide an email address. Please contact him manually. \n BID: "+bidEmail+"\n Name: "+nameEmail);	
				}
				else {
					new NotificationDialog(null,"ERROR", "query returned empty set.");
					return;
				}
				ps = con.con.prepareStatement("delete from holdrequest where callNumber = ? AND bid = ?");
				ps.setInt(1, callNumber);
				ps.setInt(2, bidEmail);
				ps.executeUpdate();
				con.con.commit();
				ps.close();
				stm.close();
				emails.close();

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
			fineCheckSet.close();
			statement123.close();
			new NotificationDialog(null, "Returned!", "Book sucessfully returned.");


		} catch (SQLException e) {
			new NotificationDialog(null, "Error", e.getMessage());
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
			ArrayList<Integer> borids = new ArrayList<Integer>();
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

				new NotificationDialog(null,"Payment Completed", "Payment accepted.");
			}
			con.con.commit();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Vector<Object[]> getInfoBorrowedItems(int bid) {
		ResultSet rs,rs2;
		Vector<Object[]> books = new Vector<Object[]>();
		int counter = 0;
		String sql = "SELECT book.callNumber, book.isbn, book.title, bor.copyno, book.mainAuthor, book.publisher, book.year FROM BORROWING bor, book book, (SELECT b.name, b.bid, bt.bookTimeLimit FROM BORROWER b, BORROWER_TYPE bt WHERE B.TYPE LIKE BT.TYPE)btl WHERE bor.bid = "+bid+" AND bor.bid = btl.bid AND bor.callNumber = book.callNumber AND INDATE IS NULL ORDER BY book.callNumber";
		try {
			Statement check = con.con.createStatement();
			rs = check.executeQuery("Select * from borrower where bid = "+bid);
			if(!rs.next()){
				new NotificationDialog(null, "ERROR!", "The BID you entered does not match any borrower in our records. Please try again.");
				return null;
			}
			Statement results = con.con.createStatement();
			rs2 = results.executeQuery(sql);
			ResultSetMetaData rsmd = rs2.getMetaData();
			int numCols = rsmd.getColumnCount();
			while (rs2.next()) {
				books.add(counter, new Object[numCols]);
				for (int i = 0; i < numCols; i++) {
					books.get(counter)[i] = rs2.getObject(i + 1);
				}
				counter++;
			}
			
				
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return books;
	}
	public Vector<Object[]> getInfoHeldItems(int bid) {
		ResultSet rs,rs2;
		Vector<Object[]> books = new Vector<Object[]>();
		int counter = 0;
		String sql = "select book.callNumber, book.isbn, book.title, book.mainAuthor, book.publisher, book.year, hr.issueddate from holdrequest hr, book book where hr.bid = "+bid+" AND hr.callNumber = book.callNumber";
		try {
			Statement check = con.con.createStatement();
			rs = check.executeQuery("Select * from borrower where bid = "+bid);
			if(!rs.next()){
				new NotificationDialog(null, "ERROR!", "The BID you entered does not match any borrower in our records. Please try again.");
				return null;
			}
			Statement results = con.con.createStatement();
			rs2 = results.executeQuery(sql);
			ResultSetMetaData rsmd = rs2.getMetaData();
			int numCols = rsmd.getColumnCount();
			while (rs2.next()) {
				books.add(counter, new Object[numCols]);
				for (int i = 0; i < numCols; i++) {
					books.get(counter)[i] = rs2.getObject(i + 1);
				}
				counter++;
			}
			
				
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return books;
	}
	public Vector<Object[]> getInfoFines(int bid) {
		ResultSet rs,rs2;
		Vector<Object[]> books = new Vector<Object[]>();
		int counter = 0;
		String sql = "select book.callNumber, book.isbn, book.title, bookfined.copyno, bookfined.amount from book book, (select fi.borid, fi.amount, fi.paiddate, bc.callnumber, bc.copyno from bookcopy bc, (select b.callNumber, f.borid, f.amount, f.paiddate, b.copyno from fine f, borrowing b where f.borid = b.borid AND b.bid = "+bid+" AND f.paiddate IS NULL) fi where fi.callNumber = bc.callNumber AND fi.copyno = bc.copyno) bookfined where book.callNumber = bookfined.callNumber";
		try {
			Statement check = con.con.createStatement();
			rs = check.executeQuery("Select * from borrower where bid = "+bid);
			if(!rs.next()){
				new NotificationDialog(null, "ERROR!", "The BID you entered does not match any borrower in our records. Please try again.");
				return null;
			}
			Statement results = con.con.createStatement();
			rs2 = results.executeQuery(sql);
			ResultSetMetaData rsmd = rs2.getMetaData();
			int numCols = rsmd.getColumnCount();
			while (rs2.next()) {
				books.add(counter, new Object[numCols]);
				for (int i = 0; i < numCols; i++) {
					books.get(counter)[i] = rs2.getObject(i + 1);
				}
				counter++;
			}
			
				
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return books;
	}
}





