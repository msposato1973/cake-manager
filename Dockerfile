FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY target/cake-manager.jar app.jar

ENV DB_URL=jdbc:hsqldb:mem:db \
    JAVA_OPTS=""

EXPOSE 8282

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dspring.datasource.url=$DB_URL -Dserver.port=8282 -jar app.jar"]

# Build the application with:
# mvn clean package -DskipTests
# Build the Docker image with:
# docker build -t cake-manager .
# Run the Docker container with:
# docker run -p 8282:8282 -e DB_URL=jdbc:hsqldb:file:./data/cake-manager-db cake-manager
# To run the container with a specific database URL, use:
# docker run -p 8282:8282 -e DB_URL=jdbc:hsqldb:file:./data/cake-manager-db cake-manager
# To run the container with the default in-memory database, use:
# docker run -p 8282:8282 cake-manager
# To run the container with a specific Java option, use:
# docker run -p 8282:8282 -e JAVA_OPTS="-Xmx512m" cake-manager
# To run the container with a specific database URL and Java options, use:
# docker run -p 8282:8282 -e DB_URL=jdbc:hsqldb:file:./data/cake-manager-db -e JAVA_OPTS="-Xmx512m" cake-manager
# To run the container with a specific database URL and Java options, use:
# docker run -p 8282:8282 -e DB_URL=jdbc:hsqldb:file:./data/cake-manager-db -e JAVA_OPTS="-Xmx512m" cake-manager


