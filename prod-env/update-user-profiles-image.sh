#!/bin/bash
sudo usermod -a -G docker ${USER}
docker-credential-gcr configure-docker

docker stop rbc-library-user-profiles
docker rm rbc-library-user-profiles
docker rmi $(docker images | grep "rbc-library-user-profiles")

export GOOGLE_APPLICATION_CREDENTIALS=/home/pd-library/credentials.json
docker run -v $GOOGLE_APPLICATION_CREDENTIALS:/home/credentials.json:ro -e GOOGLE_APPLICATION_CREDENTIALS=/home/credentials.json -dp 8082:8080 --name=rbc-library-user-profiles gcr.io/prod-pd-library/rbc-library-user-profiles:$1
