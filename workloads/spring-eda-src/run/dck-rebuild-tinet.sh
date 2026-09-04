# needs to run in WSL env from repository root instead of app root
docker stop sbs
docker rm sbs
docker rmi spring-eda-src:0.0.1
docker build -f DockerfileTiNet -t spring-eda-src:0.0.1 .
