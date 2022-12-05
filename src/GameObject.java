package src;

import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public abstract class GameObject {
    static Timer timer;
    final static int frame_rate = 60;
    final static ArrayList<GameObject> objects = new ArrayList<GameObject>();

    // Initializes the game object
    static void beginTicking() {
        // Only initialize once
        if (timer != null) return;

        // Create a timer to call Update() every frame
        timer = new Timer(1000 / frame_rate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    // Update all game objects
                    for (GameObject obj : objects) obj.Update();
                }
                });
            }
            });

        // Start the timer
        timer.start();
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
