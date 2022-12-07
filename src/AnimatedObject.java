package src;

import javafx.scene.Group;
import javafx.scene.image.ImageView;

public abstract class AnimatedObject {
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

    // Must be implemented by the child class
    protected abstract void loadAudio(String path);

    // Must be implemented by the child class
    protected abstract void loadSprites(String path);

    // Must be implemented by the child class
    protected abstract void initialize(ImageView view, SpriteManager sprites, AudioManager audio);

    // Must be implemented by the child class
    protected abstract int onVisiblilityChange(boolean visible, int frame);

    // Must be implemented by the child class
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

    boolean isVisible = false;
    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        // Ensure the visibility state has changed
        if (isVisible != visible) {
            isVisible = visible;
            view.setVisible(visible);
        }
    }

    // Weapons must be ticked manually
    public void TickUpdate() {
        // Update the frame
        frame = UpdateFrame(frame, view, sprites, audio);
    }
}
