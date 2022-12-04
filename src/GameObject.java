package src;

import java.util.Timer;
import java.util.TimerTask;

public abstract class GameObject {
    int frame_rate = 60;
    
    public GameObject() {
        initialize();
    }

    public GameObject(int frame_rate) {
        this.frame_rate = frame_rate;
        initialize();
    }

    // Initializes the game object
    void initialize() {
        // Call Start() to initialize the game object
        Start();

        // Create a timer to call Update() every frame
        long milliseconds = 1000 / frame_rate;
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Update();
            }
        };

        // Schedule the task
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(task, 0, milliseconds);
    }

    // Called once when the game object is created
    public abstract void Start();

    // Called every frame
    public abstract void Update();
}
