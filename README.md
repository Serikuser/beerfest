Its have three main roles: admin, guest, participant.

-Admin can moderate and add content on site, ban users.

-Guest can book bars places and manage their books.

-Participant can register and manage their bar, select food/beer type and number of places available, and manage books on their bar.

The most suitable pattern for implementing the application was MVC. To determine the actions performed by a user, I decided to use the pattern - Command. 
To determine which command was produced by a user, I wrote the main servlet controller. To solve security issues, it was necessary to pre-process a users request. 
For this, I decided to introduce a chain of filters that work in a chain, implementing a pattern - a chain of responsibilities. The filters determine the level of access for a user, the validity of a users actions and the encoding of the data in the incoming request. 
In response, a user receives a ready HTML page, servlets (jsp) are responsible for this. To communicate with the database, I decided to embed DAO, also implemented my own tool for transaction support. 
To manage active connections to the database, a decision was made to create our own implementation of the connection pool. For thread safety of the pool, enam and collections from the concurrent package were used. 
Also, to ensure safety and ease of use, it was decided to create a ProxyConnection class that stores the relay connection in a field and implements the Connection interface in itself, replacing the behavior. For each command, I wrote a layer of services that are responsible for the specifics of user actions. 
The site has its own service for checking an email entered by a user by sending a confirmation letter with the subsequent completion of registration using the link from the letter. The unique key sent to a user to complete the registration is stored in the database for 15 minutes, after which the key is considered invalid.
I also implemented the ability to upload files to the server by users; for uploading, I wrote a servlet using the validation services for files of a valid size / type.
The site works on Tomcat server and uses SOAP transfer type, on back-end i used Java EE specification, data-base PostgreSQL on front-end CSS, JS, jQuery, Bootstrap
