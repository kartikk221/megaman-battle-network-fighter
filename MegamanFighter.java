import src.*;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
 
public class MegamanFighter extends Application implements Thread.UncaughtExceptionHandler {
    // Private variables
    final StackPane root = new StackPane();
    Scene scene;
    Background background;
    BattleGround battleGround;
    AudioManager audioManager;

    public static void main(String[] args) {
        launch(args);
    }
    
    public void start(Stage stage) {
        // Set the title of the stage
        stage.setTitle("Megaman Fighter Game");

        // Load audio tracks into memory
        audioManager = new AudioManager();
        audioManager.load("background", "./assets/sound/background.wav");

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
        battleGround = new BattleGround(root, "./assets/stage.png", width, height);

        // Calculate a relative size for the player
        double player_size = width / 4;

        // Instantiate the megaman player
        MegamanPlayer megaman = new MegamanPlayer(scene);
        megaman.mount(root, player_size, player_size, -width / 2);

        // Instantiate the enemy player
        EnemyPlayer enemy = new EnemyPlayer(scene);
        enemy.mount(root, player_size, player_size, 0);
        enemy.setDirection(true);

        // Disable resizing and show the stage
        stage.setResizable(false);
        stage.show();

        // Play the background music
        audioManager.setVolume("background", 0.1);
        audioManager.play("background", true);
    }

    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("Uncaught exception: " + e);
    }
}