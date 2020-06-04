#!/bin/bash

export CICD_REPO="https://github.com/dotCMS/dot-cicd.git"
export CICD_BRANCH=
export CICD_DEST=.cicd/library
export CICD_VERSION=
export CICD_TARGET=core
export CICD_TOOL=travis

# Clones and checkout a provided CICD_REPO url with CICD_BRANCH (optional)
function gitCloneAndCheckout() {
  local CICD_REPO=$1
  local CICD_BRANCH=$2

  if [[ -z "${CICD_REPO}" ]]; then
    echo "Repo not provided, cannot continue"
    exit 1
  fi

  cloneOk=false
  if [[ ! -z "${CICD_BRANCH}" ]]; then
    echo "Cloning CI/CD CICD_REPO from ${CICD_REPO} with CICD_BRANCH ${CICD_BRANCH} to ${CICD_DEST}"
    git clone ${CICD_REPO} -b ${CICD_BRANCH} ${CICD_DEST}
    if [[ $? != 0 ]]; then
      echo "Error checking out CICD_BRANCH '${CICD_BRANCH}', continuing with master"
    else
      cloneOk=true
    fi
  fi

  if [[ $cloneOk == false ]]; then
    echo "Cloning CI/CD CICD_REPO from ${CICD_REPO} to ${CICD_DEST}"
    git clone ${CICD_REPO} ${CICD_DEST}

    if [[ $? != 0 ]]; then
      echo "Error cloning CICD_REPO '${CICD_REPO}'"
      exit 1
    fi
  fi
}

# Make bash scripts to be executable
function prepareScripts() {
  pushd ../cicd/library

  for script in $(find . -type f -name "*.sh"); do
    echo "Making ${script} executable"
    chmod +x ${script}
  done

  popd
}

# Fetch CI/CD github CICD_REPO to include and use its library
function fetchCICD() {
  gitCloneAndCheckout ${CICD_REPO} ${CICD_BRANCH}
  prepareScripts
}