package src;

import javafx.scene.Scene;

public class EnemyPlayer extends Player {
    Scene scene;

    public EnemyPlayer(Scene scene) {
        // Call the super constructor with the path to the megaman sprites
        super("./assets/player");

        // Set the scene
        this.scene = scene;

        // Randomly move the player between 0.1 - 1.5 seconds
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep((long) (Math.random() * 1400 + 100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Randomly fire
                setFiring(Math.random() > 0.5);
                
                // Move randomly between -1 or 1 in both directions
                boolean up = Math.random() > 0.5;
                boolean left = Math.random() > 0.5;
                move(up ? 1 : -1, left ? 1 : -1);
            }
        }).start();
    }
}
