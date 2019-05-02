# WebappsProject
WebappsProject 

This project is developed for Web Applications and Services module, University of Sussex (2019).

The project configurations relies on Payara(Glassfish), some specific configurations are needed to be made in order to pull and run this project successfully.

# Configurations:
# 1.) Create a realm:
Please follow the steps below to create a realm to be used in project:
- Go to payara admin console
- Select: Configurations -> server-config -> Security -> Realms
- Create a new Realm with the following settings and save:

Name: WebappsRealm<br/>
Class Name: com.sun.enterprise...jdbc.JDBCRealm<br/>
JAAS Context: jdbcRealm<br/>
JNDI: jdbc/webappsRealm<br/>
User Table: SYSTEMUSER<br/>
User Name Column: USERNAME<br/>
Password Column: USERPASSWORD<br/>
Group Table: SYSTEMUSERGROUP<br/>
Group Name Column: GROUPNAME<br/>
Digest Algorithm: SHA-256<br/>
Encoding: Hex<br/>
Charset: UTF-8<br/>

# 2.) Create a database named WebappsDB: 
Persistence settings are already made in the project.

# 3.) Create JDBC Pool and Data Source:
JDBC Pool Creation:
- Go to payara admin console
- Select Resources -> JDBC -> JDBC Connection Pools
- Create new JDBC Connection Pool with the following settings:<br/>
serverName: <b>localhost</b> portNumber: <b>1527</b> DatabaseName: <b>WebappsDB</b> User: <b>APP</b> Password: <b>APP</b>

JDBC Data Source Creation:
- Go to payara admin console
- Select Resources -> JDBC -> JDBC Resources
- Create new JDBC Data Source with name: jdbc/WebappsDB and select the pool created before.

# REST API
Project provides three different services for projects, students and supervisors.

Main path of the api "/resources", project path: "/project", student path: "student", supervisor path: "/supervisor"

Example api calls are given below:<br/><br/>
<b>Projects api:</b><br/>
Get all projects: http://localhost:10000/webapps2019/resources/project/all<br/>
Get projects owned by a supervisor whom id=100: http://localhost:10000/webapps2019/resources/project/100

<b>Supervisor api:</b><br/>
Get all supervisors: http://localhost:10000/webapps2019/resources/supervisor/all<br/>
Get supervisor of a student whom id=100: http://localhost:10000/webapps2019/resources/supervisor/100

<b>Student api:</b><br/>
Get all students: http://localhost:10000/webapps2019/resources/student/all<br/>
Get students supervised by a supervisor whom id=100: http://localhost:10000/webapps2019/resources/student/100
