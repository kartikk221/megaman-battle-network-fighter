package src;

import javafx.scene.Group;
import javafx.scene.image.ImageView;

public class Buster extends GameObject {
    ImageView view;
    SpriteManager sprites = new SpriteManager();
    AudioManager audio = new AudioManager();

    public Buster() {
        // Run at 20 frames per second
        super(20);

        // Load the audio
        audio.load("fire", "./assets/sound/buster-fire.wav");
        audio.setVolume("fire", 0.1);

        // Load the sprites
        sprites.load("shoot", "./assets/player/buster/buster_", ".png", 0, 3);
    
        // Instantiate the view with the first shoot frame
        view = new ImageView(sprites.getImage("shoot", 0));
        view.setVisible(false);
    }

    // Sets the weapon position
    public void setTranslatePosition(double x, double y) {
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
        if (isVisible != visible) {
            isVisible = visible;
            view.setVisible(visible);
        }
    }

    int frame = 0;
    public void Update() {
        // Do not update if the weapon is not visible
        if (!isVisible) return;

        // Determine if end of animation is reached
        if (frame >= 4) {
            // Reset the frame and play the fire sound
            frame = 0;
            audio.play("fire", false);
        } else {
            view.setImage(sprites.getImage("shoot", frame));
            frame++;
        }
    }
}
