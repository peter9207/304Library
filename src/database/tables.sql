create table borrower(
	NUMBER bid primary key,
	VARCHAR2 password not null,
	varchar2 name,
	varchar2 address,
	varchar2 emailAddress, 
	number sinOrStNo, 
	timestamp expiryDate, 
	varchar2 type);
	
create table borrower_type(
	varchar2 type primary key ,
	timestamp bookTimeLimit);
	
create table Book (
	number callNumber primary key , 
	number isbn,
	varchar2 title, 
	varchar2 mainAuthor, 
	varchar2 publisher, 
	timestamp year );
	
create table HasAuthor (
	number callNumber, 
	varchar2 name,
	CONSTRAINT hasauthor_pk PRIMARY KEY (callNumber,name)
	);

create table HasSubject (
	number callNumber, 
	varchar2 subject,
	CONSTRAINT hassubject_pk primary key(callNumber, subject));
	
create table BookCopy (
	number callNumber, 
	number copyNo, 
	varchar2 status,
	CONSTRAINT bookcopy_pk primary key(callNumber,copyNo)
	);
	
create table HoldRequest(
	number hid PRIMARY KEY, 
	number bid, 
	number callNumber, 
	timestamp issuedDate);
	
create table Borrowing(
	number borid PRIMARY KEY, 
	number bid, 
	number callNumber, 
	number copyNo, 
	timestamp outDate, 
	timestamp inDate);
	
create table Fine (
	number fid PRIMARY KEY, 
	number amount, 
	timestamp issuedDate, 
	timestamp paidDate, 
	number borid);