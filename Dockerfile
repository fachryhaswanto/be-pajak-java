FROM openjdk:8-jdk-alpine
WORKDIR /opt/app
COPY target/pajakbpkad-0.0.1-SNAPSHOT.jar pajakbpkad.jar
EXPOSE 80
ENTRYPOINT exec java -jar pajakbpkad.jar