package src;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class Buster extends GameObject {
    Player owner;
    ImageView view;
    SpriteManager sprites;

    public Buster(Player owner) {
        // Set the owner
        this.owner = owner;

        // Instantiate the sprite manager
        sprites = new SpriteManager();

        // Load weapon sprites into memory
        sprites.load("shoot", "./assets/player/buster/buster_", ".png", 0, 3);
    
        // Instantiate the view with the first shoot frame
        view = new ImageView(sprites.getImage("shoot", 0));

        // Hide the view by default
        view.setVisible(false);
    }

    double offsetX;
    double offsetY;

    // Mounts the Buster view to the given root
    public void mount(StackPane root, double width, double height, double offsetX, double offsetY) {
        // Store the offsets
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        
        // Set the view width and height
        view.setFitWidth(width);
        view.setFitHeight(height);

        // Mount the view to the root
        root.getChildren().add(view);
    }

    boolean isVisible = false;
    public void setVisible(boolean visible) {
        if (isVisible == visible) return;

        // Set the view visibility
        isVisible = visible;

        // Sync position if visible
        if (isVisible) syncPosition();

        // Update the view visibility
        view.setVisible(visible);
    }

    boolean isLeft = false;

    // Sets the weapon horizontal direction
    public void setDirection(boolean left) {
        isLeft = left;
        if (left) {
            view.setScaleX(-1);
        } else {
            view.setScaleX(1);
        }
    }

    public void Start() {}

    void syncPosition() {
        // Track this view to the owner's position
        PositionManager ownerPosition = owner.getPositionManager();
        double ownerX = ownerPosition.getTranslationX();
        double ownerY = ownerPosition.getTranslationY();

        // Update the view position
        view.setTranslateX(ownerX + offsetX * (isLeft ? -1 : 1));
        view.setTranslateY(ownerY + offsetY);
    }

    int frame = 0;
    public void Update() {
        // Check if the weapon is visible
        if (isVisible) {
            // Sync the weapon position
            syncPosition();

            // Check if we reached the last frame
            if (frame >= 4 * 3) {
                // Loop back to the 0th frame
                frame = 0;
            } else {
                // Increment the frame every 3 frames
                if (frame % 3 == 0) 
                    view.setImage(sprites.getImage("shoot", frame / 3));

                // Increment the frame
                frame++;
            }
        }
    }
}
