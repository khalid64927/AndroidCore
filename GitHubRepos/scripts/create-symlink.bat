@ECHO OFF
REM bat script
REM
REM Purpose of this script is to create Symbolic link
REM
set arg1=%1
if not "%1"=="am_admin" (powershell start -verb runas '%0' am_admin & exit /b)
REM if not "%1"=="am_admin" (powershell start -verb runas '%0' 'am_admin "%~1"' & exit)
cd /D %~dp0
cd ..\
mkdir .git\hooks
cd .git\hooks
del pre-commit.sample
mklink pre-commit ..\..\scripts\pre-commit 
del pre-push.sample
mklink pre-push ..\..\scripts\pre-push
echo "Symbolic link created"
pause

