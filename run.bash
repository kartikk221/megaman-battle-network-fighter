# Build the application
javac --module-path "F:/javafx-19-sdk/lib" --add-modules javafx.controls,javafx.media,javafx.fxml GameManager.java;

# Run the application
java --module-path "F:/javafx-19-sdk/lib" --add-modules javafx.controls,javafx.media,javafx.fxml GameManager;

# Delete the class files
rm *.class;