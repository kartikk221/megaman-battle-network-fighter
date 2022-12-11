import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class MegamanPlayer extends Player {
    boolean upPressed = false;
    boolean downPressed = false;
    boolean leftPressed = false;
    boolean rightPressed = false;
    boolean shootBusterPressed = false;
    boolean shootCannonPressed = false;

    public MegamanPlayer(int health) {
        // Call the super constructor with the path to the megaman sprites
        super("./assets/player", health);

        // Set the buster damage to 1
        getBuster().setDamage(1);
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
        if (code == KeyCode.CONTROL) shootCannonPressed = pressed;
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
        if (shootCannonPressed) setFiringCannon(shootCannonPressed);

        // Call the super Update() to tick the Player class
        super.Update();
    }
}
