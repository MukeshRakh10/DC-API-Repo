FROM openjdk:8

#COPY target/spring-boot-docker-app.jar  /usr/app/


#WORKDIR /usr/app/
#ENTRYPOINT ["java", "-jar", "spring-boot-docker-app.jar"]
COPY target/dc-api.jar  dc-api.jar
ENTRYPOINT ["java", "-jar", "dc-api.jar"]
