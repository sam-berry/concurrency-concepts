#!/bin/bash

concurrency=$1
total_requests=$2

curl -X "DELETE" localhost:8080/unique-threads

ab -k -c $concurrency -n $total_requests localhost:8080/unique-threads/current-thread-id

threads=$(curl localhost:8080/unique-threads)

echo "Threads:" ${threads}
echo "Total number of threads:" $(awk -F "," '{print NF - 1}' <<< "${threads}")

curl -X "DELETE" localhost:8080/unique-threads
