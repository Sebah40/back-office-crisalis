<<<<<<< HEAD
FROM amazoncorretto:11-alpine-jdk
LABEL maintainer="CRISALIS"
COPY backend/target/Crisalis-0.0.1-SNAPSHOT.jar crisalis-app.jar
ENTRYPOINT ["java", "-jar", "/crisalis-app.jar"]
=======
FROM amazoncorretto:11-alpine-jdk
LABEL maintainer="CRISALIS"
COPY backend/target/Crisalis-0.0.1-SNAPSHOT.jar crisalis-app.jar
ENTRYPOINT ["java", "-jar", "/crisalis-app.jar"]
>>>>>>> 0f354145b9ff2c04178e4b6397b2dfe42bfc5db8
