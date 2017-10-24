@echo off & setlocal ENABLEEXTENSIONS
set BACKUP_PATH=D:\workspaceWeSync\weSync\DB_Backup\Target
set DATABASES=TEST_2017_M
set HOST=localhost
set USERNAME=root
set PASSWORD=123456S
set MYSQL=D:\MySQL
set path_bin_mysql=%MYSQL%\bin\
set YEAR=%date:~0,4%
set MONTH=%date:~5,2%
set DAY=%date:~8,2%
set HOUR=%time:~0,2%
set MINUTE=%time:~3,2%
set SECOND=%time:~6,2%
set DIR=%BACKUP_PATH%
set ADDON="%YEAR%%MONTH%%DAY%_%HOUR%%MINUTE%%SECOND%"
if not exist %DIR% (
mkdir %DIR%
)
if not exist %DIR% (
echo Backup path: %DIR% not exists, create dir failed.
goto exit
)
cd /d %DIR%
echo Start dump databases...
for %%D in (%DATABASES%) do (
echo Dumping database %%D ...
%path_bin_mysql%mysqldump -h%HOST% -u%USERNAME% -p%PASSWORD% --skip-lock-tables %%D > %%D_%ADDON%.sql
)
echo Done
:exit