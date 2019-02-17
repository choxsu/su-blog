@echo off

setlocal & pushd


rem 启动入口类,该脚本文件用于别的项目时要改这里
set MAIN_CLASS=com.choxsu.ChoxsuConfig

rem Java 命令行参数,根据需要开启下面的配置,改成自己需要的,注意等号前后不能有空格
set APP_BASE_PATH=%~dp0
set CP=%APP_BASE_PATH%config;%APP_BASE_PATH%lib\*
java -Xverify:none %JAVA_OPTS% -cp %CP% %MAIN_CLASS%


endlocal & popd
pause



