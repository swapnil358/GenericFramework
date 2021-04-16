java -jar D:\Desktop_Data\cucumber-sandwich-3.7.0\cucumber-sandwich-3.7.0-jar-with-dependencies.jar -n -f Report/ -o Report/
cd Report

for /F "tokens=2" %%i in ('date /t') do set mydate=%%i
set mytime=%time% 

set mydate1=%mydate:/=% 
set mytime1=%mytime:.=% 
set mytime1=%mytime1::=% 
set datetime=%mydate1%%mytime1% 
set datetime1=%datetime: =% 

rename cucumber-html-reports cucumber-html-reports_%datetime1%
rename RerunReports RerunReports_%datetime1% 

Exit


