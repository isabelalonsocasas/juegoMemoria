@echo off
setlocal
cd /d "%~dp0Sources"

where javac >nul 2>nul
if errorlevel 1 (
    echo No se encuentra javac. Instala un JDK 17 o posterior y anadelo al PATH.
    pause
    exit /b 1
)

if not exist "..\build" mkdir "..\build"
javac -encoding UTF-8 -d "..\build" domain\*.java ui\*.java util\*.java
if errorlevel 1 (
    echo No se ha podido compilar el proyecto.
    pause
    exit /b 1
)

java -cp "..\build" domain.APP
if errorlevel 1 pause
endlocal
