import src.*;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
 
public class MegamanFighter extends Application {
    // Private variables
    final StackPane root = new StackPane();
    Background background;
    BattleGround battleGround;

    public static void main(String[] args) {
        launch(args);
    }
    
    public void start(Stage stage) {
        // Set the title of the stage
        stage.setTitle("Megaman Fighter Game");

        // Retrieve the device width and height
        Rectangle2D screen = Screen.getPrimary().getBounds();
        double width = screen.getWidth();
        double height = screen.getHeight() - 100; // Account for the taskbar and title bar

        // Contain the width and height to ensure a 16:9 aspect ratio
        double aspectRatio = 16.0 / 9.0;
        double newWidth = height * aspectRatio;
        double newHeight = width / aspectRatio;

        // Set the width and height to the smallest of the two
        if (newWidth < width) {
            width = newWidth;
        } else {
            height = newHeight;
        }

        // Instantiate the background
        background = new Background(root, "./assets/background.png", width, height);

        // Instantiate the battle ground
        battleGround = new BattleGround(root, "./assets/stage.png", width, height);

        // Mount the root to the stage
        stage.setScene(new Scene(root, width, height));

        // Disable resizing
        stage.setResizable(false);

        // Show the stage
        stage.show();
    }
}