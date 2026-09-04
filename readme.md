# EDA Starter

This project is for looking into event-driven architectures and patterns

## Main Branches

- `personal-github-kafka`: Kafka integration with Spring Boot apps

## Main Folders

- `docs/roadmap.md`: All the scenarios that we would want to try
- `workloads/spring-eda-src`: The Spring microservice that acts as the source for the events
- `workloads/spring-eda-tgt`: The Spring microservice that acts as the target for the events
- `repository`: Meant to contain the TAR files of the docker images to load into Rancher if required to simulate in a Kubernetes environment
- `helm-deployments`: All the helm charts to control overall deployment (both subcharts as well as parent chart `eda-stack`)
- `dck-deployments`: Includes the docker compose file for deploying a 3-node Kafka cluster
- `k8s-deployments`: Includes the ingress resource manifest for this entire setup

## Getting started

### First time setup

```bash
# Pull all images
docker pull <Postgresv18>
docker pull <MAVEN3>
docker pull <JRE25>
docker pull <Bitnami/Kafka>

# Create static docker resources
docker network create kdnet
docker run -d -p 5432:5432 --name pgdb1 --network kdnet -e POSTGRES_PASSWORD=postgrespass --mount source=pgdata1,target=/var/lib/postgresql <Postgresv18>
docker compose -f dck-deployments/kafka-deployment.yml up -d

# Create kubernetes resources
kubectl apply -f k8s-deployments/ingress.yaml

# Create dynamic docker resources (to be repeated whenever there are code changes in apps)
cd workloads/spring-eda-src
sh run/dck-rebuild-tinet.sh
sh run/cluster-load.sh
cd ../spring-eda-tgt
sh run/dck-rebuild-tinet.sh
sh run/cluster-load.sh

# Install helm chart for eda-stack-chart (to be done once cluster loads are done)
cd ../../helm-deployments/eda-stack
helm dependency build eda-stack-chart
helm package eda-stack-chart
helm install eda-stack eda-stack-chart-0.1.0.tgz
```

### Start from existing

```bash
# Start static docker containers if not already running
docker compose -f dck-deployments/kafka-deployment.yml start
docker start pgdb1

# Install helm parent chart (if not already running on cluster)
helm install eda-stack eda-stack-chart-0.1.0.tgz

```

### Access application

- Find the demo endpoints at 
  - Host/Docker: `http://localhost:8081/spring-eda-src/api/demo` and `http://localhost:8082/spring-eda-tgt/api/demo` 
  - Kubernetes: `http://localhost:8080/spring-eda-src/api/demo` and `http://localhost:8080/spring-eda-tgt/api/demo`

### Quick configuration

- We can use the parent Helm chart `eda-stack` to quickly configure things like replica counts for the producer and consumer apps
  - these are currently set to 1 at `helm-deployments/eda-stack/eda-stack-chart/values.yaml` and can be updated as required by the scenarios
- Then we can just run `helm upgrade eda-stack -f helm-deployments/eda-stack/eda-stack-chart-0.1.0.tgz` to update the resources on cluster
- For code-level changes, start from `Create dynamic docker resources` section above in `First Time Setup`

### Teardown

```bash
# optional as otherwise helm will keep running this
helm uninstall eda-stack

# stop static docker resources
docker stop pgdb1
docker compose -f dck-deployments/kafka-deployment.yml stop

# optional if want to get rid of entire setup
kubectl delete ingress my-eda-ingress
```
