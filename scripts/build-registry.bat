@echo off

echo Building ServiceRegistry
cd ..
cd UrlShortener
call mvn clean package -DskipTests
cd ..
