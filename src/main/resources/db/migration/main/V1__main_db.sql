-- Note, Quartz depends on row-level locking which means you must use the MVCC=TRUE
-- setting on your H2 database, or you will experience dead-locks
--
--
-- In your Quartz properties file, you'll need to set
-- org.quartz.jobStore.driverDelegateClass = org.quartz.impl.jdbcjobstore.StdJDBCDelegate

CREATE TABLE employee (
  employee_id int NOT NULL auto_increment,
  employee_name VARCHAR (255),
  primary key(employee_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
