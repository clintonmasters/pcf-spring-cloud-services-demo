#!/usr/bin/env bash
cf cs p-rabbitmq standard rabbit
echo sleeping 20
sleep 20
cf cs p-circuit-breaker-dashboard standard breaker
echo sleeping 20
sleep 20
cf cs p-service-registry standard registry
echo sleeping 20
sleep 20
cf cs p-config-server standard config -c config-server-setup.json
