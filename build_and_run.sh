#!/usr/bin/bash

cd backend

mvn clean

mvn package

java -jar target/minimal-cms-0.0.1-SNAPSHOT.jar
