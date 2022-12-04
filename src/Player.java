package src;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class Player extends GameObject {
    ImageView view;
    SpriteManager sprites;
    PositionManager position;

    public Player(String path) {
        // Instantiate the sprite manager
        sprites = new SpriteManager();

        // Load player sprites into memory
        sprites.load("move", path + "/move/move_", ".png", 0, 7);
        sprites.load("damaged", path + "/damaged/damaged_", ".png", 0, 7);
        sprites.load("shoot", path + "/shoot/shoot_", ".png", 0, 11);

        // Instantiate the view with the first move frame
        view = new ImageView(sprites.getImage("move", 0));
    }

    // Mounts the player view to the given root
    public void mount(StackPane root, double width, double height, double offsetX) {
        // Instantiate the position manager
        position = new PositionManager(width, offsetX - (height * 0.3), -height * 0.5);

        // Set the view width and height
        view.setFitWidth(width);
        view.setFitHeight(height);

        // Update local position to 1,1 (middle)
        updatePosition(1, 1, false);

        // Mount the view to the root
        root.getChildren().add(view);
    }

    // Sets the player horizontal direction
    public void setDirection(boolean left) {
        if (left) {
            view.setScaleX(-1);
        } else {
            view.setScaleX(1);
        }
    }

    // Private booleans
    int moveFrame = 0;
    boolean isMoving = false;

    // Sets the players slot based position
    void updatePosition(int x, int y, boolean animate) {
        // Set the position
        position.setPosition(x, y);
        if (animate) {
            // Mark the player as moving
            moveFrame = 0;
            isMoving = true;
        } else {
            // Sync the view position with the position manager
            view.setTranslateX(position.getTranslationX());
            view.setTranslateY(position.getTranslationY());
        }
    }

    // Moves the player based on the slot index change values
    public void move(int x, int y) {
        // Don't move if the player is already moving
        if (isMoving) return;

        // Retrieve current position
        int[] position = this.position.getPosition();

        // Ensure the new position is within bounds of 0 - 2
        x = Math.max(0, Math.min(2, position[0] + x));
        y = Math.max(0, Math.min(2, position[1] + y));

        // Update the position if there is a change
        if (x != position[0] || y != position[1]) {
            updatePosition(x, y, true);
        }
    }

    public void Start() {}

    public void Update() {
        // Check if the player is moving
        if (isMoving) {
            // Check if the move frame is at the end
            if (moveFrame >= 8) {
                // Reset the move frame
                moveFrame = 0;

                // Mark the player as not moving
                isMoving = false;
            } else {
                // Sync position on the 4th frame
                if (moveFrame == 4) {
                    // Sync the view position with the position manager
                    view.setTranslateX(position.getTranslationX());
                    view.setTranslateY(position.getTranslationY());
                }

                // Set the view image to the next move frame
                view.setImage(sprites.getImage("move", moveFrame));

                // Increment the move frame
                moveFrame++;
            }
        }
    }
}
