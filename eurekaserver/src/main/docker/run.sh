#!/bin/sh
echo "********************************************************"
echo "Starting eureka server "
echo "********************************************************"
java -Djava.security.egd=file:/dev/./urandom -jar /usr/local/eurekaservice/@project.build.finalName@.jar

