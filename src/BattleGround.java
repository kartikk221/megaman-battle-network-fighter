package src;

import java.io.FileInputStream;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class BattleGround {
    Image image;
    ImageView view;

    public BattleGround(StackPane root, String path, double width, double height) {
        try {
            // Create an input stream to read the image
            FileInputStream stream = new FileInputStream(path);

            // Instantiate the image
            image = new Image(stream);

            // Instantiate the view
            view = new ImageView(image);

            // Set the width and height of the view
            view.setFitWidth(width);
            view.setFitHeight(height);

            // Mount the view to the root
            root.getChildren().add(view);
        } catch (Exception e) {
            System.out.println("Failed to load BattleGround image");
            System.out.println(e);
        }
    }
}
