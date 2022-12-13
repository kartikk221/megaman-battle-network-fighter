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

        // Load the sounds
        audio.load("low-hp", "./assets/sound/low-hp.wav");
        audio.setVolume("low-hp", Math.min(1, MainMenuScene.settings.getVolume() * 2));
        audio.load("weapon-not-available", "./assets/sound/weapon-not-available.wav");
        audio.setVolume("weapon-not-available", Math.min(1, MainMenuScene.settings.getVolume() * 2));

        // Set the buster damage to 1
        getBuster().setDamage(1);

        // Set the cannon damage to 50 
        getCannon().setDamage(50);
    }

    // Mounts the player view to the given root
    public void mount(StackPane root, double width, double height, double offsetX) {
        // Call the super method to mount this player
        super.mount(root, width, height, offsetX);

        // Determine the cannon dropper cycle seconds based on difficulty
        int cycle_seconds = 10;
        int drop_chance_percent = 50;
        String difficulty = MainMenuScene.settings.getDifficulty();
        switch(difficulty) {
            case "Experienced":
                drop_chance_percent = 75;
                break;
            case "Impossible":
                cycle_seconds = 5;
                drop_chance_percent = 75;
                break;
        }

        // Instantiate the cannon dropper
        cannonDropper = new ItemDropper(root, this);
        cannonDropper.setProperties(60 * cycle_seconds, drop_chance_percent);
        cannonDropper.setItem("./assets/weapons/cannon_1.jpg", width * 0.15, height * 0.15, -width * 0.22, height * 0.1);
    }

    // Binds the key detectors to control the player
    public void bindKeyDetectors(Scene scene) {
        // Bind a listener for key presses to allow for continuous movement
        scene.setOnKeyPressed(e -> onKeyChange(e.getCode(), true));

        // Bind a listener for key up to allow for weapon fire stop
        scene.setOnKeyReleased(e -> onKeyChange(e.getCode(), false));
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

    int lowHealthFrames = 0;
    boolean isLowHealth = false;

    // Handles health change events
    public void OnHealthChange(int health) {
        // Consider less than 100 as low health
        isLowHealth = health <= 100;
    }

    // Handle when the player dies
    public void OnDeath() {
        // Display game over screen after 3 seconds
        GameObject.scheduleTick(60 * 3, new Runnable() {
            public void run() {
                // Mount the game over screen
                SceneManager.mountScreen(new GameOverScene("Game Over", "Better luck next time!"));
            }
        });
    }

    // This method is called every frame
    public void Update() {
        // Do not update if the player is dead or enemy is dead
        Player enemy = getEnemy();
        if (isDead() || (enemy != null && enemy.isDead())) {
            // Cancel pending actions
            cancelPending();

            // Tick the explosions if dead
            if (isDead()) TickExplosions();

            // Halt execution
            return;
        }

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

        // Check if the player is low health
        if (isLowHealth) {
            // Increment the low health frames
            lowHealthFrames++;

            // Play the low health sound every 45 frames
            if (lowHealthFrames % 45 == 0) {
                lowHealthFrames = 0;
                audio.play("low-hp", false);
            }
        }
    }
}
