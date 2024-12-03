FROM openjdk:17-jdk-slim as build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/target/crisalis-*.jar /app/crisalis.jar

EXPOSE 3000

ENTRYPOINT ["java", "-jar", "/app/crisalis.jar"]
