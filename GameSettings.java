import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class GameSettings implements Serializable {
    final static String file = "SETTINGS";
    double volume = 0.1;
    String difficulty = "Beginner";

    public GameSettings() {
        try {
            // Read the game settings from the file
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            GameSettings settings = (GameSettings) in.readObject();
            in.close();

            // Set the game settings
            volume = settings.volume;
            difficulty = settings.difficulty;
        } catch (IOException e) {} catch (ClassNotFoundException e) {}
    }

    // Sets the difficulty of the game
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
        save();
    }

    // Sets the volume of the game
    public void setVolume(double volume) {
        this.volume = volume;
        save();
    }

    // Returns the difficulty of the game
    public String getDifficulty() {
        return difficulty;
    }

    // Returns the volume of the game
    public double getVolume() {
        return volume;
    }

    // Saves the game settings to disk
    void save() {
        // Serialize and save the game settings
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(this);
            out.close();
        } catch (IOException e) {
            System.out.println("Error saving game settings");
            System.out.println(e);
        }
    }
}
