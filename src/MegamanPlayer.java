package src;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class MegamanPlayer extends Player {
    Scene scene;
    boolean upPressed = false;
    boolean downPressed = false;
    boolean leftPressed = false;
    boolean rightPressed = false;
    boolean shootPressed = false;

    public MegamanPlayer(Scene scene) {
        // Call the super constructor with the path to the megaman sprites
        super("./assets/player");

        // Set the scene
        this.scene = scene;

        // Bind a listener for key presses to allow for continuous movement
        scene.setOnKeyPressed(e -> {
            // Get the key code
            KeyCode code = e.getCode();

            // Check if the key code is "W" or "Up"
            if (code == KeyCode.W || code == KeyCode.UP) upPressed = true;

            // Check if the key code is "A" or "Left"
            if (code == KeyCode.A || code == KeyCode.LEFT) leftPressed = true;

            // Check if the key code is "S" or "Down"
            if (code == KeyCode.S || code == KeyCode.DOWN) downPressed = true;

            // Check if the key code is "D" or "Right"
            if (code == KeyCode.D || code == KeyCode.RIGHT) rightPressed = true;

            // Check if the space bar was pressed
            if (code == KeyCode.SPACE) shootPressed = true;
        });

        // Bind a listener for key up to allow for weapon fire stop
        scene.setOnKeyReleased(e -> {
            // Get the key code
            KeyCode code = e.getCode();

            // Check if the key code is "W" or "Up"
            if (code == KeyCode.W || code == KeyCode.UP) upPressed = false;

            // Check if the key code is "A" or "Left"
            if (code == KeyCode.A || code == KeyCode.LEFT) leftPressed = false;

            // Check if the key code is "S" or "Down"
            if (code == KeyCode.S || code == KeyCode.DOWN) downPressed = false;

            // Check if the key code is "D" or "Right"
            if (code == KeyCode.D || code == KeyCode.RIGHT) rightPressed = false;

            // Check if the space bar was pressed
            if (code == KeyCode.SPACE) shootPressed = false;
        });
    }

    public void Update() {
        // Call super update
        super.Update();

        // Move the player based on the key presses
        if (upPressed) move(0, 1);
        if (leftPressed) move(1, 0);
        if (downPressed) move(0, -1);
        if (rightPressed) move(-1, 0);

        // Update the firing state
        setFiring(shootPressed);
    }
}
