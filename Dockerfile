FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/ehr-0.0.1-SNAPSHOT.jar ehr.jar

ENTRYPOINT ["java","-jar","ehr.jar"]