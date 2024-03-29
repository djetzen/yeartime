FROM openjdk:17.0.2
EXPOSE 8080
WORKDIR /
COPY ./build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]