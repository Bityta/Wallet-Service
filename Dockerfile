FROM openjdk:17-oracle
WORKDIR src
COPY ./src/main/resources src/main/resources
ADD /target/Wallet-Service-1.0-SNAPSHOT.jar test.jar
ENTRYPOINT ["java","-jar","test.jar"]