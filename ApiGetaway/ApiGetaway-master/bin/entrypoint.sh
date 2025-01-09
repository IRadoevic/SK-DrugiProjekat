#!/usr/bin/env sh
set -x

java \
    -Djava.security.egd=file:/dev/./urandom \
    -XX:+PrintFlagsFinal \
    ${JAVA_OPTS} \
-jar /opt/app/app.jar