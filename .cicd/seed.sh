#!/bin/bash

repo="https://github.com/dotCMS/dot-cicd.git"
branch=
version=
project=core
tool=travis

# Clones and checkout a provided repo url with branch (optional)
function gitCloneAndCheckout() {
  local repo=$1
  local branch=$2

  if [[ -z "${repo}" ]]; then
    echo "Repo not provided, cannot continue"
    exit 1
  fi

  cloneOk=false
  if [[ ! -z "${branch}" ]]; then
    echo "Cloning CI/CD repo from ${repo} with branch ${branch}"
    git clone ${repo} -b ${branch} ../cicd/library
    if [[ $? != 0 ]]; then
      echo "Error checking out branch '${branch}', continuing with master"
    else
      cloneOk=true
    fi
  fi

  if [[ $cloneOk == false ]]; then
    echo "Cloning CI/CD repo from ${repo}"
    git clone ${repo} ../cicd/library

    if [[ $? != 0 ]]; then
      echo "Error cloning repo '${repo}'"
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

# Fetch CI/CD github repo to include and use its library
function fetchCICD() {
  gitCloneAndCheckout ${repo} ${branch}
  prepareScripts
}