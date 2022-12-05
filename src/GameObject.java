package src;

import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class GameObject {
    Timer timer;
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
        // Create a timer to call Update() every frame
        timer = new Timer(1000 / frame_rate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    Update();
                }
              });
            }
          });

        // Start the timer
        timer.start();
    }

    // Returns the timer for this instance
    public Timer getTimer() {
        return timer;
    }

    // Called every frame
    public abstract void Update();
}
