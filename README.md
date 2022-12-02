# cs122b-fall-team-38
- # General
    - ## Our img/* folder is [/graphImages](https://github.com/uci-jherold2-teaching/cs122b-fall-team-38/tree/main/graphImages)
    - ## Out logs/* folder is [/logs](https://github.com/uci-jherold2-teaching/cs122b-fall-team-38/tree/main/logs)
	- #### Team#: 38
	- #### Contributions: Derek Lam (doing it solo)

## Demo Video
	- View our Project 1 Demo Video here: https://youtu.be/4h4_VF9epUQ 
	- View our Project 2 Demo Video here: https://youtu.be/7PqNQ-MBd_g 
	- View our Project 3 Demo Video here: https://youtu.be/DYmaoLrQGE8
	- View our Project 4 Demo Video here: https://youtu.be/3_g7o0DEKRU


- # Project 1: Setup AWS, MySQL, JDBC, Tomcat, Start FabFlix
- #### FabFlix is an eCommerce platform designed using the full stack web application where a user can browse for the movies and make a purchase for a Movie when needed.The backend is a spring boot application which is used to create apis. This seperates the frontend from backend making it more efficient. The design is supported user authentication and a reCaptcha. We hosted FabFlix on a single AWS EC2 instance which was running on an Ubuntu Linux Operating system.

- # Project 2: Developing FabFlix Website
- #### The other step for deployment was deploying it using the TomCAT platform. You will grab the WAR File which was generated and deploy it using the TomCat web manager. This can also be done manually by placing the WAR file into the TOMCAT webapps directory. From here you can also run the application. \

- # Project 3: reCAPTCHA, HTTPS, PreparedStatement, StoredProcedure, XML Parsing 
- #### This Project is about securing the website using Recaptcha and https. Also to protect from sql injection attack use of Prepared Statements is done.
The passwords stored in the database are also encrypted using strong password encryptor and is decrypted on login by user.
To load Bulk Data a SAX Xml Paser is made which can read data and do a bulk insert into the database.


- # Project 4: Full Text Search, Autocomplete, Android Application, Fuzzy Search
- #### This Project is about creating autocomplete search anf full text-search on FabFlix website.We have also created an Android app for the FabFlix.\

- # Project 5 : Scaling FabFlix and performance Tuning
- #### In this we made use of the Master and Slave MySQL setup to host the two instances. Then we created another instance which is used asn the Apache Load Blancer which was handling both the incoming traffic and the redirecting it to the other two instances. This was designed to ease the load in case there was a high traffic.We also implemented Connection pooling to speed the the serve time of instances.\

- # Connection Pooling
    - #### filename/path of all code/configuration files in GitHub of using JDBC Connection Pooling.
        - [application.properties](https://github.com/uci-jherold2-teaching/cs122b-fall-team-38/blob/main/backend/src/main/resources/application.properties)

	- #### how Connection Pooling is utilized in the Fabflix code ?
	In the past, the Tomcat Servlet established a connection to the database using a url, created connections, and executed queries. Setting up new connections and running new queries every time meant that this took a long time. By creating a more secure connection that is saved in "application.properties" Connection Pooling allows us to speed up the process.When we request connections, the pre-existing connections in the pool are used instead of having to be formed from scratch. When the connection is ended,  the connection returns to the pool for future usage.
	
	- #### Explain how Connection Pooling works with two backend SQL.
    With two backend SQL, we have our database connection to either the Master or Slave's database, and achieves the same purpose explained as above, saving time on establishing and freeing connections.

- # Master/Slave
    - #### Include the filename/path of all code/configuration files in GitHub of routing queries to Master/Slave SQL.
        - All Repositories use master/slave implementation  /backend/src/main/java/com/fabflix/repository/*Repository.java
        -[application.properties](https://github.com/uci-jherold2-teaching/cs122b-fall-team-38/blob/main/backend/src/main/resources/application.properties)
    
	- #### How read/write requests were routed to Master/Slave SQL?
		With MySQL Replication  using mysql and connector/j, a client or web app reads and writes to MySQL Master but only reads to MySQL slave.Connector/j automatically routes the queries to masters and slaves in round-robin fashion. In our FabFlix, we route write requests to Master while allowing read requests to either Master or Slave.Read Requests are defined using @Transational(readOnly=true) while write requests are defined using @Transactional(readOnly=False).

- # JMeter TS/TJ Time Logs
    - #### Instructions of how to use the `log_processing.*` script to process the JMeter logs.
	- The `log_processing.py` file is located at the base of the repo, to use it, simply in the command line type:
		```
		python3 log_processing.py [LOG_FILE_PATH]
		```
		
- # JMeter TS/TJ Time Measurement Report

| **Single-instance Version Test Plan**          | **Graph Results Screenshot** | **Average Query Time(ms)** | **Average Search Servlet Time(ms)** | **Average JDBC Time(ms)** | **Analysis** |
|------------------------------------------------|------------------------------|----------------------------|-------------------------------------|---------------------------|--------------|
| Case 1: HTTP/1 thread                          | ![](https://github.com/uci-jherold2-teaching/cs122b-fall-team-38/blob/main/graphImages/SIngle%20Instance%201%20HTTP.png)   | 260                         | 260.19644208932624                                 | 257.0862982588948                        |  |
| Case 2: HTTP/10 threads                        | ![](https://github.com/uci-jherold2-teaching/cs122b-fall-team-38/blob/main/graphImages/Single%20Instance%2010%20HTTP.png)   | 298                         | 292.31772259889544                                  | 290.49243410713484                        |  |
| Case 3: HTTPS/10 threads                       | ![](https://github.com/uci-jherold2-teaching/cs122b-fall-team-38/blob/main/graphImages/Single%20Instance%2010%20HTTPS.png)   | 290                         | 288.7266443414048                                  | 287.1040271337798                       |  |
| Case 4: HTTP/10 threads/No connection pooling  | ![](https://github.com/uci-jherold2-teaching/cs122b-fall-team-38/blob/main/graphImages/Single%20Instance%2010%20(without%20CP).png)   | 322                         | 320.9089584850692                                  | 318.86016023306627                        |  |

| **Scaled Version Test Plan**                   | **Graph Results Screenshot** | **Average Query Time(ms)** | **Average Search Servlet Time(ms)** | **Average JDBC Time(ms)** | **Analysis** |
|------------------------------------------------|------------------------------|----------------------------|-------------------------------------|---------------------------|--------------|
| Case 1: HTTP/1 thread                          | ![](https://github.com/uci-jherold2-teaching/cs122b-fall-team-38/blob/main/graphImages/Scaled%20Instance%201%20HTTP.png)   | 266                         | 260.2785768357305                                  | 258.9981074943225                        |    |
| Case 2: HTTP/10 threads                        | ![](https://github.com/uci-jherold2-teaching/cs122b-fall-team-38/blob/main/graphImages/Scaled%20Instance%2010%20HTTP.png)   | 260                         | 259.0551990251828                                  | 257.5515028432169                        | |
| Case 3: HTTP/10 threads/No connection pooling  | ![](https://github.com/uci-jherold2-teaching/cs122b-fall-team-38/blob/main/graphImages/Scaled%20Instance%2010%20(without%20CP).png)   | 258                         | 256.3338820121148 | 254.92671846194364                        |      |

## Inconsistences
We have written in 3 files [inconsistentGenreInMovies.md](https://github.com/uci-jherold2-teaching/cs122b-fall-team-38/blob/main/SAXParser/inconsistentGenreInMovies.md) , [inconsistentGenres.md](https://github.com/uci-jherold2-teaching/cs122b-fall-team-38/blob/main/SAXParser/inconsistentGenres.md) and [inconsistentYear.md](https://github.com/uci-jherold2-teaching/cs122b-fall-team-38/blob/main/SAXParser/inconsistentYear.md)

## Efficiency
We modified two essential processes to make parsing more effective.

1 : We export the new inserts, such as for stars, genres, movies, stars in movies, and genres in movies, into.txt files so that we can quickly and effectively load them into the sql database. This saves back-and-forth with the db.
We found that doing this significantly reduced the amount of time we had to spend parsing compared to when we tried single insertion.

2 : We load all the data from SQL Dtabase to Java Memory in a single query.(eg. Loading the all genres as Map)

Prepared Statements can be found here in the folder [SQL Files](https://github.com/uci-jherold2-teaching/cs122b-fall-team-38/tree/main/SQL%20Files)



## Instructions for deployment:
Deploy FabFlix (Remotely)
- Git clone repository: git clone https://github.com/uci-jherold2-teaching/cs122b-fall-team-38.git

- Change directory into repo: cd cs122b-fall-team-38

- Build war file: mvn package

- Copy the war file into tomcat: cp ./target/*.war /home/ubuntu/tomcat/webapps

- Open Tomcat Domain at <your-amazon-instance-domain>:8080

- Go to Manager Apps > Click FabFlix

- You should now be on the movie list page.
