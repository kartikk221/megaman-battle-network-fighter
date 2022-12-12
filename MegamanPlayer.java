import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;

public class MegamanPlayer extends Player {
    boolean upPressed = false;
    boolean downPressed = false;
    boolean leftPressed = false;
    boolean rightPressed = false;
    boolean shootBusterPressed = false;
    boolean shootCannonPressed = false;
    AudioManager audio = new AudioManager();
    ItemDropper cannonDropper;

    public MegamanPlayer(int health) {
        // Call the super constructor with the path to the megaman sprites
        super("./assets/player", health);

        // Load the weapon not available sound
        audio.load("weapon-not-available", "./assets/sound/weapon-not-available.wav");
        audio.setVolume("weapon-not-available", 0.2);

        // Set the buster damage to 1
        getBuster().setDamage(1);

        // Set the cannon damage to 50 
        getCannon().setDamage(50);
    }

    // Mounts the player view to the given root
    public void mount(StackPane root, double width, double height, double offsetX) {
        // Call the super method to mount this player
        super.mount(root, width, height, offsetX);

        // Instantiate the cannon dropper
        cannonDropper = new ItemDropper(root, this);
        cannonDropper.setProperties(60 * 30, 50);
        cannonDropper.setItem("./assets/weapons/cannon_1.jpg", width * 0.15, height * 0.15, -width * 0.22, height * 0.1);
    }

    // Binds the key detectors to control the player
    public void bindKeyDetectors(Scene scene) {
        // Bind a listener for key presses to allow for continuous movement
        scene.setOnKeyPressed(e -> {
            // Refresh the key press state
            onKeyChange(e.getCode(), true);
        });

        // Bind a listener for key up to allow for weapon fire stop
        scene.setOnKeyReleased(e -> {
            // Refresh the key press state
            onKeyChange(e.getCode(), false);
        });
    }

    // Handles key press state changes
    void onKeyChange(KeyCode code, boolean pressed) {
        // Check if the key code is "W" or "Up"
        if (code == KeyCode.W || code == KeyCode.UP) upPressed = pressed;

        // Check if the key code is "A" or "Left"
        if (code == KeyCode.A || code == KeyCode.LEFT) leftPressed = pressed;

        // Check if the key code is "S" or "Down"
        if (code == KeyCode.S || code == KeyCode.DOWN) downPressed = pressed;

        // Check if the key code is "D" or "Right"
        if (code == KeyCode.D || code == KeyCode.RIGHT) rightPressed = pressed;

        // Check if the space bar was pressed
        if (code == KeyCode.SPACE) shootBusterPressed = pressed;

        // CHeck if the left control or right control key was pressed
        if (code == KeyCode.CONTROL) {
            // Update the shoot cannon flag
            shootCannonPressed = pressed;

            // Play the weapon not available sound if the cannon is not available
            if (shootCannonPressed && !hasItem()) audio.play("weapon-not-available", false);
        }
    }

    // This method is called every frame
    public void Update() {
        // Move the player based on the key presses
        if (upPressed) move(0, 1);
        if (leftPressed) move(1, 0);
        if (downPressed) move(0, -1);
        if (rightPressed) move(-1, 0);

        // Update the firing state
        // We constantly update state because buster can be held down to fire constantly
        setFiringBuster(shootBusterPressed);

        // Update the cannon firing state only if pressing the cannon button
        if (shootCannonPressed && hasItem()) {
            // Fire the cannon and set the item to not available if it was successfully fired
            boolean fired = setFiringCannon(shootCannonPressed);
            if (fired) setHasItem(false);
        }

        // Update the cannon dropper
        cannonDropper.Update();

        // Call the super Update() to tick the Player class
        super.Update();
    }
}
