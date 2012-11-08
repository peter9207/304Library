create table BORROWER (
			NUMBER BID,
			NUMBER PASSWORD,
			VARCHAR2 NAME,
			VARCHAR2 ADDRESS,
			NUMBER PHONE,
			VARCHAR2 EMAILADDRESS,
			NUMBER SINORSTNO,
			VARCHAR2 TYPE
);

create table BORROWERTYPE(
				varchar2 type,
				booktimelimit
);

create table book(
				number callnumber,
				number isbn,
				varchar2 title,
				varchar2 mainauthor,
				varchar2 publisher,
				date year
				);
				