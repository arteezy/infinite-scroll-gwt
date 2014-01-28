# Infinite scrolling with GWT
===================

Infinite scrolling with [GWT](http://www.gwtproject.org/) framework.

This project shows how to implement infinite scrolling on webpage, in this case, inside a GWT `DataGrid` table. 

As soon as scrollbar approaches the end of the scrollable area, JavaScript (compiled from Java) sends an asynchronous request to backend using GWT `RequestFactory`, which queries the database and returns a small batch of new elements to display. All of that happens in fast and async way.

The showcase data is generated randomly. 1 million entries are generated on multiple threads (for reprormance purposes) and stored inside an in-memory [HSQL](http://hsqldb.org/) database.

## Technologies used

* Java
* GWT
* Tomcat
* Hibernate
* HSQLDB
* Maven

## Running

Clone the repo, then do:

    mvn tomcat7:run-war
        
That's it. Maven will handle running embedded Tomcat and embedded HSQLDB, so you have nothing to worry about. 