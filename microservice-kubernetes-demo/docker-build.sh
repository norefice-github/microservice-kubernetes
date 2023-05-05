#!/bin/sh
if [ -z "$DOCKER_ACCOUNT" ]; then
    DOCKER_ACCOUNT=updateme
fi;
docker build --tag=microservice-kubernetes-demo-apache apache
docker tag microservice-kubernetes-demo-apache $DOCKER_ACCOUNT/microservice-kubernetes-demo-apache:latest
docker push $DOCKER_ACCOUNT/microservice-kubernetes-demo-apache

docker build --target=CATALOG --tag=microservice-kubernetes-demo-catalog .
docker tag microservice-kubernetes-demo-catalog $DOCKER_ACCOUNT/microservice-kubernetes-demo-catalog:latest
docker push $DOCKER_ACCOUNT/microservice-kubernetes-demo-catalog

docker build --target=CUSTOMER --tag=microservice-kubernetes-demo-customer .
docker tag microservice-kubernetes-demo-customer $DOCKER_ACCOUNT/microservice-kubernetes-demo-customer:latest
docker push $DOCKER_ACCOUNT/microservice-kubernetes-demo-customer

docker build --target=ORDER --tag=microservice-kubernetes-demo-order .
docker tag microservice-kubernetes-demo-order $DOCKER_ACCOUNT/microservice-kubernetes-demo-order:latest
docker push $DOCKER_ACCOUNT/microservice-kubernetes-demo-order