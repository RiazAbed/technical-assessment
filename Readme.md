# Technical Assessment

This project represents a simple rest service used to perform CRUD operations. 

##Planning
The main goal is to provide a rest service that connects to a MongoDB. I decided to let the Mongorepository to most of the work for me instead of writing specific queries for each requests

I also opted to use Lombok for my Customer entity so that I add and remove attributes without worrying about getters and setters.

Lastly I TDD'd most of the functionality in the CustomerResource class.  

##Improvements
The first thing I would improve is the security on the app. Basic auth with a single password should be replaced with either an authenticator that references a LDAP server, or a separate security service. 

I would also consider using liquibase to reference the database and ensue my entity schemas match up with the schema's in the database.