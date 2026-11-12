@echo off
setlocal
cd /d "%~dp0"
if not exist build mkdir build
dir /b *.java > build\sources.txt
javac -encoding UTF-8 -d build @build\sources.txt
if errorlevel 1 exit /b 1
java -Dfile.encoding=UTF-8 -cp build Main %*
exit /b %errorlevel%
