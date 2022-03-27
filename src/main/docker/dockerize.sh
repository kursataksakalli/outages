#!/bin/sh

cp target/outages-0.0.1-SNAPSHOT.jar src/main/docker/outages-0.0.1-SNAPSHOT.jar

cd src/main/docker

docker build --tag=outages-api:v1.0 .
