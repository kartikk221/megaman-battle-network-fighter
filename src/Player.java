package src;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class Player extends GameObject {
    Group group;
    ImageView view;
    Buster buster;
    Flare busterFlare;
    PositionManager position;
    SpriteManager sprites = new SpriteManager();

    public Player(String path) {
        // Instantiate the sprite manager and load the player sprites
        sprites.load("move", path + "/move/move_", ".png", 0, 7);
        sprites.load("damaged", path + "/damaged/damaged_", ".png", 0, 7);
        sprites.load("shoot", path + "/shoot/shoot_", ".png", 0, 11);

        // Instantiate the buster weapon
        buster = new Buster(path);
        busterFlare = new Flare("./assets/effects");

        // Instantiate the view and group
        view = new ImageView(sprites.getImage("move", 0));
        group = new Group(view);
    }

    public PositionManager getPositionManager() {
        return position;
    }

    // Mounts the player view to the given root
    public void mount(StackPane root, double width, double height, double offsetX) {
        // Instantiate the position manager
        position = new PositionManager(width, offsetX - (height * 0.3), -height * 0.5);

        // Set the view width and height
        view.setFitWidth(width);
        view.setFitHeight(height);

        // Update local player position to 1,1 (middle)
        updatePosition(1, 1, false);

        // Mount the buster weapon to the root
        buster.mount(group, width * 0.75, height * 0.75, height * 0.05, height * 0.125);

        // Mount the buster flare to the root
        busterFlare.mount(group, width * 0.75, height * 0.75, height * 0.25, height * 0.015);

        // Mount the view to the root
        root.getChildren().add(group);
    }

    // Sets the player horizontal direction
    public void setDirection(boolean left) {
        group.setScaleX(left ? -1 : 1);
    }

    // Private booleans
    void renderPosition() {
        group.setTranslateX(position.getTranslationX());
        group.setTranslateY(position.getTranslationY());
    }

    // Sets the players slot based position
    void updatePosition(int x, int y, boolean animate) {
        position.setPosition(x, y);
        if (animate) {
            // Mark the player as moving
            movingFrame = 0;
            isPlayerMoving = true;
        } else {
            // Render the position instantly
            renderPosition();
        }
    }

    // Moves the player based on the slot index change values
    int movingFrame = 0;
    boolean isPlayerMoving = false;
    public void move(int x, int y) {
        // Don't move if the player is already moving or firing
        if (isPlayerMoving || isFiringBuster) return;

        // Retrieve current position and constrain the new position
        int[] position = this.position.getPosition();
        x = Math.max(0, Math.min(2, position[0] + x));
        y = Math.max(0, Math.min(2, position[1] + y));

        // Update the position if there is a change
        if (x != position[0] || y != position[1]) updatePosition(x, y, true);
    }

    int busterFireFrame = 0;
    boolean isFiringBuster = false;
    public void setFiringBuster(boolean firing) {
        // Reject if the player is already moving or firing
        if (isPlayerMoving || isFiringBuster == firing) return;
        
        // Determine if the player is firing or not
        if (isFiringBuster) {
            // Mark the player as not firing, hide the buster and reset the player frame to neutral
            isFiringBuster = false;
            buster.setVisible(false);
            busterFlare.setVisible(false);
            view.setImage(sprites.getImage("move", 0));
        } else {
            // Reset the fire frame and set firing flag
            busterFireFrame = 0;
            isFiringBuster = true;
        }
    }

    public void Update() {
        // Check if the player is moving
        if (isPlayerMoving) {
            // Check if the move frame is at the end
            if (movingFrame >= 8) {
                // Reset the moving flag
                isPlayerMoving = false;
            } else {
                // Render the position change on the 4th frame
                if (movingFrame == 4) renderPosition();

                // Set the view image to the next move frame and increment the move frame
                view.setImage(sprites.getImage("move", movingFrame));
                movingFrame++;
            }
        } else if (isFiringBuster) {
            // Check if the fire frame is at the end
            int throttle = 4;
            if (busterFireFrame >= 12 * throttle) {
                // Loop back to 8th frame
                busterFireFrame = 8 * throttle;
            } else {
                // Increment the fire frame and update every 4 frames
                if (busterFireFrame % throttle == 0) {
                    // Show the buster and flare on the 3rd frame
                    if (busterFireFrame == 3 * throttle) {
                        buster.setVisible(true);
                        busterFlare.setVisible(true);
                    }
                    
                    // Set the view image to the next shoot frame
                    view.setImage(sprites.getImage("shoot", busterFireFrame / throttle));
                }

                // Tick the buster and flare
                if (buster.isVisible()) buster.TickUpdate();
                if (busterFlare.isVisible()) busterFlare.TickUpdate();

                // Increment the fire frame
                busterFireFrame++;
            }
        }
    }
}
