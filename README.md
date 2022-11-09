# cs122b-fall-team-38

Team number: 38\
Contributions: Derek Lam (doing it solo)\
\
Project 1:\
Instructions for deployment: \
FabFlix is an eCommerce platform designed using the full stack web application where a user can browse for the movies and make a purchase for a Movie when needed.The backend is a spring boot application which is used to create apis. This seperates the frontend from backend making it more efficient. The design is supported user authentication and a reCaptcha. We hosted FabFlix on a single AWS EC2 instance which was running on an Ubuntu Linux Operating system. We also made use of the Master and Slave MySQL setup to host the two instances. Its also important to that, the first instance we used the Apache Load Blancer which was handling both the incoming traffic and the redirecting it to the other two instances. This was designed to ease the load in case there was a high traffic.\
Demo 1 link: https://youtu.be/4h4_VF9epUQ 

Project 2:\
Instructions for deployment: \
The other step for deployment was deploying it using the TomCAT platform. You will grab the WAR File which was generated and deploy it using the TomCat web manager. This can also be done manually by placing the WAR file into the TOMCAT webapps directory. From here you can also run the application. \
Demo 2 link: https://youtu.be/7PqNQ-MBd_g 

Project 3:\
\
This Project is about securing the website using Recaptcha and https. Also to protect from sql injection attack use of Prepared Statements is done.\
The passwords stored in the database are also encrypted using strong password encryptor and is decrypted on login by user.
To load Bulk Data a SAX Xml Paser is made which can read data and do a bulk insert into the database.\
Demo 3 link: https://youtu.be/DYmaoLrQGE8 
From here you can also run the application. \
http://15.206.238.236:8080/FabFlix

Instructions for deployment:\
Deploy FabFlix (Remotely)
Git clone repository: git clone https://github.com/uci-jherold2-teaching/cs122b-fall-team-38.git

Change directory into repo: cd cs122b-fall-team-38

Build war file: mvn package

Copy the war file into tomcat: cp ./target/*.war /home/ubuntu/tomcat/webapps

Open Tomcat Domain at <your-amazon-instance-domain>:8080

Go to Manager Apps > Click FabFlix

You should now be on the movie list page.

Project 3 -

Inconsistences
We have written two files inconsistentGenreInMovies.md and inconsistentGenres.md

We have not written out as talked about in the demo inconsistences in parsing (such as parseInt kind of problems.

Efficiency
We modified two essential processes to make parsing more effective.

1 : We export the new inserts, such as for stars, genres, movies, stars in movies, and genres in movies, into.txt files so that we can quickly and effectively load them into the sql database. This saves back-and-forth with the db.
We found that doing this significantly reduced the amount of time we had to spend parsing compared to when we tried single insertion.

2 : We load all the data from SQL Dtabase to Java Memory in a single query.(eg. Loading the all genres as Map)

Prepared Statements can be found here in the folder [SQL Files](https://github.com/uci-jherold2-teaching/cs122b-fall-team-38/tree/main/SQL%20Files)
  
Demo 3 Link: https://youtu.be/DYmaoLrQGE8
