package src;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioManager {
    HashMap<String, MediaPlayer> audioMap = new HashMap<String, MediaPlayer>();

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

    public void stop(String name) {
        // Get the player from the audio map
        MediaPlayer player = audioMap.get(name);

        // Stop the player
        player.stop();
    }

    public void setVolume(String name, double volume) {
        // Get the player from the audio map
        MediaPlayer player = audioMap.get(name);

        // Set the player volume
        player.setVolume(volume);
    }
}
