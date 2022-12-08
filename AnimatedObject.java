import javafx.scene.Group;
import javafx.scene.image.ImageView;

public abstract class AnimatedObject implements Tickable {
    int frame = 0;
    ImageView view = new ImageView();
    AudioManager audio = new AudioManager();
    SpriteManager sprites = new SpriteManager();

    public AnimatedObject(String path) {
        // Throw an error if the path is empty
        if (path.isEmpty()) throw new IllegalArgumentException("The path cannot be empty");

        // Load the audio
        loadAudio(path);

        // Load the sprites
        loadSprites(path);

        // Initialize the view
        initialize(view, sprites, audio);
    }

    // Called when the AnimatedObject's audio must be loaded
    protected abstract void loadAudio(String path);

    // Called when the AnimatedObject's sprites must be loaded
    protected abstract void loadSprites(String path);

    // Called when the AnimatedObject is initialized
    protected abstract void initialize(ImageView view, SpriteManager sprites, AudioManager audio);

    // Called when the visibility of the AnimatedObject changes
    protected abstract int onVisiblilityChange(boolean visible, int frame);

    // Called on each frame update in which changes can be performed and new frames can be returned
    protected abstract int UpdateFrame(int frame, ImageView view, SpriteManager sprites, AudioManager audio);

    // Mounts the Buster view to the given root
    public void mount(Group root, double width, double height, double translateX, double translateY) {
        // Set the view width and height
        view.setFitWidth(width);
        view.setFitHeight(height);

        // Set the view position
        view.setTranslateX(translateX);
        view.setTranslateY(translateY);

        // Mount the view to the root
        root.getChildren().add(view);
    }

    // Returns the visibility of the AnimatedObject
    boolean isVisible = false;
    public boolean isVisible() {
        return isVisible;
    }

    // Sets the visibility of the AnimatedObject
    public void setVisible(boolean visible) {
        // Ensure the visibility state has changed
        if (isVisible != visible) {
            isVisible = visible;
            view.setVisible(visible);
        }
    }

    // This method is ticked manually by the parent class
    public void Update() {
        // Update the frame
        frame = UpdateFrame(frame, view, sprites, audio);
    }
}
