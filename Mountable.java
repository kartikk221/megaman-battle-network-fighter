import javafx.stage.Stage;

public abstract class Mountable {
    // Mounts the scene to the stage
    public abstract void mount(Stage stage);

    // Unmounts the scene from the stage
    public abstract void unmount(Stage state);
}
