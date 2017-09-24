#!/bin/sh
echo "********************************************************"
echo "Starting authentication server "
echo "********************************************************"
java -Djava.security.egd=file:/dev/./urandom -jar /usr/local/authenticationservice/@project.build.finalName@.jar

