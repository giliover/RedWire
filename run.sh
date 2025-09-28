#!/bin/bash
project_name="sei-spa"

function build() {
    docker run -it --rm \
        -v ~/AndroidStudioProjects/CampoMinado/:/app \
        -v ~/Android/Sdk/:/Android/Sdk/ \
        -e ANDROID_HOME=/Android/Sdk \
        -w /app \
          android-openjdk:24 bash gradlew assembleDebug 

}

case $1 in
"--build") build;;
*) echo "Opção Inválida!" ;;
esac
