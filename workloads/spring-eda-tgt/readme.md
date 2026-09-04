# Getting Started

## Traditional Build and Run

- Currently running with JDK25 so have to make sure JDK in POM is 25
- CD into `./workloads/spring-eda-tgt` from Windows CMD
- Basic build `mvn clean package -DskipTests` or `sh run/build.sh`
- Basic run `java -jar ./target/spring-eda-tgt-0.0.1-SNAPSHOT.jar` or `sh run/start.sh`
- Find the demo endpoint at `http://localhost:8082/spring-eda-tgt/api/demo`

### Database connection

- Works directly wnen DB is run in localhost with connection URL as `jdbc:postgresql://localhost:5432/postgres`
- Database setup scripts in `resources/test.sql`

### Kafka setup

- Add maven dependency `spring-boot-starter-kafka`
  - also need to add `jackson-databind` as otherwise it fails to create listener without this
- Configure Kafka properties
  - here we also configure deserializers for the event, where the industry-standard for polyglot services is Kafka Avro for value and string for key
  - for now we have only used String and Json for simpler setup
- Then we setup a DTO object called `TaskEvent` and write the consuming code in `ConsumerService` which then calls methods on `TaskService` on consumer
- We have to configure what are trusted sources and mappings for the deserializers in properties
  - the trusted sources are required to let Spring know its safe to deserialize the specific class
  - because the DTO classes are different in source and target, we also need to mention a type mapping

---

## Docker Build and Run

### Dockerfile

- We can run `run/dck-rebuild.sh` or `run/dck-rebuild-tinet.sh` to build the image depending on if we are inside or outside of TI network respectively
  - they each use a different Dockerfile
  - try and keep the last `<none>` tagged image for each of these as this includes the docker layer for the maven build
    - this allows reusing that layer quickly instead of redownloading it which takes a lot of time
- We can run `run/dck-run` to run the image
- Find the demo endpoint at `http://localhost:8082/spring-eda-tgt/api/demo`

### Database connection for docker containers

- When app is dockerized, both need to be on same docker network
  - create a network with `docker network create kdnet`
- Then run both db and app on same network using `--network kdnet` in the `run` command
  - or we can connect existing db container to this network using `docker network connect kdnet pgdb1`
- Then the connection URL becomes `jdbc:postgresql://pgdb1:5432/postgres`

### Kafka setup for docker containers

- the `bootstrap-servers` updates to the `PLAINTEXT` advertized listener addresses (refer to `application.yaml`)

---

## Kubernetes Build and Run

### Load images and run

- use `run/cluster-load.sh` to load the new image into Rancher k3 cluster and package the corresponding helm subchart
- use `helm dependency update eda-stack-chart` to pull the new subchart versions into the parent chart
- then create a new release for eda-stack using helm to update the cluster resources
- parent chart also includes an external service called `my-external-service` to connect to DB and Kafka
- the app will now be available at `http://localhost:8080/spring-eda-tgt/api/demo`
  - make sure to create the ingress at `k8s-deployments/ingress.yaml`

### Database connection for kubernetes

- Uses the external service name instead of docker container name to connect to database (refer to `application.yaml`)

### Kafka setup for kubernetes

- Uses the external service name and `EXTERNAL_K8S` advertized listener addresses for the `bootstrap-servers` (refer to `application.yaml`)
