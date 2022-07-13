#Download base maven image
FROM maven:3.8.4-openjdk-11-slim

ENV email=lkgamor@gmail.com

#Set Image Author
LABEL maintainer=${email}

#Set project deployment directory
WORKDIR /mtn-getfri-service


#Copy project pom file to working directory
COPY ./pom.xml .

#Build and cache all dependencies
RUN mvn dependency:go-offline -B

#Copy all required files and folders to 'build' directory
COPY ./src ./src

#Build application binary
RUN mvn clean package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "./target/mtn-getfri-service.jar"]