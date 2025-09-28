#!/bin/bash
project_name="sei-spa"

function build() {
    docker run -it --rm \
        -v ./:/app \
        -v ~/Android/Sdk/:/Android/Sdk/ \
        -e ANDROID_HOME=/Android/Sdk \
        -w /app \
           openjdk:24 bash gradlew assembleDebug 

}

case $1 in
"--build") build;;
*) echo "Opção Inválida!" ;;
esac
