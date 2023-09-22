#!/bin/bash

# 進入腳本所在的目錄
PPATH=`readlink -f "${BASH_SOURCE:-$0}"`
PDIR=`dirname $PPATH`
cd $PDIR

# harbor 主機
HOST=docker-php.icchance.com.tw
# IMAGE倉
PROJECT=/java/q91/apifront
# 預設不 push
SKIP_PUSH=Y
# 預設 rebuild
REBUILD=Y
# 預設 BUILD_ONLY
BUILD_ONLY=""

for ARG in "$@"
do
    if [ "$ARG" = "-p" ]; then
    # 推送到 harbor
        SKIP_PUSH=""
    fi
    if [ "$ARG" = "-s" ]; then
    # 使用舊檔
        REBUILD=""
    fi
    if [ "$ARG" = "-b" ]; then
    # 只做 build
        BUILD_ONLY="Y"
    fi
done

# 重新建立輸出檔案
if [ "$REBUILD" != "" ];then
  # 檢查 java 是否安裝
  if ! which java > /dev/null
  then
    echo "java not found"
    exit 2
  fi
  # 檢查 maven 是否安裝
  if ! which mvn > /dev/null
  then
    echo "mvn not found"
    exit 3
  fi
  # 建立輸出檔
  mvn clean || (echo "mvn clean fail" && exit 4)
  mvn compile || (echo "mvn compile fail" && exit 5)
  mvn package -DskipTests || (echo "mvn package fail" && exit 6)

fi

# 檢查 jar 檔
JARFILE=$(cd target && ls -r *.jar | head -1)
if [ "$JARFILE" = "" ];then
  echo "jar file not found"
  exit 7
fi
# 版本tag
VERSION=$(echo $JARFILE | sed 's|-SNAPSHOT.jar||g;s|.*-||g')
VERSION=${VERSION:-unknow}

IMAGE=${HOST}${PROJECT}
IMAGEFULL=${IMAGE}:${VERSION}

[ "$BUILD_ONLY" != "" ] && exit 0

if ! docker build -t ${IMAGE}:${VERSION} --build-arg="SRCFILE=$JARFILE" -f docker/Dockerfile .
then
  echo "docker build image ${IMAGEFULL} fail"
  exit 5
else
  echo "docker build image ${IMAGEFULL} success"
  [ "$SKIP_PUSH" != "" ] && exit 0
  # 推送到 harbor
  docker push ${IMAGEFULL} \
  && echo "docker push ${IMAGEFULL} success" 
fi

