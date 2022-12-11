import java.io.FileInputStream;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class ControlsBox {
    Image image;
    ImageView view;

    public ControlsBox(StackPane root, String path, double width) {
        try {
            // Create an input stream to read the image
            FileInputStream stream = new FileInputStream(path);

            // Instantiate the image and view
            image = new Image(stream);
            view = new ImageView(image);

            // Set the width and height of the view
            view.setFitWidth(width);
            view.setFitHeight(width * 0.3125);

            // Set the x and y position of the view
            view.setTranslateX(-width);
            view.setTranslateY(-width * 0.68);

            // Set the default opacity to 0.5
            view.setOpacity(0.5);

            // Increase opacity to 1 when mouse enters
            view.setOnMouseEntered(e -> {
                view.setOpacity(1);
            });

            // Decrease opacity to 0.5 when mouse exits
            view.setOnMouseExited(e -> {
                view.setOpacity(0.5);
            });

            // Mount the view to the root
            root.getChildren().add(view);
        } catch (Exception e) {
            System.out.println("Failed to load ControlsBox image");
            System.out.println(e);
        }
    }
}
