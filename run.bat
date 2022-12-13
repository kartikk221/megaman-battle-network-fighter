REM Change this to your computer's path to the JavaFX SDK's lib folder so "F:\javafx-19-sdk\lib"
set JAVAFX_PATH="F:\javafx-19-sdk\lib"

REM Build the application
javac --module-path %JAVAFX_PATH% --add-modules javafx.controls,javafx.media,javafx.fxml SceneManager.java

REM Run the application
java --module-path %JAVAFX_PATH% --add-modules javafx.controls,javafx.media,javafx.fxml SceneManager

REM Delete the class files
del *.class