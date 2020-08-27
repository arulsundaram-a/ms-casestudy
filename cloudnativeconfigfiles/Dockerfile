FROM openjdk:8-jdk-alpine
ARG JAR_FILE=*.war
COPY ${JAR_FILE} startSpringBootApp.war
ENTRYPOINT ["java","-jar","/startSpringBootApp.war"]