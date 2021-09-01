#!/bin/sh

echo "${GITHUB_REPOSITORY}"
echo "${DOCKER_SERVICE}"
if [ "${GITHUB_REPOSITORY}" != "KvalitetsIT/vdx-example-java" ] && [ "${DOCKER_SERVICE}" = "kvalitetsit/vdx-example-java" ]; then
  echo "Please run setup.sh REPOSITORY_NAME"
  exit 1
fi
