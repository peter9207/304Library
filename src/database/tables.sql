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
	number callNumber primary key, 
	varchar2 name);

create table HasSubject (
	number callNumber, 
	varchar2 subject,
	primary key(callNumber, subject));
	
create table BookCopy (
	number callNumber, 
	number copyNo, 
	varchar2 status);
	
create table HoldRequest(
	number hid, 
	number bid, 
	number callNumber, 
	timestamp issuedDate);
	
create table Borrowing(
	number borid, 
	number bid, 
	number callNumber, 
	number copyNo, 
	timestamp outDate, 
	timestamp inDate);
	
create table Fine (
	number fid, 
	number amount, 
	timestamp issuedDate, 
	timestamp paidDate, 
	number borid);