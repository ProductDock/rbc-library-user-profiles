#!/bin/bash
sudo usermod -a -G docker ${USER}
docker-credential-gcr configure-docker
docker stop rbc-library-user-profiles
docker rm rbc-library-user-profiles
docker rmi $(docker images | grep "rbc-library-user-profiles")

docker run -v $USER_PROFILES_JWT_PUBLIC_KEY:/home/app.pub:ro -v $USER_PROFILES_JWT_PRIVATE_KEY:/home/app.key:ro -e USER_PROFILES_JWT_PUBLIC_KEY=/home/app.pub -e USER_PROFILES_JWT_PRIVATE_KEY=/home/app.key -dp 8085:8080 --name=rbc-library-user-profiles gcr.io/prod-pd-library/rbc-library-user-profiles:$1
