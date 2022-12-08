import java.io.File;
import java.util.HashMap;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioManager implements Manager {
    HashMap<String, MediaPlayer> audioMap = new HashMap<String, MediaPlayer>();

    // Loads an audio file into memory
    public void load(String name, String path) {
        try {
            // Load the audio file
            Media track = new Media(
                new File(path).toURI().toString()
            );

            // Instantiate the media player
            MediaPlayer player = new MediaPlayer(track);
            
            // Add the player to the audio map
            audioMap.put(name, player);
        } catch (Exception e) {
            System.out.println("Error loading audio file: " + path);
            System.out.println(e);
        }
    }

    // Checks if an audio file is loaded
    public boolean isLoaded(String name) {
        return audioMap.get(name) != null;
    }

    // Unloads an audio file from memory
    public void unload(String name) {
        audioMap.remove(name);
    }

    // Plays an audio file
    public void play(String name, boolean loop) {
        // Get the player from the audio map
        MediaPlayer player = audioMap.get(name);

        // Set the player to loop
        player.setCycleCount(loop ? MediaPlayer.INDEFINITE : 1);

        // Reset the player and play
        if (!loop) player.seek(player.getStartTime());

        // Play the player
        player.play();
    }

    // Stops an audio file
    public void stop(String name) {
        // Get the player from the audio map
        MediaPlayer player = audioMap.get(name);

        // Stop the player
        player.stop();
    }

    // Sets the volume of an audio file
    public void setVolume(String name, double volume) {
        // Get the player from the audio map
        MediaPlayer player = audioMap.get(name);

        // Set the player volume
        player.setVolume(volume);
    }
}
