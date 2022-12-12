import javafx.scene.image.ImageView;

public class Explosion extends AnimatedObject {
    int throttle = 3;
    boolean playAudio = false;
    public Explosion() {
        super("./assets/effects/explosion");
    }

    // Loads the audio for the explosion
    protected void loadAudio(String path, AudioManager audio) {
        audio.load("explode", "./assets/sound/explosion.wav");
        audio.setVolume("explode", MainMenuScene.settings.getVolume() * 0.5);
    }

    // Loads the sprites for the explosion
    protected void loadSprites(String path, SpriteManager sprites) {
        sprites.load("explosion", path + "/explosion_", ".png", 0, 5);
    }

    // Initializes the explosion
    protected void initialize(ImageView view, SpriteManager sprites, AudioManager audio) {
        // Set the initial image and hide the view
        view.setImage(sprites.getImage("explosion", 0));
        setVisible(false);
    }

    // Called when the visibility of the Buster changes
    protected int onVisiblilityChange(boolean visible, int frame) {
        // Reset the frame on visibility hide
        if (!visible) {
            return 0;
        } else {
            return frame;
        }
    }

    // Updates the play audio flag
    public void setPlayAudio(boolean playAudio) {
        this.playAudio = playAudio;
    }

    // Animates the explosion
    protected int UpdateFrame(int frame, ImageView view, SpriteManager sprites, AudioManager audio) {
        // Determine if end of animation is reached by
        // Multiplying and divide the frame by 3 to throttle the animation
        if (frame >= 6 * throttle) {
            // Reset the frame
            frame = 0;

            // Play the explosion sound
            if (playAudio) audio.play("explode", false);
        } else {
            // Display the frame sprite
            if (frame % throttle == 0) view.setImage(sprites.getImage("explosion", frame / throttle));
            
            // Increment the frame
            frame++;
        }

        // Return the frame
        return frame;
    }
}
