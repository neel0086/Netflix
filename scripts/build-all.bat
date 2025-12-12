@echo off
set services=upload-service

cd ..
for %%s in (%services%) do (
    echo Building %%s...
    cd %%s
    call mvn clean package -DskipTests
    cd ..
)
