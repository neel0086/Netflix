@echo off

echo Building upload-service
cd ..
cd url-service
call mvn clean package -DskipTests
cd ..


