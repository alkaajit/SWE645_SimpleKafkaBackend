/*--SWE 642: Assignment 4 Restful webservice & Database I/O with JPA/Hibernate--*/

/ *
@author Ambily Sureshbabu Rema
This assignment focuses on server side processing of the Student Survey Form data via Restful web service and backend database connectivity using JPA/Hibernate.
The implementation required to implement one SurverResource that acts as a Restfule controller that receives all client requests, saves the form data 
to a database table, performs business logic(s) via business delegate classes and 
then responds back with a JSON response to the calling client. The assignment required all business logic code into separate.
All presentation logic is implemented using a separate angular application.
*/ 

Software and jar files Required:
--------------------------------
Apache Tomcat 9
Cisco AnyConnect VPN
JDBC driver for Oracle database (ojdbc8.jar)
Jars required for JPA hibernate (included as maven dependency)
Eclipse IDE for Java EE Developers

Application running steps
------------------------
1) Extract tomcat 9 (apache-tomcat-9.0.33.zip)
2) Copy the HW.war file into the apache-tomcat-9.0.33\webapps folder under extracted tomcat
3) Run the start.bat(windows) or start.sh(mac/linux) under the apache-tomcat-9.0.33\bin folder
4) Access application in a browser at http://localhost:8080/SWE645_RestBackend/
5) Refer the Navigation section in readme for more details

Application development steps
-----------------------------
1)Download Eclipse from https://www.eclipse.org/ and start

2) Extract Apache Tomcat and configure with eclipse 

3) Install Cisco AnyConnect VPN and use this "vpn.gmu.edu"

4)SQL Developer Oracle Table Creation:

create table STUDENT_DATA( 
name varchar2(20),address varchar(50), city varchar(10),
 ID int NOT NULL PRIMARY KEY,
 zip int ,state varchar(10),email varchar(20),
 telephone int,url varchar(30),
 date date,interests varchar(20),
 gradmonth varchar2(15),gradyear varchar(20),
 likes varchar(20),recommendation varchar(20),comments varchar(50));

4) Download hibernate (zip file) www.hibernate.org/downloads and jar files to project build
path/ classpath and add them to lib folder of the application.Get all jars in lib/ jpa and lib/required folders
eg:hibernate-core-5.4.1.final.jar or added the maven dependency for hibernate(i.e. hibernate-agroal) in pom.xml file

5)Download JDBC driver (ojdbc8.jar) and add it to the project�s build
path.This configures hibernate to use JDBC driver (jar) to connect to database

6)Since we are using JPA/Hibernate,Specify database connection properties in persistence.xml 

<persistence-unit name="StudentDB">
		<class>StudentDAO</class>
		<properties>
			<property name="javax.persistence.jdbc.url"
				value="jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu" />
			<property name="javax.persistence.jdbc.user"
				value="" />
			<property name="javax.persistence.jdbc.password"
				value="" />
			<property name="javax.persistence.jdbc.driver"
				value="oracle.jdbc.OracleDriver" />
			<property name="hibernate.dialect"
				value="org.hibernate.dialect.Oracle8iDialect" />
			<property name="hibernate.show_sql" value="true" />
		</properties>
	</persistence-unit>
</persistence>
 
4)Add dependencies for hibernate in the pom.xml file
<dependencies>
		<dependency>
			<groupId>org.hibernate</groupId>
			<artifactId>hibernate-agroal</artifactId>
			<version>5.4.12.Final</version>
			<type>pom</type>
		</dependency>
	</dependencies>


5) Unzip the file: it contains ReadMe file 'war' file(created using Export)
'Import the 'war' file to eclipse -> file -> import -> filter with 'war' and click next -> browse for this 'war' file -> click finish
Run this project -> Right click on this project -> 'Run as' -> select 'Run on server'

6) To check if the application is running you could access http://localhost:8080/HW4/student/survey this should display Ids of all students in the database,4
Also to check information of one student you could access http://localhost:8080/HW4/student/survey/1000
