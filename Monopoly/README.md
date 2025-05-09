This is a Kotlin Multiplatform project targeting Desktop.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that's common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple's CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.


## Prerequisites

### Java Development Kit (JDK)
This project requires Java to be installed on your system. If you encounter the error:
```
ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
```

Follow these steps to set up Java:

1. **Install JDK**: Download and install JDK 17 or later from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://adoptium.net/).

2. **Set JAVA_HOME Environment Variable**:
   - **Windows**:
     1. Right-click on "This PC" or "My Computer" and select "Properties"
     2. Click on "Advanced system settings"
     3. Click on "Environment Variables"
     4. Under "System variables", click "New"
     5. Variable name: `JAVA_HOME`
     6. Variable value: Path to your JDK installation (e.g., `C:\Program Files\Java\jdk-17`)
     7. Click "OK"
     8. Find the "Path" variable in the system variables list, select it and click "Edit"
     9. Click "New" and add `%JAVA_HOME%\bin`
     10. Click "OK" on all dialogs to save the changes

   - **macOS/Linux**:
     1. Edit your shell profile file (`~/.bash_profile`, `~/.zshrc`, etc.)
     2. Add these lines:
        ```
        export JAVA_HOME=/path/to/your/jdk
        export PATH=$JAVA_HOME/bin:$PATH
        ```
     3. Save the file and run `source ~/.bash_profile` (or your appropriate profile file)

3. **Verify Installation**:
   - Open a new command prompt/terminal
   - Run `java -version`
   - You should see the Java version information


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…