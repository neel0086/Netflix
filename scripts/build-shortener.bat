@echo off

echo Building UrlShortener
cd ..
cd UrlShortener
call mvn clean package -DskipTests
cd ..


