FROM openjdk:8u171-alpine3.7
RUN apk --no-cache add curl
COPY build/libs/*-all.jar micronaut-sandbox.jar
CMD java ${JAVA_OPTS} -jar micronaut-sandbox.jar