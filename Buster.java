import javafx.scene.image.ImageView;

public class Buster extends AnimatedObject {
    public Buster(String path) {
        super(path);
    }

    // Loads the audio for the Buster
    protected void loadAudio(String path) {
        // Load the audio
        audio.load("fire", "./assets/sound/buster-fire.wav");
        audio.setVolume("fire", 0.1);
    }

    // Loads the sprites for the Buster
    protected void loadSprites(String path) {
        // Load the sprites
        sprites.load("shoot", path + "/buster/buster_", ".png", 0, 3);
    }

    // Initializes the Buster view
    protected void initialize(ImageView view, SpriteManager sprites, AudioManager audio) {
        // Set the initial image and hide the view
        view.setImage(sprites.getImage("shoot", 0));
        view.setVisible(false);
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

    // Animates the Buster during active frames along with playing the fire sound
    protected int UpdateFrame(int frame, ImageView view, SpriteManager sprites, AudioManager audio) {
        // Determine if end of animation is reached
        // Multiply and divide the frame by 3 to slow down the animation
        if (frame >= 4 * 3) {
            // Reset the frame and play the fire sound
            frame = 0;
            audio.play("fire", false);
        } else {
            if (frame % 3 == 0) view.setImage(sprites.getImage("shoot", frame / 3));
            frame++;
        }

        // Return the frame
        return frame;
    }
}
