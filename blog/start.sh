#!/bin/bash

# 启动入口类，该脚本文件用于别的项目时要改这里
MAIN_CLASS=com.choxsu.ChoxsuApplication

# Java 命令行参数，根据需要开启下面的配置，改成自己需要的，注意等号前后不能有空格

# 生成 class path 值
APP_BASE_PATH=$(cd `dirname $0`; pwd)
CP=${APP_BASE_PATH}/config:${APP_BASE_PATH}/lib/*

# 运行为后台进程，并在控制台输出信息
# java -Xverify:none ${JAVA_OPTS} -cp ${CP} ${MAIN_CLASS} &

# 运行为后台进程，并且不在控制台输出信息
nohup java -Xverify:none ${JAVA_OPTS} -cp ${CP} ${MAIN_CLASS} >/dev/null 2>&1 &

# 运行为后台进程，并且将信息输出到 output.log 文件
# nohup java -Xverify:none ${JAVA_OPTS} -cp ${CP} ${MAIN_CLASS} > output.log &

# 运行为非后台进程，多用于开发阶段，快捷键 ctrl + c 可停止服务
# java -Xverify:none ${JAVA_OPTS} -cp ${CP} ${MAIN_CLASS}







