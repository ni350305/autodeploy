@echo off
echo [INFO] Runing...
cd %~dp0
cd ..
call mvn versions:update-parent
pause