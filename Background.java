import java.io.FileInputStream;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class Background extends GameObject {
    double speed = 2;
    double width;
    double height;
    Image image;
    Group group;

    public Background(StackPane root, String path, double width, double height) {
        // Set the width and height
        this.width = width;
        this.height = height;

        // Load the images
        try {
            // Create an input stream to read the image
            FileInputStream stream = new FileInputStream(path);

            // Instantiate the image
            image = new Image(stream);

            // Instantiate 3 images to create a 3x3 grid
            ImageView[] views = new ImageView[2];
            for (int i = 0; i < 2; i++) {
                // Instantiate the view
                views[i] = new ImageView(image);

                // Set the width and height of the view
                views[i].setFitWidth(width);
                views[i].setFitHeight(height);

                // Position the images above each other and keep the last one at center
                if (i == 0) {
                    views[i].setTranslateY(height);
                } else {
                    views[i].setTranslateY(0);
                }
            }

            // Instantiate the group
            group = new Group(views);

            // Mount the group to the root
            root.getChildren().add(group);

            // Close the stream
            stream.close();
        } catch (Exception e) {
            System.out.println("Failed to load Background image");
            System.out.println(e);
        }
    }

    int throttle = 0;

    // Called on each frame update which gradually animates the background from bottom to top in an infinite loop
    public void Update() {
        // Ensure views are not null
        if (group == null) return;

        // Check if the throttle is exhausted
        if (throttle >= 4) {
            throttle = 0;

            // Animate the background
            group.setTranslateY(group.getTranslateY() - speed);
            
            // Check if the background has reached the end
            if (group.getTranslateY() <= -height / 2) {
                // Reset the background
                group.setTranslateY(0);
            }
        }

        // Increment the throttle
        throttle++;
    }
}
