create TABLE app_info(
	PROPERTY varchar2(100) not null,
	VALUE varchar2(100),
	primary key(PROPERTY)
);

insert into app_info VALUES ('DATABASE_VERSION', '1');

create SEQUENCE RFQ_SEQ;



create TABLE RFQ (
        id number(19,0) not null,
		CUSTOMER_ID  number(19,0),
		COMPANY_ID	number(19,0),
		ADDRESS_ID	number(19,0),
		CUSTOMER_NAME	varchar2(500),
		COMPANY_NAME	varchar2(500),
		TITLE		varchar2(500),
		Address1	varchar2(500),
		ADDRESS2 	varchar2(500),
		DISTRICT_ID	number(19,0),
		PROVINCE_ID number(19,0),
		ZIPCODE		varchar2(100),
		TELEPHONE		varchar2(100),
		FAX			varchar2(100),
		EMAIL		varchar2(100),
		MOBILE		varchar2(100),
		REMARKS		clob,
		TEST_METHOD	CLOB,
		SAMPLE_DESC	varchar2(1000),
		RFQ_DATE	timestamp,
		has_Sent_Before NUMBER(1),
		REFERENCE	varchar2(100),
		
	
		QUOTATION_ID	number(19,0),
		LAB_REQ_ID		number(19,0),
		STATUS			varchar2(20),
		STATUS_REMARK	varchar2(500),
		
        primary key(id),
        
        CONSTRAINT fk_RFQ_CUSTOMER FOREIGN KEY (CUSTOMER_ID) REFERENCES CUSTOMER_TCS3(ID),
       	CONSTRAINT fk_RFQ_COMPANY FOREIGN KEY (COMPANY_ID) REFERENCES COMPANIES(COMPANY_ID),
       	CONSTRAINT fk_RFQ_ADDRESS FOREIGN KEY (ADDRESS_ID) REFERENCES ADDRESS(ADDRESS_ID),
        
        CONSTRAINT fk_RFQ_QUOTATION FOREIGN KEY (QUOTATION_ID) REFERENCES QUOTATION_TCS3(ID),
        CONSTRAINT fk_RFQ_REQUEST FOREIGN KEY (LAB_REQ_ID) REFERENCES LAB_REQUEST(REQ_ID)
);


create sequence RFQ_ITEM_SEQ;

create TABLE QFQ_ITEM (
        id number(19,0) not null,
		TESTMETHOD_INDEX number(19,0),
	    name varchar2(500),
		remark varchar2(500),
		TEST_METHOD_ID number(19,0),
		RFQ_ID number(19,0),
		primary key(id),
		CONSTRAINT fk_testMethod_RFQ_ITEM FOREIGN KEY (TEST_METHOD_ID) REFERENCES TEST_METHOD(TEST_METHOD_ID),
		CONSTRAINT fk_RFQ_RFQ_ITEM FOREIGN KEY (RFQ_ID) REFERENCES RFQ(ID)
);