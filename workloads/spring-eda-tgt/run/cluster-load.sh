# needs to run in WSL env from app root and not git repo root
cd ../..
rm repository/spring-eda-tgt/spring-eda-tgt-v0.0.1.tar
docker save -o repository/spring-eda-tgt/spring-eda-tgt-v0.0.1.tar spring-eda-tgt:0.0.1
cat repository/spring-eda-tgt/spring-eda-tgt-v0.0.1.tar | nerdctl -n k8s.io load
cd helm-deployments/spring-eda-tgt
helm package spring-edatgt-chart
cd ../../workloads/spring-eda-tgt
