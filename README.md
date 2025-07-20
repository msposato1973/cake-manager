# cake-manager

Cake Manager Micro Service (fictitious)

# Cake Manager Micro Service (fictitious)

A summer intern started on this project but never managed to get it finished.
The developer assured us that some of the above is complete, but at the moment accessing the /cakes endpoint
returns a 404, so getting this working should be the first priority.

Requirements:

- By accessing /cakes, it should be possible to list the cakes currently in the system. JSON would be an acceptable response format.

- It must be possible to add a new cake.

- It must be possible to update an existing cake.

- It must be possible to delete an existing cake.

Comments:

- We feel like the software stack used by the original developer is quite outdated, it would be good to migrate the entire application to something more modern. If you wish to update the repo in this manner, feel free! An explanation of the benefits of doing so (and any downsides) can be discussed at interview.

- Any other changes to improve the repo are appreciated (implementation of design patterns, seperation of concerns, ensuring the API follows REST principles etc)

Bonus points:

- Add some suitable tests (unit/integration...)
- Add some Authentication / Authorisation to the API
- Continuous Integration via any cloud CI system
- Containerisation

Scope

- Only the API and related code is in scope. No GUI of any kind is required

# Original Project Info

To run a server locally execute the following command:

`mvn jetty:run`

and access the following URL:

`http://localhost:8282/`

Feel free to change how the project is run, but clear instructions must be given in README
You can use any IDE you like, so long as the project can build and run with Maven or Gradle.

The project loads some pre-defined data in to an in-memory database, which is acceptable for this exercise. There is
no need to create persistent storage.

# Submission

Please provide your version of this project as a git repository (e.g. Github, BitBucket, etc).

A fork of this repo, or a Pull Request would be suitable.

Good luck!

# Cake Manager Micro Service compile:
mvn clean spring-boot:run

# Memory Database accessible on:
http://localhost:8080/console/

# Swagger UI accessible on:
http://localhost:9090/actuator/swagger-ui/index.html

Swagger UI will now display the 🔐 Authorize button. When you insert a Bearer token there, 
all requests documented with @SecurityRequirement(name = "bearerAuth") 
will be sent with Authorization: Bearer <token> in the header.

# Build IMAGE
docker build -t cake-manager .

# Run with custom DB_URL variable
docker run -d -p 8282:8282 \
-e DB_URL=jdbc:mysql://db:3306/cakes \
--name cake-service \
cake-manager

# Get all cakes
curl http://localhost:8282/cakes/
curl http://localhost:8282/cakes/2

# Add a cake
curl -X POST http://localhost:8282/cakes -H "Content-Type: application/json" \
-d '{"title": "Massimo Sposato sdded", "description": "test Add your name in the body massimo", "image": "https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg"}'

# Update a cake
curl -X PUT http://localhost:8282/cakes/1 -H "Content-Type: application/json" \
-d '{"title": "Massimo Sposato update", "description": "test Add your name in the body massimo", "image": "https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg"}'


# Delete a cake
curl -X DELETE http://localhost:8282/cakes/1


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