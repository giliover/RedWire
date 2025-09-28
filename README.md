# Red Wire 
O Red wire é um projeto de Campo Minado android ( Kotlin ).
O principal objetivo é criar uma aplicação com programação orientada à objeto para uma disciplina de paradigma de programação orientado à objeto.

## Requisitos

Para compilar e executar este projeto, você precisará dos seguintes softwares instalados e configurados corretamente:

* **Java Development Kit (JDK) 24 ou superior**:
    * Verifique sua versão do JDK com `java -version` no terminal.
    * Se não tiver, você pode baixar o OpenJDK ou outra distribuição.
* **Android SDK (Standalone)**:
    * Embora o Android Studio seja a forma mais fácil de obter o SDK, você pode baixá-lo separadamente. Isso inclui ferramentas como `build-tools`, `platform-tools` (com `adb`), e plataformas Android.
    * Você pode instalar via linha de comando usando `sdkmanager` (parte das Command Line Tools do Android SDK).
    * **Configuração da variável de ambiente `ANDROID_HOME`**: Defina esta variável para apontar para o diretório raiz do seu SDK do Android. Por exemplo:
        * `export ANDROID_HOME=$HOME/Android/Sdk` (Linux/macOS)
        * `set ANDROID_HOME=C:\Users\SeuUsuario\AppData\Local\Android\Sdk` (Windows, ajuste o caminho)
    * **Adicionar `platform-tools` ao PATH**: Adicione o diretório `platform-tools` do seu SDK ao seu PATH para poder usar `adb` diretamente. Por exemplo:
        * `export PATH=$PATH:$ANDROID_HOME/platform-tools` (Linux/macOS)
* **Gradle**:
    * O sistema de build utilizado pelo Android. O projeto já deve incluir o wrapper Gradle (`gradlew`), então uma instalação global não é estritamente necessária, mas é útil para outros projetos.
* **Um dispositivo Android ou Emulador**:
    * Para instalar e testar o aplicativo.

## Como compilar e instalar (Apenas Linha de Comando)

Siga estes passos para compilar o projeto e instalar o aplicativo em um dispositivo ou emulador, utilizando apenas a linha de comando.

1.  **Navegue até a pasta raiz do projeto**:
    Abra seu terminal ou prompt de comando e navegue até o diretório onde este README e o arquivo `build.gradle` principal do projeto estão localizados.

    ```bash
    cd /caminho/para/seu/projeto/android
    ```

2.  **Compile o projeto e gere o APK de depuração**:
    Execute o wrapper Gradle para construir o projeto. Isso irá compilar o código Kotlin, empacotar os recursos e gerar um arquivo APK.

    ```bash
    # Para Linux/macOS
    ./gradlew assembleDebug

    # Para Windows
    gradlew.bat assembleDebug
    ```
    * Este comando irá compilar o aplicativo e gerar um arquivo APK de depuração. O APK gerado geralmente estará localizado em `app/build/outputs/apk/debug/app-debug.apk`.

3.  **Prepare seu dispositivo/emulador**:
    * **Dispositivo Físico**:
        * Conecte seu dispositivo Android ao computador via cabo USB.
        * Certifique-se de que o "Modo Desenvolvedor" e a "Depuração USB" estejam ativados nas configurações do seu dispositivo.
        * Verifique se o ADB reconhece seu dispositivo: `adb devices`. Você deverá ver seu dispositivo listado.
    * **Emulador**:
        * Você precisará ter um emulador configurado e iniciado. Você pode usar o AVD Manager (se o instalou via Command Line Tools) ou outro emulador.

4.  **Instale o APK no seu dispositivo/emulador**:
    Use o Android Debug Bridge (ADB) para instalar o APK gerado no dispositivo ou emulador conectado.

    ```bash
    adb install app/build/outputs/apk/debug/app-debug.apk
    ```
    * Se o comando `adb` não for reconhecido, certifique-se de que o diretório `platform-tools` do seu SDK Android está incluído na sua variável de ambiente `PATH` (conforme mencionado na seção de Requisitos).

5.  **Inicie o aplicativo**:
    Após a instalação bem-sucedida, o aplicativo estará disponível na lista de aplicativos do seu dispositivo ou emulador. Você pode iniciá-lo manualmente na interface do usuário ou via ADB.

    ```bash
    # Exemplo: Iniciar a atividade principal 
    # adb shell am start -n com.example.campominado/MainActivity
    ```

