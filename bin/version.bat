@echo off
setlocal enabledelayedexpansion

set "version=%~1"
if "%version%"=="" (
    for /f "tokens=2 delims==" %%I in ('wmic os get localdatetime /value') do set "dt=%%I"
    set "version=!dt:~0,14!"
    echo Using date version: !version!
)

endlocal & set "version=%version%"

mvn versions:set -DnewVersion=%version% -DgenerateBackupPoms=false -DprocessAllModules=true
echo New version set to %version%
