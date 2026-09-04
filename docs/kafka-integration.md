# Kafka integration

## Kafka setup

- Run the kafka container as single broker with host app connections
  - `docker run -d --network kdnet --name kafka -p 9094:9094 -e KAFKA_CFG_NODE_ID=0 -e KAFKA_CFG_PROCESS_ROLES=controller,broker -e KAFKA_CFG_LISTENERS=PLAINTEXT://:9092,CONTROLLER://:9093,EXTERNAL_HOST://:9094 -e KAFKA_CFG_ADVERTISED_LISTENERS=PLAINTEXT://kafka:9092,EXTERNAL_HOST://localhost:9094 -e KAFKA_CFG_CONTROLLER_QUORUM_VOTERS=0@kafka:9093 -e KAFKA_CFG_CONTROLLER_LISTENER_NAMES=CONTROLLER -e KAFKA_LISTENER_SECURITY_PROTOCOL_MAP=CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT,EXTERNAL_HOST:PLAINTEXT <kafka image>`
  - `KAFKA_CFG_NODE_ID` specifies the unique id of each node in a Kafka cluster
  - `KAFKA_CFG_PROCESS_ROLES` specifies the container to run in dual-role, both as broker and controller
  - `KAFKA_CFG_LISTENERS` specifies regular traffic is on port 9092 and controller voting traffic is on 9093
  - `KAFKA_CFG_ADVERTISED_LISTENERS` specifies what port apps should connect on, which is 9092
  - `KAFKA_CFG_CONTROLLER_QUORUM_VOTERS` specifies which cluster nodes are allowed to vote during quorum in `NodeID@Host:Port` format
  - `KAFKA_CFG_CONTROLLER_LISTENER_NAMES` specifies the name of the listener that handles controller traffic 
  - `KAFKA_LISTENER_SECURITY_PROTOCOL_MAP` specifies if data is encrypted or not across what channels
  - Now to manage topics, we can login to the container using `docker exec -it kafka /bin/bash`
    - we can list topics using `kafka-topics.sh --bootstrap-server localhost:9092 --list`
    - we can create a topic using `kafka-topics.sh --bootstrap-server localhost:9092 --create --topic TASK-TOPIC`
    - we can delete topics using `kafka-topics.sh --bootstrap-server localhost:9092 --delete --topic TASK_TOPIC`
  - We can remove this singular container as we will create a 3-node cluster next

- Run the kafka cluster as multiple brokers with docker compose
  - create the kafka cluster using `docker compose -f dck-deployments/kafka-deployment.yml up -d`
  - create the topic by logging into one container and running below command
    - `kafka-topics.sh --bootstrap-server localhost:9092 --create --topic TASK-TOPIC --partitions 3 --replication-factor 3`
    - this automatically replicates it across all 3 containers
  - we can start/stop all the kafka containers using `docker compose -f dck-deployments/kafka-deployment.yml start` and `docker compose -f dck-deployments/kafka-deployment.yml stop` respectively

- The app-specific configurations are covered in their respective `workloads/*/readme.md`

## Best Practices

- Topics shouldn't be created by the transactional application themselves
- Name topics without underscores as these may conflict with internal topics
