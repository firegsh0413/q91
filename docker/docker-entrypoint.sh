#!/bin/sh

# 暫存腳本
FILE=runjava
touch $FILE
chmod 755 $FILE

# 預設為指令java 如果$1為己存在指令則手動執行
CMD=java
if [ ! "${1#-}" != "$1" ] && which "$1" ;then
  exec $@
  exit 0
fi

# 預設值
DEFAULT_PORT=80

# 調整LOG輸出
DEFAULT_LOG_ROOT=INFO
DEFAULT_LOG_AUTOCONFIG=ERROR

# 依環境變數增加參數
[ "$PORT" != "" ] && DEFAULT_PORT="$PORT"
[ "$LOG_ROOT" != "" ] && DEFAULT_LOG_ROOT="$LOG_ROOT"
[ "$LOG_AUTOCONFIG" != "" ] && DEFAULT_LOG_AUTOCONFIG="$LOG_AUTOCONFIG"

[ "$REDIS_HOST" != "" ] && CMD="$CMD -Dspring.redis.host=$REDIS_HOST"
[ "$REDIS_PORT" != "" ] && CMD="$CMD -Dspring.redis.port=$REDIS_PORT"
[ "$REDIS_PASS" != "" ] && CMD="$CMD -Dspring.redis.password=$REDIS_PASS"

[ "$MYSQL_USER" != "" ] && CMD="$CMD -Dspring.datasource.username=$MYSQL_USER"
[ "$MYSQL_PASS" != "" ] && CMD="$CMD -Dspring.datasource.password=$MYSQL_PASS"
[ "$MYSQL_HOST" != "" ] && CMD="$CMD -Dq91.db.ip=$MYSQL_HOST"
[ "$MYSQL_DB" != "" ] && CMD="$CMD -Dq91.db.name=$MYSQL_DB"
[ "$LOG_FOLDER" != "" ] && CMD="$CMD -Dq91.log.folder=$LOG_FOLDER"

# 對外PORT
CMD="$CMD -Dserver.port=$DEFAULT_PORT"
# LOG相關
if [ "$SHOW_LOG" = "" ]; then
  CMD="$CMD -Dlogging.level.root=$DEFAULT_LOG_ROOT"
  CMD="$CMD -Dlogging.level.org.apache.tomcat=$DEFAULT_LOG_ROOT"
  CMD="$CMD -Dlogging.level.org.apache.util.net=$DEFAULT_LOG_ROOT"
  CMD="$CMD -Dlogging.level.org.springframework.boot.autoconfigure=$DEFAULT_LOG_AUTOCONFIG"
  CMD="$CMD -Dlogging.level.org.springframework.web=$DEFAULT_LOG_ROOT"
fi

# 組合指令到暫存腳本並執行
echo "#!/bin/sh" > $FILE
echo $CMD $@ -jar $JARFILE >> $FILE
exec $FILE
