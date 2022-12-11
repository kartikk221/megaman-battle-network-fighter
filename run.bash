# Change this to your computer's path to the JavaFX SDK's lib folder so "/path/to/javafx-sdk-19/lib"
JAVAFX_PATH="F:/javafx-19-sdk/lib";

# Build the application
javac --module-path $JAVAFX_PATH --add-modules javafx.controls,javafx.media,javafx.fxml GameManager.java;

# Run the application
java --module-path $JAVAFX_PATH --add-modules javafx.controls,javafx.media,javafx.fxml GameManager;

# Delete the class files
rm *.class;