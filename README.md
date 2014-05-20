VaadinHibernateSample
=====================

This is a very simple application that demonstrates one of many ways to add Hibernate to your Vaadin application.
It accesses Hibernate via JPA and thus should work with other JPA implementations as well. The application is completely
self-contained, using an in-memory H2 database for storage.

To try it out, first clone the repository. Then build and run the project using Maven:

$ mvn clean install
$ mvn jetty:run
