# Build the application
javac --module-path "/Users/kartik/Downloads/javafx-sdk-19/lib" --add-modules javafx.controls,javafx.media,javafx.fxml GameManager.java;

# Run the application
java --module-path "/Users/kartik/Downloads/javafx-sdk-19/lib" --add-modules javafx.controls,javafx.media,javafx.fxml GameManager;

# Delete the class files
rm *.class
rm src/*.class