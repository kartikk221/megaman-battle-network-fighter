import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public abstract class GameObject implements Tickable {
    static public Runnable ticker = null;
    final public static int frame_rate = 60;
    final public static ArrayList<GameObject> objects = new ArrayList<GameObject>();
    final public static ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    // Begins ticking all game objects at the rate of <GameObject.frame_rate> frames per second
    public static boolean beginTicking() {
        // Only initialize once and return false if already initialized
        if (ticker != null) return false;

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
    
        // Return true to indicate that the ticker was initialized
        return true;
    }
    
    public GameObject() {
        // Add this object to the list of objects
        objects.add(this);
    }

    // Called automatically every frame at the rate of <GameObject.frame_rate> frames per second
    public abstract void Update();
}
