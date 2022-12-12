import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GameScene extends Mountable {
    // Private variables
    Scene scene;
    Background background;
    ControlsBox controlsBox;
    BattleGround battleGround;
    StackPane container = new StackPane();
    public static AudioManager audio = new AudioManager();

    public GameScene() {
        // Retrieve the screen dimensions
        double[] screenDimensions = SceneManager.getScreenDimensions();
        double width = screenDimensions[0];
        double height = screenDimensions[1];

        // Retrieve the playable window dimensions with a 16:9 aspect ratio
        double[] windowDimensions = SceneManager.getWindowDimensions(screenDimensions);
        double newWidth = windowDimensions[0];
        double newHeight = windowDimensions[1];

        // Set the width and height to the smallest of the two
        if (newWidth < width) {
            width = newWidth;
        } else {
            height = newHeight;
        }

        // Create a scene with the dimensions
        scene = new Scene(container, width, height);

        // Instantiate the background and battle ground
        background = new Background(container, "./assets/background.png", width, height);
        battleGround = new BattleGround(container, "./assets/stage.png", width, height);
        controlsBox = new ControlsBox(container, "./assets/controls.png", width / 3);

        // Calculate a relative size for the player
        double player_size = width / 3.9;

        // Instantiate the megaman player
        MegamanPlayer megaman = new MegamanPlayer(200);
        megaman.mount(container, player_size, player_size, -width / 2);
        megaman.bindKeyDetectors(scene);

        // Instantiate the enemy player
        EnemyPlayer enemy = new EnemyPlayer(500);
        enemy.mount(container, player_size, player_size, 0);
        enemy.setDirection(true);

        // Set the enemies for each player
        megaman.setEnemy(enemy);
        enemy.setEnemy(megaman);

        // Load the background music
        audio.load("background", "./assets/sound/background.wav");
        audio.setVolume("background", 0.1);
    }

    // Mounts the scene to the stage
    public void mount(Stage stage) {
        // Begin ticking the GameObjects
        GameObject.startTicking();

        // Play the background music
        audio.play("background", true);

        // Set the title of the stage
        stage.setTitle("Fight! - Megaman Fighter Game");

        // Sets the scene to the stage
        stage.setScene(scene);

        // Show the stage
        stage.show();
    }

    // Unmounts the scene from the stage
    public void unmount(Stage state) {
        // Purge registered GameObjects
        GameObject.objects.clear();

        // Stop ticking the GameObjects
        GameObject.stopTicking();

        // Stop the background music
        audio.stop("background");

        // Sets the scene to null
        state.setScene(null);

        // Hide the stage
        state.hide();
    }
}