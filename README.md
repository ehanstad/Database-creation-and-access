# Database-creation-and-access
Second assignment for the backend part of the course Java Fullstack Remote Nordics Jan 23

## Appendix A
Found in `/sqlQueries` separated into eight different `.sql`-files. Run in a postgres Query Tool tables of superheroes, powers and assistants are created populated, altered and deleted.

## Appendix B
A maven built Java spring boot project, connected to a postgres database using JDBC.

### Functionality
The prime focus of the project was to create a connection between Java and a postgres database, to be able to easily handle some queries. It was achieved implementing a **CRUD** based solution with a customized Customer interface extending the CRUD interface.

### File structure
The project uses a hexagonal architecture. As shown in the picture below the it uses a package for the `/models` in this case records and `/repositories` containing the **CRUD** and the more specific one for Customer.

![file-structure](/Database-creation-and-access/chinook/src/main/resources/file-structure.jpg)

#### Authors
- Melvin Hedenfalk
- Erik Hanstad
