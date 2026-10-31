@echo off
setlocal
chcp 65001 >nul

rem If JAVA_HOME is set, put its bin folder on PATH for this run.
if defined JAVA_HOME (
    if exist "%JAVA_HOME%\bin\javac.exe" set "PATH=%JAVA_HOME%\bin;%PATH%"
)

where javac >nul 2>nul
if errorlevel 1 (
    echo [ERROR] Java JDK was not found.
    echo Install JDK 17+ from https://adoptium.net
    echo Or set JAVA_HOME to your JDK folder and run this file again.
    pause
    exit /b 1
)

javac -encoding UTF-8 WelcomeApp.java
if errorlevel 1 (
    echo [ERROR] Compilation failed.
    pause
    exit /b 1
)

java WelcomeApp
endlocal
