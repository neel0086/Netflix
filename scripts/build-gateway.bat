UrlRedirectiona@echo off

echo Building ApiGateway
cd ..
cd UrlShortener
call mvn clean package -DskipTests
cd ..
