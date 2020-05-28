#!/bin/bash

export GITHUB_TEST_RESULTS_URL="https://github.com/dotCMS/test-results"
export GITHUB_TEST_RESULTS_REPO="${GITHUB_TEST_RESULTS_URL}.git"
export GITHUB_TEST_RESULTS_BROWSE_URL="${GITHUB_TEST_RESULTS_URL}/blob/master/projects/core"

function removeIfExists() {
  local results=${1}

  if [[ -d $results ]]; then
    echo "Removing test results results: ${results}"
    rm -rf $results
  fi
}

function createAndSwitch() {
  local results=${1}
  if [[ ! -d $results ]]; then
    mkdir -p $results
  fi

  cd $results
}

function getTestPath() {
  echo "${1}/${TEST_TYPE}/${databaseType}"
}

function cleanTestFolders() {
  if [[ -n "${BUILD_HASH}" ]]; then
    removeIfExists "./${BUILD_HASH}"
  fi

  if [[ -n "${BUILD_ID}" ]]; then
    removeIfExists "./${BUILD_ID}"
  fi

  git status
  git commit -m "Cleaning ${TEST_TYPE} tests resultss with hash '${BUILD_HASH}' and branch '${BUILD_ID}'"
}

function addResults() {
  local results=${1}
  if [[ -z "$results" ]]; then
    echo "Cannot add results since its empty, ignoring"
    exit 0
  fi

  local targetFolder=$(getTestPath ${results})
  mkdir -p ${targetFolder}
  echo "Adding test results to: ${targetFolder}"
  cp -R ${outputFolder}/ ${targetFolder}
}

function persistResults() {
  git status
  git add .
  git commit -m "Adding ${TEST_TYPE} tests results for ${BUILD_HASH} at ${BUILD_ID}"
  git push origin master
}

export GITHUB_PERSIST_COMMIT_URL="${GITHUB_TEST_RESULTS_BROWSE_URL}/$(getTestPath ${BUILD_HASH})"
export GITHUB_PERSIST_BRANCH_URL="${GITHUB_TEST_RESULTS_BROWSE_URL}/$(getTestPath ${BUILD_ID})"
