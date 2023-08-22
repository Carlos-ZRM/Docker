#!/bin/bash

PORT=$1

while true; do
    echo "Starting nc server..."
    nc -l -u -p $PORT
    echo "nc server terminated. Restarting..."
done

