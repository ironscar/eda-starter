# needs to run in WSL env from repository root instead of app root
docker stop sbt
docker rm sbt
docker rmi spring-eda-tgt:0.0.1
docker build -t spring-eda-tgt:0.0.1 .
