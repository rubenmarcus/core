FROM

ARG BUILD_FROM=COMMIT
ENV BUILD_FROM=${BUILD_FROM}
ARG BUILD_ID=origin/master
ENV BUILD_ID=${BUILD_ID}
ARG BUILD_HASH
ENV BUILD_HASH=${BUILD_HASH}

#  In order to run the integration test a license must be provided
ARG LICENSE_KEY

WORKDIR /srv

