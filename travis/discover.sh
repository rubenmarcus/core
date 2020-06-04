#!/bin/bash

source .cicd/seed.sh

pwd
fetchCICD

ls -las  .cicd/library
find .cicd/library -type f

if [[ $? != 0 ]]; then
  echo "Aborting pipeline"
  exit 1
fi
