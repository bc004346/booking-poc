# Orchestrator with Custom Plug-Ins

The purpose of this POC is to prove that it is possible for multiple teams to independently develop and deploy custom plug-ins to be used by core common project such as an orchestrator. The assumed constraints include using K8S with Istio for traffic management. The desired outcome is to show that no custom code is necessary in the common core project to map or tie-in custom plug-ins.

## Step 1

### Compile and create docker images for each project in a repo*:

```
cd autoPricing/
mvn clean install docker:build
cd ../manualPricing
mvn clean install docker:build
cd ../orchestrator
mvn clean install docker:build
```

*If doing it on a Windows machine, you will have to modify the value of ```<dockerHost>``` to something like 'http://localhost:2375'. For more info see [Docxker FAQ](https://docs.docker.com/desktop/faqs/)

*Another option is to create docker images manually (you will have to be in a corresponding folder for it to work):

```
docker build -t bc004346/orchestrator .
docker build -t bc004346/manual-pricing .
docker build -t bc004346/auto-pricing .
```

## Step 2

### Create local Kubernetes cluster **OR** connect to the one on the public cloud

I used [Kind](https://kind.sigs.k8s.io/), but the rest of the setup should work with Minikube or other K3S as well. The process to load local Docker images to other Kubernetes clusters will be different for each provider

```
cd ../k8s

kind create cluster --config 0-kind-config.yaml

kind load docker-image bc004346/orchestrator bc004346/manual-pricing bc004346/auto-pricing
```

## Step 3

### Setup Istio and start new pods with Docker images created in Step 1

```
# setup Istio
kubectl apply -f 1-istio-init.yaml
kubectl apply -f 2-istio-kind.yaml
kubectl apply -f 3-kiali-secret.yaml
kubectl apply -f 4-label-default-namespace.yaml

# wait for all Istio pods to be running:
kubectl get po -n istio-system

# create deployments and services
kubectl apply -f 5-deployments.yaml

# create virtual service and destination rule
kubectl apply -f 6-virtualservice.yaml

# wait for all pods to be running before testing
kubectl get po
```

Once all pods in ```default``` namespace are running, it will still take a few minutes for them to be fully operational

## Step 4

### To generate live traffic and be able to observe it in Kiali, use the following commands (on Mac) by running each one in it's own Terminal window:

```
while true; do echo 'curl http://localhost:30080/reservation -H x-client: aacorn -X POST'; curl http://localhost:30080/reservation -H "x-client: aacorn" -X POST; echo '\n';  sleep 5; done
while true; do echo 'curl http://localhost:30080/reservation -H x-client: aacom -X POST'; curl http://localhost:30080/reservation -H "x-client: aacom" -X POST; echo '\n';  sleep 3; done
while true; do echo 'curl http://localhost:30080/reservation -H x-client: mobile -X POST'; curl http://localhost:30080/reservation -H "x-client: mobile" -X POST; echo '\n';  sleep 15; done
```

### You can access the following Istio components below:
Kiali on http://localhost:31000   
Jaeger on http://localhost:31001    
Grafana on http://localhost:31002    

