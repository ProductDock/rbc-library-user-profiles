#!/bin/bash
sudo usermod -a -G docker ${USER}
docker-credential-gcr configure-docker
docker stop rbc-library-user-profiles
docker rm rbc-library-user-profiles
docker rmi $(docker images | grep "rbc-library-user-profiles")
export USER_PROFILES_LOGS=/home/pd-library/logs/user-profiles
docker run -v $USER_PROFILES_LOGS:/app/logs -v $USER_PROFILES_JWT_PUBLIC_KEY:/app/app.pub:ro -v $USER_PROFILES_JWT_PRIVATE_KEY:/app/app.key:ro --env-file /home/pd-library/.user-profiles-service_env -dp 8085:8080 --name=rbc-library-user-profiles gcr.io/prod-pd-library/rbc-library-user-profiles:$1