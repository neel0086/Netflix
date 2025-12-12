@echo off

echo Building UrlRedirection
cd ..
cd UrlShortener
call mvn clean package -DskipTests
cd ..
