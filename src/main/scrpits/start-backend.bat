@echo off
title inventory - Backend Server
echo ========================================
echo Starting Inventory API
echo ========================================
echo.

echo Checking Java installation...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install Java Runtime Environment 24 or later
    pause
    exit /b 1
)

echo Java found successfully!
echo Starting backend server...
echo.

java -jar student-management-api-1.0.0.jar --spring.profiles.active=prod

echo.
echo Server stopped.
pause