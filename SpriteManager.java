import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.image.Image;

public class SpriteManager implements Manager {
    // Private variables
    HashMap<String, ArrayList<Image>> sprites = new HashMap<String, ArrayList<Image>>();

    // Loads sprites into memory from a given path concatenated with the index range
    public void load(String name, String path_name, String extension, int start, int end) {
        // Create a new array list to store the sprites
        ArrayList<Image> list = new ArrayList<Image>();

        // Load the sprites into memory
        for (int i = start; i <= end; i++) {
            String path = path_name + i + extension;
            try {
                // Create read stream
                FileInputStream stream = new FileInputStream(path_name + i + extension);

                // Create image
                Image image = new Image(stream);

                // Add image to list
                list.add(image);

                // Close stream
                stream.close();
            } catch (Exception e) {
                System.out.println("Failed to load sprite: " + path);
                System.out.println(e);
            }
        }

        // Add the sprite list to the sprites hash map
        sprites.put(name, list);
    }

    // Loads sprites into memory from a given path concatenated with the index range
    public void load(String name, String path) {
        // Call the overloaded method with defaults
        load(name, path, ".png", 0, 0);
    }

    // Checks if a given sprite list is loaded
    public boolean isLoaded(String name) {
        return sprites.get(name) != null;
    }

    // Unloads a given sprite list from memory
    public void unload(String name) {
        sprites.remove(name);
    }

    // Returns the image at the given index of the given sprite
    Image getImage(String name, int index) {
        // Get the sprite list
        ArrayList<Image> list = sprites.get(name);
        if (list == null) {
            System.out.println("Failed to get sprite: " + name + " at index: " + index);
            return null;
        }

        // Return the sprite at the given index
        return list.get(index);
    }

    // Returns the number of sprites in a given sprite list
    int getCount(String name) {
        ArrayList<Image> list = sprites.get(name);
        if (list == null) {
            System.out.println("Failed to get sprite count: " + name);
            return 0;
        }

        return list.size();
    }
}
