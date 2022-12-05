package src;

import java.io.FileInputStream;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class Background extends GameObject {
    double speed = 0.5;
    double width;
    double height;
    Image image;
    double[] offsets;
    ImageView[] views;

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
            views = new ImageView[2];
            offsets = new double[2];
            for (int i = 0; i < 2; i++) {
                // Instantiate the view
                views[i] = new ImageView(image);

                // Store the offset as 0 which will be used to perform animations later
                offsets[i] = 0;

                // Set the width and height of the view
                views[i].setFitWidth(width);
                views[i].setFitHeight(height);

                // Position the images above each other and keep the last one at center
                if (i == 0) {
                    views[i].setTranslateY(height);
                } else {
                    views[i].setTranslateY(0);
                }

                // Mount the view to the root
                root.getChildren().add(views[i]);
            }

            // Close the stream
            stream.close();
        } catch (Exception e) {
            System.out.println("Failed to load Background image");
            System.out.println(e);
        }
    }

    public ImageView[] getViews() {
        return views;
    }

    public void Update() {
        // Ensure views are not null
        if (views == null) return;

        // Move each image up by 1 pixel
        for (int i = 0; i < views.length; i++) {
            if (views[i] != null) {
                // Check the offset to see if the image needs to be moved back down by height to reset the animation
                double y = views[i].getTranslateY();
                if (offsets[i] >= height) {
                    offsets[i] = 0;
                    views[i].setTranslateY(y + height);
                } else {
                    offsets[i] += speed;
                    views[i].setTranslateY(y - speed);
                }
            }
        }
    }
}
