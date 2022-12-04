package src;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class MegamanPlayer extends Player {
    Scene scene;

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
            if (code == KeyCode.W || code == KeyCode.UP) move(0, 1);

            // Check if the key code is "A" or "Left"
            if (code == KeyCode.A || code == KeyCode.LEFT) move(1, 0);

            // Check if the key code is "S" or "Down"
            if (code == KeyCode.S || code == KeyCode.DOWN) move(0, -1);

            // Check if the key code is "D" or "Right"
            if (code == KeyCode.D || code == KeyCode.RIGHT) move(-1, 0);
        });
    }
}
