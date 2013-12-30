#!/bin/sh

java -cp ./target/mardybm-gmail-com/WEB-INF/lib/hsqldb-2.3.1.jar org.hsqldb.server.Server --database.0 file:db/mydb --dbname.0 xdb
