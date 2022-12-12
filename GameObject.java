import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javafx.application.Platform;

public abstract class GameObject implements Tickable {
    static boolean isTicking = false;
    static public Runnable ticker = null;
    final public static int frame_rate = 60;
    final public static ArrayList<GameObject> objects = new ArrayList<GameObject>();
    final public static ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    // Begins ticking all game objects at the rate of <GameObject.frame_rate> frames per second
    public static void startTicking() {
        // Mark this instance as ticking
        isTicking = true;

        // Only initialize once and return false if already initialized
        if (ticker != null) return;

        // Define the ticker runnable
        ticker = new Runnable() {
            public void run() {
                // Run the update method for each object in Platform.runLater to ensure that the UI thread is used
                // This is required to prevent JavaFX from throwing an exception
                Platform.runLater(new Runnable() {
                    public void run() {
                        // Update all game objects if ticking
                        if (isTicking) {
                            for (GameObject object : objects) {
                                try {
                                    object.Update();
                                } catch (Exception e) {
                                    System.out.println("Update() failed for " + object.getClass().getName());
                                    System.out.println(e);
                                }
                            }
                        }
                    }
                });
            }
        };

        // Schedule the ticker to run every 1000/frame_rate ms
        executor.scheduleAtFixedRate(ticker, 0, 1000 / frame_rate, java.util.concurrent.TimeUnit.MILLISECONDS);
    }

    // Stops ticking all game objects
    public static void stopTicking() {
        isTicking = false;
    }

    // Schedules a runnable tick to run after specified number of frames
    public static void scheduleTick(int frames, Runnable runnable) {
        // Schedule the runnable to run after <frames> frames
        executor.schedule(new Runnable() {
            public void run() {
                // Run the runnable in Platform.runLater to ensure that the UI thread is used
                // This is required to prevent JavaFX from throwing an exception
                Platform.runLater(runnable);
            }
        }, frames * 1000 / frame_rate, java.util.concurrent.TimeUnit.MILLISECONDS);
    }
    
    public GameObject() {
        // Add this object to the list of objects
        objects.add(this);
    }

    // Called automatically every frame at the rate of <GameObject.frame_rate> frames per second
    public abstract void Update();
}
