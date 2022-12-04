# Build the application
javac --module-path "F:\javafx-19-sdk\lib" --add-modules javafx.controls,javafx.fxml HelloWorld.java;

# Run the application
java --module-path "F:\javafx-19-sdk\lib" --add-modules javafx.controls,javafx.fxml HelloWorld;

# Delete the class files
rm *.class