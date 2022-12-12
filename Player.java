import java.io.Serializable;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Player extends GameObject implements Serializable {
    Group group;
    ImageView item;
    ImageView view;
    Buster buster;
    Cannon cannon;
    Flare busterFlare;
    PositionManager position;
    SpriteManager sprites = new SpriteManager();

    Text healthText;
    int health = 100;
    boolean hasItem = false;
    public static Font healthFont;

    public Player(String path, int health) {
        // Set the health
        this.health = health;

        // Instantiate the sprite manager and load the player sprites
        sprites.load("move", path + "/move/move_", ".png", 0, 7);
        sprites.load("damaged", path + "/damaged/damaged_", ".png", 0, 7);
        sprites.load("shoot", path + "/shoot/shoot_", ".png", 0, 11);
        sprites.load("cannon", "./assets/weapons/cannon_", ".jpg", 1, 1);

        // Instantiate the buster weapon
        buster = new Buster(path);
        busterFlare = new Flare("./assets/effects");

        // Instantiate the cannon weapon
        cannon = new Cannon(path);

        // Instantiate the item view
        item = new ImageView(sprites.getImage("cannon", 0));
        setHasItem(false);

        // Instantiate the player view
        view = new ImageView(sprites.getImage("move", 0));

        // Instantiate the health font
        if (healthFont == null)
        healthFont = Font.loadFont(getClass().getResourceAsStream("./assets/fonts/BN6FontSmExt.ttf"), 100);

        // Instantiate the health text
        healthText = new Text(health + "");
        healthText.setFont(healthFont);
        healthText.setStroke(Color.BLACK);
        healthText.setFill(Paint.valueOf("#f5fcfd"));
        healthText.setTextAlignment(TextAlignment.CENTER);
        healthText.setStrokeWidth(4);
        healthText.setOpacity(0.9);

        // Instantiate the group
        group = new Group(view, item, healthText);
    }

    // Return the group for the player
    public Group getGroup() {
        return group;
    }

    // Returns the buster instance for the player
    public Buster getBuster() {
        return buster;
    }

    // Returns the cannon instance for the player
    public Cannon getCannon() {
        return cannon;
    }

    // Returns whether the player has an item
    public boolean hasItem() {
        return hasItem;
    }

    // Sets whether the player has an item
    public void setHasItem(boolean hasItem) {
        this.hasItem = hasItem;
        item.setOpacity(hasItem ? 1 : 0);
    }

    // Sets the enemy for the player
    public void setEnemy(Player enemy) {
        // Set the targets for the buster
        buster.setTargets(this, enemy);

        // Set the targets for the cannon
        cannon.setTargets(this, enemy);
    }

    // Tracks the number of frames the player is invincible for
    int invincibilityFrames = 0;

    // Makes the player invincible for the given number of frames
    void makeInvincible(int frames) {
        // Do not make the player invincible if they are already invincible
        if (isInvicible()) return;

        // Set the invincibility frames
        invincibilityFrames = frames;

        // Decrease the player's opacity to indicate invincibility
        group.setOpacity(0.7);
    }

    // Returns whether this player is invincible
    public boolean isInvicible() {
        return invincibilityFrames > 0;
    }

    // Cancels any pending animations / actions from the player
    void cancelPending() {
        // Cancel movement if the player is moving
        if (isPlayerMoving) {
            // Set the frame to the last one in the animation which effectively cancels the animation / action
            movingFrame = 8;
        }

        // Cancel buster firing if the player is firing
        if (isFiringBuster) setFiringBuster(false);

        // Cancel cannon firing if the player is firing
        if (isFiringCannon) setFiringCannon(false);
    }

    // Tracks damage frames for the heavy damage animation
    int heavyDamageFrames = -1;

    // Inflicts damage to the player based on the provided damage and heavy flag
    public void inflictDamage(int damage, boolean heavy) {
        // Decrement and update the health text
        health = Math.max(0, health - damage);
        healthText.setText(health + "");
        healthText.setFill(Paint.valueOf("#ff845a"));

        // Determine if the player is dead
        if (health == 0) {
            // TODO - When the player dies, do something
            System.out.println("Some Player died");
        } else if (heavy) {
            // Cancel pending actions
            cancelPending();

            // Set the heavy damage frames to 0 to play damage animation
            heavyDamageFrames = 0;

            // Make invincible for 120 frames aka. 2 seconds
            makeInvincible(120);
        } else {
            // Make the player invincible for 5 frames
            makeInvincible(6);
        }
    }

    // Returns the position manager instance
    public PositionManager getPositionManager() {
        return position;
    }

    // Mounts the player view to the given root
    public void mount(StackPane root, double width, double height, double offsetX) {
        // Instantiate the position manager
        position = new PositionManager(width, offsetX - (height * 0.1), -height * 0.6);

        // Set the view width and height
        view.setFitWidth(width);
        view.setFitHeight(height);

        // Set the item width and height
        item.setFitWidth(width * 0.15);
        item.setFitHeight(height * 0.15);
        item.setTranslateX(width * 0.5);
        item.setTranslateY(-height * 0.175);

        // Update local player position to 1,1 (middle)
        updatePosition(1, 1, false);

        // Mount the buster weapon to the root
        buster.mount(group, width * 0.75, height * 0.75, height * 0.05, height * 0.125);

        // Mount the cannon weapon to the root
        cannon.mount(group, width, height, height * 0.45, -height * 0.31);

        // Mount the buster flare to the root
        busterFlare.mount(group, width * 0.75, height * 0.75, height * 0.23, height * 0.015);

        // Adjust the health text position
        healthText.setTranslateX(width * 0.4);
        healthText.setTranslateY(height * 0.63);

        // Mount the view to the root
        root.getChildren().add(group);
    }

    // Sets the player horizontal direction
    public void setDirection(boolean left) {
        // Set the inverse absolute flag of the position manager
        position.setInverseAbsolute(left);
        renderPosition();

        // Set the scale of the player based on the direction
        group.setScaleX(left ? -1 : 1);

        // Inverse the text scaleX to prevent it from flipping
        healthText.setScaleX(left ? -1 : 1);
        if (left) {
            // Adjust the health text horizontal position
            healthText.setTranslateX(healthText.getTranslateX() * 0.9);
        }
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

            // Set the health text opacity to 0.5
            healthText.setOpacity(0.4);
        } else {
            // Render the position instantly
            renderPosition();
        }
    }

    // Moves the player based on the slot index change values
    int movingFrame = 0;
    boolean isPlayerMoving = false;
    public boolean move(int x, int y) {
        // Don't move if the player is damaged, moving or firing a weapon
        if (heavyDamageFrames >= 0 || isPlayerMoving || isFiringBuster || isFiringCannon) return false;

        // Retrieve current position and constrain the new position
        int[] position = this.position.getPosition();
        x = Math.max(0, Math.min(2, position[0] + x));
        y = Math.max(0, Math.min(2, position[1] + y));

        // Update the position if there is a change
        if (x != position[0] || y != position[1]) {
            updatePosition(x, y, true);
            return true;
        } else {
            return false;
        }
    }

    // Tracks buster firing state and animation
    int busterFireFrame = 0;
    boolean isFiringBuster = false;

    // Attempts to update the firing state of the buster
    public boolean setFiringBuster(boolean firing) {
        // Reject if the player is damaged, moving or firing a weapon
        if (heavyDamageFrames >= 0 || isPlayerMoving || isFiringCannon || isFiringBuster == firing) return false;
        
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

        // Return true to indicate the firing state was updated
        return true;
    }

    // Tracks cannon firing state and animation
    int cannonFireFrame = 0;
    boolean isFiringCannon = false;

    // Attempts to update the firing state of the cannon
    public boolean setFiringCannon(boolean firing) {
        // Reject if the player is damaged, moving or firing a weapon
        if (heavyDamageFrames >= 0 || isPlayerMoving || isFiringBuster || isFiringCannon == firing) return false;
        
        // Determine if the player is firing or not
        if (isFiringCannon) {
            // Mark the player as not firing and update sprite to neutral
            isFiringCannon = false;
            view.setImage(sprites.getImage("move", 0));

            // Hide the cannon if visible
            if (cannon.isVisible()) cannon.setVisible(false);
        } else {
            // Reset the fire frame, set firing flag
            cannonFireFrame = 0;
            isFiringCannon = true;
        }

        // Return true to indicate the firing state was updated
        return true;
    }

    public void Update() {
        // Check if the player is damaging
        if (heavyDamageFrames >= 0) {
            // Play the heavy damage animation
            int throttle = 6;
            if (heavyDamageFrames / throttle <= 7) {
                // Update the damage sprite until last frame
                if (heavyDamageFrames % throttle == 0) view.setImage(sprites.getImage("damaged", heavyDamageFrames / throttle));
            
                // Increment the damage frame
                heavyDamageFrames++;
            } else {
                // Reset the damage frames
                heavyDamageFrames = -1;
            }
        }

        // Check if the player is moving
        if (isPlayerMoving) {
            // Check if the move frame is at the end
            if (movingFrame >= 8) {
                // Reset the moving flag
                isPlayerMoving = false;
                healthText.setOpacity(1);
            } else {
                // Render the position change on the 4th frame
                if (movingFrame == 4) renderPosition();

                // Set the view image to the next move frame and increment the move frame
                view.setImage(sprites.getImage("move", movingFrame));

                // Increment the moving frame
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
                    // Show the buster and flare on the 4th frame
                    if (busterFireFrame == 4 * throttle) {
                        buster.setVisible(true);
                        busterFlare.setVisible(true);
                    }
                    
                    // Set the view image to the next shoot frame
                    view.setImage(sprites.getImage("shoot", busterFireFrame / throttle));
                }

                // Tick the buster and flare
                if (buster.isVisible()) buster.Update();
                if (busterFlare.isVisible()) busterFlare.Update();

                // Increment the fire frame
                busterFireFrame++;
            }
        } else if (isFiringCannon) {
            // Check if the firing frame is throttled
            int throttle = 4;
            if (cannonFireFrame % throttle == 0) {
                // Show the cannon on the 4th frame
                if (cannonFireFrame == 4 * throttle) cannon.setVisible(true);

                // Update the player sprites up to the 4th frame
                if (cannonFireFrame <= 4 * throttle) {
                    // Update the player shoot sprite
                    view.setImage(sprites.getImage("shoot", cannonFireFrame / throttle));
                }
            }

            // Increment the cannon fire frames and Update the cannon
            cannonFireFrame++;
            if (cannon.isVisible()) {
                cannon.Update();
            } else if (cannonFireFrame > 4 * throttle) {
                // Reset the firing state
                setFiringCannon(false);
            }
        }

        // Determine if the player is invincible
        if (isInvicible()) {
            // Decrement and Reset the opacity back to make the player vulnerable
            invincibilityFrames--;
            if (invincibilityFrames == 0) {
                // Reset the opacity and health text color
                healthText.setFill(Paint.valueOf("#f5fcfd"));
                group.setOpacity(1);
            }
        }
    }
}
