#!/bin/bash

export GITHUB_TEST_RESULTS_URL=https://github.com/dotCMS/test-results
export GITHUB_TEST_RESULTS_REPO=${GITHUB_TEST_RESULTS_URL}.git
export GITHUB_TEST_RESULTS_BROWSE_URL=${GITHUB_TEST_RESULTS_URL}/blob/master/projects/core

function removeIfExists() {
  local folder=${1}

  if [[ -d $folder ]]; then
    echo "Removing test results folder: ${folder}"
    rm -rf $folder
  fi
}

function createAndSwitch() {
  local folder=${1}
  if [[ ! -d $folder ]]; then
    mkdir -p $folder
  fi

  cd $folder
}

function getTestPath() {
  echo "${1}/${TEST_TYPE}/${databaseType}"
}

function cleanTestFolders() {
  removeIfExists "./${BUILD_HASH}"
  removeIfExists "./${BUILD_ID}"
  git status
  git commit -m "Cleaning ${TEST_TYPE} tests folders for ${BUILD_HASH} at ${BUILD_ID}"
}

function addResults() {
  local targetFolder=$(getTestPath ${1})
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
