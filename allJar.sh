#!/usr/bin/env bash

rm -rf backend/build
./gradlew backend:bootJar

rm -rf dashboard/build
./gradlew dashboard:bootJar

rm -rf monitor/build
./gradlew monitor:bootJar



scp -r backend/build/libs root@8.142.166.66:/home/applications/backend
scp -r backend/build/resources root@8.142.166.66:/home/applications/backend

scp -r dashboard/build/libs root@8.142.166.66:/home/applications/dashboard
scp -r dashboard/build/resources root@8.142.166.66:/home/applications/dashboard

scp -r monitor/build/libs root@8.142.166.66:/home/applications/monitor
scp -r monitor/build/resources root@8.142.166.66:/home/applications/monitor


#java -jar -Duser.timezone=Asia/Shanghai backend-0.0.1.jar --spring.profiles.active=online
