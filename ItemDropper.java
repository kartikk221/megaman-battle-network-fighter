import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class ItemDropper implements Tickable {
    String path;
    Player player;
    StackPane root;
    int frames = 0;
    int drop_chance_percent = 25;
    int drop_rate_frames = 60 * 30;
    double width, height, offsetX, offsetY;
    AudioManager audio = new AudioManager();
    ImageView[][] dropped = new ImageView[3][3]; // 3 rows, 3 columns

    public ItemDropper(StackPane root, Player player) {
        this.root = root;
        this.player = player;

        // Load the item sound effects
        audio.load("drop", "./assets/sound/item-drop.wav");
        audio.setVolume("drop", MainMenuScene.settings.getVolume());
        audio.load("pickup", "./assets/sound/item-pickup.wav");
        audio.setVolume("pickup", MainMenuScene.settings.getVolume());
    }

    // Sets the item to drop
    public void setItem(String path, double width, double height, double offsetX, double offsetY) {
        this.path = path;
        this.width = width;
        this.height = height;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    // Sets the drop rate and chance properties for this dropper
    public void setProperties(int drop_rate_frames, int drop_chance_percent) {
        this.drop_rate_frames = drop_rate_frames;
        this.drop_chance_percent = drop_chance_percent;
    }

    // Ticks the internal state of the drops manager
    public void Update() {
        // Iterate through the dropped items every 2 frames
        for (int x = 0; x < dropped.length; x++) {
            for (int y = 0; y < dropped[0].length; y++) {
                // Check if the item has been dropped
                if (dropped[x][y] != null) {
                    // Get the item view
                    ImageView view = dropped[x][y];

                    // Retrieve the position for this item
                    PositionManager manager = player.getPositionManager();
                    double expectedY = manager.getTranslationY(y) + offsetY;

                    // Check if the item has reached the player
                    if (view.getTranslateY() < expectedY) {
                        // Decrement the position of the item
                        view.setTranslateY(Math.min(view.getTranslateY() + (height * 0.3), expectedY));
                    
                        // Check if the item has reached the player
                        if (view.getTranslateY() == expectedY) {
                            // Play drop sound effect
                            audio.play("drop", false);
                        }
                    }
                }
            }
        }

        // Check if the frames have reached the drop rate
        if (frames >= drop_rate_frames) {
            // Reset the frames
            frames = 0;

            // Determine if the item should be dropped and if the player has an item
            boolean drop = Math.random() * 100 < drop_chance_percent;
            if (drop && !player.hasItem()) {
                // Generate a random position for the item
                int x = (int) (Math.random() * dropped.length);
                int y = (int) (Math.random() * dropped[0].length);

                // Check if the item has already been dropped in this position
                if (dropped[x][y] == null) {
                    // Check if the player is in the same position
                    PositionManager manager = player.getPositionManager();
                    int[] position = manager.getPosition();
                    if (position[0] != x || position[1] != y) {
                        // Mark the item as dropped
                        dropped[x][y] = new ImageView(path);

                        // Set the dimensions of the item
                        dropped[x][y].setFitWidth(width);
                        dropped[x][y].setFitHeight(height);

                        // Set the position of the item to the player slot position
                        dropped[x][y].setTranslateX(manager.getTranslationX(x) + offsetX);
                        dropped[x][y].setTranslateY(-height * 5);
                    
                        // Add the item to the root
                        root.getChildren().add(dropped[x][y]);

                        // Bring the player to the front
                        player.getGroup().toFront(); 
                    }
                }
            }
        } else {
            // Retrieve the position of the player
            PositionManager manager = player.getPositionManager();
            int[] position = manager.getPosition();
            ImageView item = dropped[position[0]][position[1]];
            double expectedY = manager.getTranslationY(position[1]) + offsetY;
            if (item != null && item.getTranslateY() == expectedY) {
                // Remove the item from the root
                root.getChildren().remove(item);

                // Mark the item as not dropped
                dropped[position[0]][position[1]] = null;

                // Add the item to the player's inventory
                player.setHasItem(true);

                // Play the pickup sound effect
                audio.play("pickup", false);
            }

            // Increment the frames
            frames++;
        }
    }
}
