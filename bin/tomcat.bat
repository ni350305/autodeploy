@echo off
echo [INFO] Install jar to local repository.

cd %~dp0
cd ..
call mvn tomcat6:run
cd bin
pause