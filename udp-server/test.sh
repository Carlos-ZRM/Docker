#!/bin/bash

DEST_IP=$1
DEST_PORT=$2
NUM_PACKETS=15

for ((i = 0; i < $NUM_PACKETS; i++)); do
    echo "Packet $i" | nc -v -u $DEST_IP $DEST_PORT
done

