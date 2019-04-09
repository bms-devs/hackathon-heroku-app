FROM maven:3-jdk-8-alpine
COPY src /app/src
COPY .travis.yml /app/
COPY app.json /app/
COPY pom.xml /app/
COPY Procfile /app/
EXPOSE 3332
WORKDIR /app/
RUN mvn install
ENTRYPOINT [ "mvn", "spring-boot:run" ]