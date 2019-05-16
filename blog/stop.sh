#!/bin/bash

# 启动入口类，该脚本文件用于别的项目时要改这里
MAIN_CLASS=com.choxsu.ChoxsuApplication

# kill 命令不使用 -9 参数时，项目被正常、温和地关闭，jfinal 项目中的回调会起作用
kill `pgrep -f ${MAIN_CLASS}` 2>/dev/null

# 以下代码与上述代码等价
# kill $(pgrep -f ${MAIN_CLASS}) 2>/dev/null

