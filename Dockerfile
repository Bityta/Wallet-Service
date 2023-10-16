FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY ./src/main/resources /app/resources
ADD /target/Wallet-Service-1.0-SNAPSHOT.jar test.jar
ENTRYPOINT ["java","-jar","test.jar"]