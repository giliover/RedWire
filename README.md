# Red Wire 
Red wire is a Minesweeper project for Android (Kotlin).
The main goal is to create an application with object-oriented programming for an object-oriented programming paradigm course.

## Requirements

To build and run this project, you will need the following software installed and properly configured:

* **Java Development Kit (JDK) 24**:
    * Check your JDK version with `java -version` in your terminal.
* **Android SDK (Standalone)**: 
    * Although Android Studio is the easiest way to get the SDK, you can download it separately. This includes tools like `build-tools`, `platform-tools` (with `adb`), and Android platforms.
    * You can install it using the command line tool `sdkmanager` (part of the Android SDK Command Line Tools).
    * **Setting the `ANDROID_HOME` environment variable**: Set this variable to point to the root directory of your Android SDK. For example:
        * `export ANDROID_HOME=$HOME/Android/Sdk` (Linux/macOS)
        * `set ANDROID_HOME=C:\Users\YourUser\AppData\Local\Android\Sdk` (Windows, adjust the path)
    * **Add `platform-tools` to the PATH**: Add the `platform-tools` directory from your SDK to your PATH to use `adb` directly. For example:
        * `export PATH=$PATH:$ANDROID_HOME/platform-tools` (Linux/macOS)
* **Gradle**:
    * The build system used by Android. The project should already include the Gradle wrapper (`gradlew`), so a global installation is not strictly required, but may be useful for other projects.
* **An Android device or Emulator**:
    * To install and test the application.

## How to build and install (Command Line Only)

Follow these steps to build the project and install the application on a device or emulator using only the command line.

1.  **Navigate to the project root folder**:
    Open your terminal or command prompt and go to the directory where this README and the main `build.gradle` file are located.

    ```bash
    cd /path/to/your/android/project
    ```

2.  **Build the project and generate the debug APK**:
    Run the Gradle wrapper to build the project. This will compile the Kotlin code, package the resources, and generate an APK file.

    ```bash
    # For Linux/macOS
    ./gradlew assembleDebug

    # For Windows
    gradlew.bat assembleDebug
    ```
    * This command will compile the app and generate a debug APK file. The generated APK is usually located at `app/build/outputs/apk/debug/app-debug.apk`.

3.  **Prepare your device/emulator**:
    * **Physical Device**:
        * Connect your Android device to the computer via USB cable.
        * Make sure "Developer Mode" and "USB Debugging" are enabled in your device settings.
        * Check if ADB recognizes your device: `adb devices`. You should see your device listed.
    * **Emulator**:
        * You need to have an emulator configured and running. You can use the AVD Manager (if installed via Command Line Tools) or another emulator.

4.  **Install the APK on your device/emulator**:
    Use Android Debug Bridge (ADB) to install the generated APK on your connected device or emulator.

    ```bash
    adb install app/build/outputs/apk/debug/app-debug.apk
    ```
    * If the `adb` command is not recognized, make sure the `platform-tools` directory from your Android SDK is included in your `PATH` environment variable (as mentioned in the Requirements section).

5.  **Start the application**:
    After successful installation, the app will be available in your device or emulator's app list. You can launch it manually through the UI or via ADB.

    ```bash
    # Example: Start main activity
    # adb shell am start -n com.example.campominado/MainActivity
    ```
