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
        this.scene = scene;

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
        if (code == KeyCode.SPACE) shootPressed = pressed;
    }

    // This method is called every frame
    public void Update() {
        // Move the player based on the key presses
        if (upPressed) move(0, 1);
        if (leftPressed) move(1, 0);
        if (downPressed) move(0, -1);
        if (rightPressed) move(-1, 0);

        // Update the firing state
        setFiringBuster(shootPressed);

        // Call the super Update() to tick the Player class
        super.Update();
    }
}
