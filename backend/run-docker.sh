#!/bin/bash
set -e

# The requested service to run (e.g., "user-service")
SERVICE_NAME=$1

echo "--- Booting up $SERVICE_NAME using native host maven cache ---"
cd $SERVICE_NAME

# Execute spring-boot:run natively. Avoids deadlocking ~/.m2 with concurrent install processes
mvn spring-boot:run
