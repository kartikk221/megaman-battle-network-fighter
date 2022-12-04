# Build the application
javac --module-path "F:\javafx-19-sdk\lib" --add-modules javafx.controls,javafx.fxml MegamanFighter.java;

# Run the application
java --module-path "F:\javafx-19-sdk\lib" --add-modules javafx.controls,javafx.fxml MegamanFighter;

# Delete the class files
rm *.class
rm src/*.class