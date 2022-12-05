package src;

import javafx.scene.Group;
import javafx.scene.image.ImageView;

public class Buster extends GameObject {
    Player owner;
    ImageView view;
    SpriteManager sprites;

    public Buster(Player owner) {
        // Run at 20 frames per second
        super(20);

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

    // Sets the weapon position
    public void setTranslatePosition(double x, double y) {
        // Set the view position
        view.setTranslateX(x);
        view.setTranslateY(y);
    }

    // Mounts the Buster view to the given root
    public void mount(Group root, double width, double height) {
        // Set the view width and height
        view.setFitWidth(width);
        view.setFitHeight(height);

        // Mount the view to the root
        root.getChildren().add(view);
    }

    boolean isVisible = false;
    public void setVisible(boolean visible) {
        // Ensure the visibility state has changed
        if (isVisible == visible) return;

        // Set the view visibility
        isVisible = visible;
        view.setVisible(visible);
    }

    int frame = 0;
    public void Update() {
        // Check if the weapon is visible
        if (isVisible) {
            // Check if we reached the last frame
            if (frame >= 4) {
                // Loop back to the 0th frame
                frame = 0;
            } else {
                // Increment and display the next frame
                view.setImage(sprites.getImage("shoot", frame));
                frame++;
            }
        }
    }
}
