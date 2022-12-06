package src;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public abstract class GameObject {
    final static int frame_rate = 60;
    final static ArrayList<GameObject> objects = new ArrayList<GameObject>();
    final static ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    static Runnable ticker = null;

    // Initializes the game object
    static void beginTicking() {
        // Only initialize once
        if (ticker != null) return;

        // Define the ticker runnable
        ticker = new Runnable() {
            public void run() {
                // Update all game objects
                for (GameObject object : objects) {
                    try {
                        object.Update();
                    } catch (Exception e) {
                        System.out.println("Update() failed for " + object.getClass().getName());
                        System.out.println(e);
                    }
                }
            }
        };

        // Schedule the ticker to run every 1000/frame_rate ms
        executor.scheduleAtFixedRate(ticker, 0, 1000 / frame_rate, java.util.concurrent.TimeUnit.MILLISECONDS);
    }
    
    public GameObject() {
        // Add this object to the list of objects
        objects.add(this);

        // Begin ticking if this is the first object
        if (objects.size() == 1) beginTicking();
    }

    // Called every frame must be overridden by child classes
    public abstract void Update();
}
