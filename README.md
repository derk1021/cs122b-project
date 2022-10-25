# cs122b-fall-team-38

Team number: 38\
Contributions: Derek Lam (doing it solo)\
\
Project 1:\
Instructions for deployment: \
Demo 1 link: https://youtu.be/4h4_VF9epUQ 

Project 2:\
Instructions for deployment: \
Demo 2 link: https://youtu.be/7PqNQ-MBd_g 

Deployment:\
FabFlix is an eCommerce platform designed using the full stack web application where a user can browse for the movies and make a purchase for a Movie when needed. The design is supported user authentication and a reCaptcha. We hosted FabFlix on a single AWS EC2 instance which was running on an Ubunti Linux Operating system. We also made use of the Master and Slave MySQL setup to host the two instances. Its also important to that, the first instance we used the Apache Load Blancer whicu was handling both the incoming traffic and the redirecting it to the other two instances. This was designed to ease the load in case there was a high traffic. 
The other step for deployment was deploying it using the TomCAT platform. You will grab the WAR File which was generated and deploy it using the TomCat web manager. This can also be done manually by placing the WAR file into the TOMCAT webapps directory. From here you can also run the application. 

