# Advanced_Queue_Java

Environment Variables
---
1. AQ_USER --> SYS as SYSDBA
2. AQ_PASSWORD --> oracle
3. AQ_URL --> jdbc:oracle:thin:@localhost:1521:xe
4. AQ_DESTINATION --> AQ_ADMIN.MESSAGE_Q
5. AQ_MESSAGE --> AQ_ADMIN.MESSAGE_TYPE

---

Oracle (Image Docker)
---
1. docker pull quay.io/maksymbilenko/oracle-12c
2. docker run -d --name oracle -p 1521:1521 quay.io/maksymbilenko/oracle-12c
3. USERNAME --> SYS
4. PASSWORD --> oracle

---

Advanced Queue
---

-----Create Users-----

CREATE USER aq_admin IDENTIFIED BY aq_admin DEFAULT TABLESPACE users;
GRANT connect TO aq_admin;
GRANT create type TO aq_admin;
GRANT aq_administrator_role TO aq_admin;
ALTER USER aq_admin QUOTA UNLIMITED ON users;

CREATE USER aq_user IDENTIFIED BY aq_user DEFAULT TABLESPACE users;
GRANT connect TO aq_user;
GRANT aq_user_role TO aq_user;

-----Create Object Message-----

CONNECT aq_admin/aq_admin

CREATE OR REPLACE TYPE MESSAGE_TYPE AS OBJECT(
subject VARCHAR2(30),
text VARCHAR2(80)
);

-----Create Queue-----

CONNECT aq_admin/aq_admin

EXECUTE dbms_aqadm.create_queue_table (queue_table => 'MESSAGE_QTAB', queue_payload_type => 'MESSAGE_TYPE');

EXECUTE dbms_aqadm.create_queue (queue_name => 'MESSAGE_Q', queue_table => 'MESSAGE_QTAB');

EXECUTE dbms_aqadm.start_queue (queue_name => 'MESSAGE_Q');

-----Enqueue Message-----

CONNECT aq_admin/aq_admin

DECLARE
enqueue_options DBMS_AQ.enqueue_options_t;
message_properties DBMS_AQ.message_properties_t;
message_handle RAW(16);
message AQ_ADMIN.MESSAGE_TYPE;
BEGIN
message := AQ_ADMIN.MESSAGE_TYPE('Hello', 'Test1');

    dbms_aq.enqueue(queue_name => 'aq_admin.MESSAGE_Q', enqueue_options => enqueue_options, message_properties => message_properties, payload => message, msgid => message_handle);

    COMMIT;
END;


---