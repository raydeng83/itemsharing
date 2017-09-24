#!/bin/sh
echo "********************************************************"
echo "Starting user server "
echo "********************************************************"
java -Djava.security.egd=file:/dev/./urandom -jar /usr/local/userservice/@project.build.finalName@.jar

