FROM openjdk:8-jdk-alpine
VOLUME /tmp

RUN mkdir -p /opt/app

ARG JAR_FILE
ADD ${JAR_FILE} /opt/app/app.jar

# A simple shell script to pass JVM arguments
ADD bin/entrypoint.sh /opt/app/entrypoint.sh

ENTRYPOINT [ "/opt/app/entrypoint.sh" ]