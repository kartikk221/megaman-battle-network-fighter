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

    public GameScene(String difficulty) {
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

        // Set the player health and enemy health based on the difficulty
        int player_health, enemy_health;
        switch (difficulty) {
            case "Experienced":
                player_health = 350;
                enemy_health = 1000;
                break;
            case "Impossible":
                player_health = 400;
                enemy_health = 1500;
                break;
            default:
                player_health = 200;
                enemy_health = 500;
                break;
        }

        // Instantiate the megaman player
        MegamanPlayer megaman = new MegamanPlayer(player_health);
        megaman.mount(container, player_size, player_size, -width / 2);
        megaman.bindKeyDetectors(scene);

        // Instantiate the enemy player
        EnemyPlayer enemy = new EnemyPlayer(enemy_health);
        enemy.mount(container, player_size, player_size, 0);
        enemy.setDirection(true);

        // Set the enemies for each player
        megaman.setEnemy(enemy);
        enemy.setEnemy(megaman);

        // Load the background music
        audio.load("background", "./assets/sound/background.wav");
        audio.setVolume("background", MainMenuScene.settings.getVolume());
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