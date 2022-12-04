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
    Scene scene;
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

        // Mount the root to the stage
        scene = new Scene(root, width, height);
        stage.setScene(scene);

        // Instantiate the background
        background = new Background(root, "./assets/background.png", width, height);

        // Instantiate the battle ground
        battleGround = new BattleGround(root, "./assets/stage.png", width, height);

        // Calculate a square size for the player that scales to the screen
        double squareSize = width / 4;

        // Instantiate the megaman player
        MegamanPlayer megaman = new MegamanPlayer(scene);
        megaman.mount(root, squareSize, squareSize, -width / 2);

        // Disable resizing
        stage.setResizable(false);

        // Show the stage
        stage.show();
    }
}