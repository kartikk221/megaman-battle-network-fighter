import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SceneManager extends Application implements Thread.UncaughtExceptionHandler {
    static Stage stage;
    static Mountable activeScene;

    // Returns the screen dimensions as a double array
    public static double[] getScreenDimensions() {
        // Retrieve the device width and height
        Rectangle2D screen = Screen.getPrimary().getBounds();
        double width = screen.getWidth();
        double height = screen.getHeight() - 100; // Account for the taskbar and title bar
        return new double[] { width, height };
    }

    // Returns the window dimensions as a double array with a contained 16:9 aspect ratio
    public static double[] getWindowDimensions(double[] dimensions) {
        double aspectRatio = 16.0 / 9.0;
        dimensions[0] = dimensions[1] * aspectRatio;
        dimensions[1] = dimensions[0] / aspectRatio;
        return dimensions;
    }

    // Mounts a new scene to the scene manager
    public static void mountScreen(Mountable scene) {
        // Unmount the current scene
        if (activeScene != null) activeScene.unmount(stage);

        // Mount the new scene
        activeScene = scene;
        activeScene.mount(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    // Called when JavaFX is ready to start the application
    public void start(Stage stage) {
        try {
            // Store stage reference and make it not resizable
            SceneManager.stage = stage;
            stage.setResizable(false);

            // Mount the main menu scene
            mountScreen(new MainMenuScene());
        } catch (Exception e) {
            System.out.println("Error Occured In SceneManager:");
            System.out.println(e);
        }
    }

    public void displayMainMenu() {}

    @Override
    public void stop() {
        // Exit the application
        System.out.println("Closing application...");
        System.exit(0);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("Uncaught exception: " + e);
    }
}