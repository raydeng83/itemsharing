#!/bin/sh
echo "********************************************************"
echo "Starting config server "
echo "********************************************************"
java -Djava.security.egd=file:/dev/./urandom -jar /usr/local/configservice/@project.build.finalName@.jar

