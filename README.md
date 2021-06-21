# IPOSE

How to start the IPOSE application
---

1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/ipose-1.0-SNAPSHOT.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`

Working on the frontend
---

1. Run `mvn exec:exec@ng-build` to compile the frontend to
   `src/main/resources/assets`, this runs `ng build` in watch mode.
2. Run `mvn exec:java` to start the backend, this runs the backend in
   server mode using `config.yml` for configuration.
