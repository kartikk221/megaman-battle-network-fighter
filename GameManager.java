import src.*;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
 
public class GameManager extends Application implements Thread.UncaughtExceptionHandler {
    // Singleton instance
    public static GameManager Instance;

    // Private variables
    Scene scene;
    Background background;
    BattleGround battleGround;
    final StackPane root = new StackPane();
    public static AudioManager sharedAudio = new AudioManager();

    public static void main(String[] args) {
        launch(args);
    }
    
    public void start(Stage stage) {
        try {
            // Set the singleton instance
            Instance = this;
            if (Instance != this) {
                System.out.println("GameManager is a singleton class. Only one instance can exist at a time.");
                System.exit(1);
            }

            // Set the title of the stage
            stage.setTitle("Megaman Fighter Game");

            // Load shared audio tracks into memory
            sharedAudio.load("background", "./assets/sound/background.wav");

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
            EnemyPlayer enemy = new EnemyPlayer();
            enemy.mount(root, player_size, player_size, 0);
            enemy.setDirection(true);

            // Disable resizing and show the stage
            stage.setResizable(false);
            stage.show();

            // Play the background music
            sharedAudio.setVolume("background", 0.1);
            sharedAudio.play("background", true);

            // Start the game loop
            GameObject.beginTicking();
            System.out.println("Ticking " + GameObject.objects.size() + " objects every " + GameObject.frame_rate + " framers per second.");
        } catch (Exception e) {
            System.out.println("An error occurred while starting the game: ");
            System.out.println(e);
        }
    }

    @Override
    public void stop() {
        // Exit the application
        System.out.println("Closing application...");
        System.exit(0);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("Uncaught exception: " + e);
    }
}