#!/bin/bash

source .cicd/seed.sh

echo "OJO:>> $(pwd)"
echo "OJO:>> $(ls -las ../)"
echo "OJO:>> $(ls -las)"
fetchCICD
echo "OJO:>> $(ls -las ../)"
echo "OJO:>> $(ls -las)"
echo "OJO:>> $(ls -las  ../cicd/library)"
echo "OJO:>>" && find ../cicd/library -type f

if [[ $? != 0 ]]; then
  echo "Aborting pipeline"
  exit 1
fi
