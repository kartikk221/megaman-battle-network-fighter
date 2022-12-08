import javafx.scene.image.ImageView;

public class Flare extends AnimatedObject {
    public Flare(String path) {
        super(path);
    }

    protected void loadAudio(String path, AudioManager audio) {
        // No audio to load
    }

    protected void loadSprites(String path, SpriteManager sprites) {
        // Load the sprites
        sprites.load("flare", path + "/flare/flare_", ".png", 0, 2);
    }

    protected void initialize(ImageView view, SpriteManager sprites, AudioManager audio) {
        // Set the initial image and hide the view
        view.setImage(sprites.getImage("flare", 0));
        view.setVisible(false);
    }

    protected int onVisiblilityChange(boolean visible, int frame) {
        // Reset the frame on visibility hide
        if (!visible) {
            return 0;
        } else {
            return frame;
        }
    }

    protected int UpdateFrame(int frame, ImageView view, SpriteManager sprites, AudioManager audio) {
        int throttle = 6;
        if (frame >= 2 * throttle) {
            // Reset the frame
            frame = 0;
        } else {
            // Display the throttled frame
            if (frame % throttle == 0)
                view.setImage(sprites.getImage("flare", frame / throttle));

            // Increment the frame
            frame++;
        }

        // Return the frame
        return frame;
    }
}
