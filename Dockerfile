FROM maven:3-jdk-8  as build
WORKDIR /workspace/app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
RUN pwd
RUN ls -l
RUN mvn install -DskipTests
# COPY /workspace/app/target/spring-jpa.jar .

FROM openjdk:8-jdk-alpine
RUN mkdir -p /opt/app/
WORKDIR /opt/app/
COPY --from=build /workspace/app/target/spring-jpa.jar /opt/app/spring-jpa.jar
ENTRYPOINT ["java","-jar","spring-jpa.jar"]