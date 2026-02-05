@echo off

if "%~1"=="" (
    echo ERROR: New version not specified, please provide parameter 1
    exit /b
)

mvn versions:set -DnewVersion=%1 -DgenerateBackupPoms=false -DprocessAllModules=true
echo New version set to %1