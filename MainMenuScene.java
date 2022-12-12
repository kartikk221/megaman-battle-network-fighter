import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class MainMenuScene extends Mountable {
    // Static variables
    static Font titleFont;
    static Font buttonFont;
    public static GameSettings settings = new GameSettings();

    // Private variables
    Scene scene;
    VBox container;
    double width, height;

    public MainMenuScene() {
        // Retrieve the screen dimensions
        double[] screenDimensions = SceneManager.getScreenDimensions();
        width = screenDimensions[0];
        height = screenDimensions[1];

        // Load the font if it is not loaded
        if (titleFont == null) {
            titleFont = Font.loadFont(getClass().getResourceAsStream("./assets/fonts/BN6FontSmExt.ttf"), Math.round(width * 0.03));
            buttonFont = Font.loadFont(getClass().getResourceAsStream("./assets/fonts/BN6FontSmExt.ttf"), Math.round(width * 0.015));
        }

        // Add a title text to the container
        Text title = new Text("Megaman Fighter");
        title.setFont(titleFont);
        title.setFill(Color.BLACK);

        // Create a volume text tag
        Text difficultyTag = new Text("Game Difficulty");
        difficultyTag.setFont(buttonFont);
        difficultyTag.setFill(Color.BLACK);
        difficultyTag.setTextAlignment(TextAlignment.CENTER);

        // Create a difficulty combo box with same style as the buttons but inverted
        ComboBox<String> difficultyComboBox = new ComboBox<>();
        difficultyComboBox.setMaxHeight(width / 5);
        difficultyComboBox.setMaxHeight(width / 6);
        difficultyComboBox.getItems().addAll("Beginner", "Experienced", "Impossible");
        difficultyComboBox.setValue(settings.getDifficulty());

        // Detect when difficulty combo box is changed
        difficultyComboBox.setOnAction(e -> {
            // Set the difficulty in the settings
            settings.setDifficulty(difficultyComboBox.getValue());
        });

        // Create a volume text tag
        Text volumeSliderTag = new Text("Game Volume " + (int) (settings.getVolume() * 100));
        volumeSliderTag.setFont(buttonFont);
        volumeSliderTag.setFill(Color.BLACK);
        volumeSliderTag.setTextAlignment(TextAlignment.CENTER);

        // Create a volume slider with same style as the buttons but inverted
        Slider volumeSlider = new Slider(0, 1, settings.getVolume());
        volumeSlider.setMaxHeight(width / 5);
        volumeSlider.setMaxHeight(width / 6);
        volumeSlider.setAccessibleText("Set Volume");

        // Detect when volume slider is changed
        volumeSlider.setOnMouseDragged(e -> {
            // Set the volume in the settings
            settings.setVolume(((Slider) e.getSource()).getValue());

            // Update the volume text tag
            volumeSliderTag.setText("Game Volume " + (int) (settings.getVolume() * 100));
        });

        // Create New Game button which mounts the game scene when clicked
        Button newGameButton = createButton("Start New Game");
        newGameButton.setOnAction(e -> {
            // Mount the game scene
            SceneManager.mountScreen(new GameScene(difficultyComboBox.getValue()));
        });

        // Create the container
        container = new VBox(title, difficultyTag, difficultyComboBox, volumeSliderTag, volumeSlider, newGameButton);
        container.setSpacing(height * 0.02);
        container.setAlignment(Pos.CENTER);

        // Create the scene and container
        scene = new Scene(container, width / 3, height / 2);
    }

    // Creates a UI button
    Button createButton(String text) {
        // Create a button
        Button button = new Button(text);
        button.setFont(buttonFont);
        button.setMaxHeight(width / 5);
        button.setMaxHeight(width / 6);
        button.setTextFill(Color.WHITE);
        button.setTextAlignment(TextAlignment.CENTER);
        button.setBackground(Background.fill(Color.BLACK));

        // Increase button size on hover
        button.setOnMouseEntered(e -> {
            button.setScaleX(1.02);
            button.setScaleY(1.02);
        });

        // Decrease button size on hover
        button.setOnMouseExited(e -> {
            button.setScaleX(1);
            button.setScaleY(1);
        });

        // Return the button
        return button;
    }

    // Mounts the scene to the stage
    public void mount(Stage stage) {
        // Set the title of the stage
        stage.setTitle("Main Menu - Megaman Fighter Game");

        // Sets the scene to the stage
        stage.setScene(scene);

        // Show the stage
        stage.show();
    }

    // Unmounts the scene from the stage
    public void unmount(Stage state) {
        // Stop ticking the GameObjects
        GameObject.stopTicking();

        // Sets the scene to null
        state.setScene(null);

        // Hide the stage
        state.hide();
    }
}
