#!/bin/sh

java -jar ./target/mardybm-gmail-com/WEB-INF/lib/sqltool-2.3.1.jar --sql="SHUTDOWN;" --rcfile="db/connect.rc" localhost-sa 
