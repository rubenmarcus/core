#!/bin/bash

export outputFolder="/custom/output"

if [ -z "${BUILD_HASH}" ]
then
    echo ""
    echo "======================================================================================"
    echo " >>>                'BUILD_HASH' environment variable NOT FOUND                    <<<"
    echo "======================================================================================"
    exit 0
fi

if [ -z "${BUILD_ID}" ]
then
    echo ""
    echo "======================================================================================"
    echo " >>>                'BUILD_ID' environment variable NOT FOUND                    <<<"
    echo "======================================================================================"
    exit 0
fi

# Validating if we have something to copy
if [ -z "$(ls -A $outputFolder)" ]; then
   echo ""
   echo "================================================================"
   echo "           >>> EMPTY [${outputFolder}] FOUND <<<"
   echo "================================================================"
   exit 0
fi

echo ""
echo "  >>> Pushing reports and logs to [${GITHUB_TEST_RESULTS_REPO}] <<<"
echo ""

# Now we want to add the logs link at the end of index.html results report file
logURL="${GITHUB_PERSIST_COMMIT_URL}/logs/dotcms.log"
logsLink="<h2 class=\"summaryGroup infoBox\" style=\"margin: 40px; padding: 15px;\"><a href=\"${logURL}\" target=\"_blank\">dotcms.log</a></h2>"

if [[ "${TEST_TYPE}" == "unit" ]]; then
  echo "
  ${logsLink}
  " >> ${outputFolder}/reports/html/index.html
elif [[ "${TEST_TYPE}" == "curl" ]]; then
  echo "
  ${logsLink}
  " >> ${outputFolder}/reports/html/curlTest/index.html
else
  echo "
  ${logsLink}
  " >> ${outputFolder}/reports/html/integrationTest/index.html
fi

cd /build/src
git clone ${GITHUB_TEST_RESULTS_REPO} .

createAndSwitch ${TEST_RESULTS}/projects

cleanTestFolders
addResults ./${BUILD_HASH}
addResults ./${BUILD_ID}

persistResults

cd /build/src/core/dotCMS/src/curl-test

bash /build/github_status.sh
ignoring_return_value=$?

bash /build/printStatus.sh
ignoring_return_value=$?
