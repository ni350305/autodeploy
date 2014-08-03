@echo off
echo [INFO] Install jar to local repository.

cd %~dp0
cd ..
set MAVEN_OPTS=-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=9797,suspend=n,server=y -Xms256m -Xmx512m -XX:MaxPermSize=256m -Dspring.profiles.active=local
call mvn tomcat6:run
cd bin
pause