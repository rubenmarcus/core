#!/bin/bash

source ../.cicd/seed.sh

fetchCICD
ls -las  ../.cicd/library

if [[ $? != 0 ]]; then
  echo "Aborting pipeline"
  exit 1
fi
