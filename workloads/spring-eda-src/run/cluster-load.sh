# needs to run in WSL env from app root and not git repo root
cd ../..
rm repository/spring-eda-src/spring-eda-src-v0.0.1.tar
docker save -o repository/spring-eda-src/spring-eda-src-v0.0.1.tar spring-eda-src:0.0.1
cat repository/spring-eda-src/spring-eda-src-v0.0.1.tar | nerdctl -n k8s.io load
cd helm-deployments/spring-eda-src
helm package spring-edasrc-chart
cd ../../workloads/spring-eda-src
