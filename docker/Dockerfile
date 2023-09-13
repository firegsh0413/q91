FROM alpine:3.18 as base

ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8

RUN apk --no-cache update \
  && apk add --no-cache openjdk8-jre


FROM base

ARG SRCFILE=q91.jar

ENV JARFILE=${SRCFILE:-q91.tar}
# 預設埠
ENV DEFAULT_PORT=80
# 預設不顯示LOG
ENV SHOW_LOG=

WORKDIR /usr/local/bin

ADD ./target/${SRCFILE} ./docker/docker-entrypoint.sh /usr/local/bin/

RUN chmod 0755 /usr/local/bin/docker-entrypoint.sh

EXPOSE 80

ENTRYPOINT ["docker-entrypoint.sh"]

