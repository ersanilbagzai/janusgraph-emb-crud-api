FROM openjdk:8

MAINTAINER er.sanil.bagzai@gmail.com

COPY target/api.crud-0.0.1-SNAPSHOT.jar /api.crud-0.0.1-SNAPSHOT.jar

COPY src/main/resources/janusgraph-cql.properties /janusgraph-cql.properties

ENTRYPOINT ["java","-Dspring.profiles.active=${ENV}","-jar" ,"/api.crud-0.0.1-SNAPSHOT.jar"]