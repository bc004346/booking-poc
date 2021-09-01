# Create new Kind cluster
kind create cluster --config 0-kind-config.yaml

# Load docker images into Kind cluster - this will take several minutes
kind load docker-image bc004346/orchestrator bc004346/manual-pricing bc004346/auto-pricing 

# Verify that images were correctly loaded
docker exec -it kind-control-plane crictl images

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

# in order to test Kiali or Grafana it helps to keep requests running 
# the first request represents aacorn, second aacom and third is expected to error out 
# each request should be run in a separate session to show traffic in Kiali flowing through Istio 
while true; do echo 'curl http://localhost:30080/reservation -H x-client: aacorn -X POST'; curl http://localhost:30080/reservation -H "x-client: aacorn" -X POST; echo '\n';  sleep 5; done
while true; do echo 'curl http://localhost:30080/reservation -H x-client: aacorn -X POST'; curl http://localhost:30080/reservation -H "x-client: aacom" -X POST; echo '\n';  sleep 3; done
while true; do echo 'curl http://localhost:30080/reservation -H x-client: aacorn -X POST'; curl http://localhost:30080/reservation -H "x-client: mobile" -X POST; echo '\n';  sleep 15; done

# cleanup
kind delete cluster