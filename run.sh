#!/bin/bash
project_name="sei-spa"

function exec() {
    docker run -it --rm \
        -v ./:/app \
        -v ~/Android/Sdk/:/Android/Sdk/ \
        -e ANDROID_HOME=/Android/Sdk \
        -w /app \
          android-openjdk:24 bash 

}

case $1 in
"--exec") exec;;
*) echo "Opção Inválida!" ;;
esac
