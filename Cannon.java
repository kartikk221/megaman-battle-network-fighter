import javafx.scene.image.ImageView;

public class Cannon extends Weapon {
    int throttle = 4;

    public Cannon(String path) {
        super(path);
    }

    // Loads the audio for the Buster
    protected void loadAudio(String path, AudioManager audio) {
        // Load the audio
    }

    // Loads the sprites for the Buster
    protected void loadSprites(String path, SpriteManager sprites) {
        // Load the sprites
        sprites.load("shoot", path + "/cannon/cannon_", ".png", 1, 11);
    }

    // Initializes the Buster view
    protected void initialize(ImageView view, SpriteManager sprites, AudioManager audio) {
        // Set the initial image and hide the view
        view.setImage(sprites.getImage("shoot", 0));
        setVisible(false);
    }

    // Called when the visibility of the Buster changes
    protected int onVisiblilityChange(boolean visible, int frame) {
        // Start from 4th frame on visibility show
        return visible ? 4 : 0;
    }

    // Animates the Buster during active frames along with playing the fire sound
    protected int UpdateFrame(int frame, ImageView view, SpriteManager sprites, AudioManager audio) {
        // Determine if end of animation is reached by
        // Multiplying and divide the frame by 3 to throttle the animation
        if (frame >= 10 * throttle) {
            // Attempt damage on the enemy and play hit sound if damage was dealt
            attemptDamage(true);

            // Reset the visibility to hide the cannon after firing
            setVisible(false);

            // Reset the frame
            return 0;
        } else {
            if (frame % throttle == 0) view.setImage(sprites.getImage("shoot", frame / throttle));
            return frame + 1;
        }
    }
}
