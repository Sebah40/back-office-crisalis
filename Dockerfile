FROM amazoncorretto:11-alpine-jdk
LABEL maintainer="CRISALIS"
COPY backend/target/Crisalis-0.0.1-SNAPSHOT.jar crisalis-app.jar
ENTRYPOINT ["java", "-jar", "/crisalis-app.jar"]