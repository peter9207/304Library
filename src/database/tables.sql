create table borrower(
	bid NUMBER primary key,
	password VARCHAR2(25) not null,
	name VARCHAR2(25),
	address VARCHAR2(255),
	emailAddress VARCHAR2(25), 
	sinOrStNo number, 
	expiryDate DATE, 
	type VARCHAR2(25));
	
create table borrower_type(
	type VARCHAR2(25) primary key ,
	bookTimeLimit number);
	

insert into borrower_type VALUES('Student', 14);
insert into borrower_type VALUES('Faculty', 84);
insert into borrower_type VALUES('Staff', 42);
insert into borrower_type VALUES('General Public', 0);


create table Book (
	callNumber number primary key , 
	isbn number,
	title VARCHAR2(25), 
	mainAuthor VARCHAR2(25) , 
	publisher VARCHAR2(25) , 
	year number);
	
create table HasAuthor (
	callNumber number , 
	name VARCHAR2(25) ,
	CONSTRAINT hasauthor_pk PRIMARY KEY (callNumber,name)
	);

create table HasSubject (
	callNumber number, 
	subject VARCHAR2(25),
	CONSTRAINT hassubject_pk primary key(callNumber, subject));
	
create table BookCopy (
	callNumber number, 
	copyNo number, 
	status VARCHAR2(25),
	CONSTRAINT bookcopy_pk primary key(callNumber,copyNo)
	);
	
create table HoldRequest(
	hid number PRIMARY KEY, 
	bid number, 
	callNumber number, 
	issuedDate DATE);
	
create table Borrowing(
	borid number PRIMARY KEY, 
	bid number, 
	callNumber number, 
	copyNo number, 
	outDate DATE, 
	inDate DATE);
	
create table Fine (
	fid number PRIMARY KEY, 
	amount number, 
	issuedDate DATE, 
	paidDate DATE, 
	borid number);

create sequence hid_sequence;
create sequence borid_sequence;
create sequence bid_sequence;
create sequence fid_sequence;