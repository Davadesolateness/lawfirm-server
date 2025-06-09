#!/bin/bash

# 应用名称
APP_NAME="lawfirm-server"
# JVM参数
JAVA_OPTS="-Xms512m -Xmx1024m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=512m"
# 日志目录
LOG_DIR="/var/log/lawfirm"
# 应用目录
APP_DIR="/opt/lawfirm"

# 创建日志目录
if [ ! -d "$LOG_DIR" ]; then
    mkdir -p $LOG_DIR
    chmod 755 $LOG_DIR
fi

# 检查应用是否已经运行
pid=$(ps -ef | grep $APP_NAME | grep -v grep | awk '{print $2}')
if [ -n "$pid" ]; then
    echo "$APP_NAME is already running, pid: $pid"
    exit 1
fi

# 启动应用
echo "Starting $APP_NAME..."
nohup java $JAVA_OPTS \
    -jar $APP_DIR/$APP_NAME.jar \
    --spring.profiles.active=prod \
    > $LOG_DIR/startup.log 2>&1 &

# 获取进程ID
pid=$!
echo "$APP_NAME started with pid: $pid"

# 等待应用启动
sleep 5
if ps -p $pid > /dev/null; then
    echo "$APP_NAME started successfully"
else
    echo "$APP_NAME failed to start"
    exit 1
fi 