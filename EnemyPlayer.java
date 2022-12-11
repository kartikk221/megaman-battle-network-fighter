public class EnemyPlayer extends Player {
    int min_reaction_frames = 30; // 500ms at 60fps
    int max_reaction_frames = 90; // 1500ms at 60fps

    public EnemyPlayer(int health) {
        // Call the super constructor with the path to the megaman sprites
        super("./assets/enemy", health);

        // Set the buster damage to 2
        getBuster().setDamage(2);

        // Set the cannon damage to 50
        getCannon().setDamage(50);
    }

    int frames = 0;
    int reaction_frames = 0;

    // Called on each frame update which drives the enemy AI and underlying player
    public void Update() {
        // Generate a random reaction time
        if (reaction_frames == 0)
            reaction_frames = (int) (Math.random() * (max_reaction_frames - min_reaction_frames) + min_reaction_frames);

        // If the reaction time has been reached, perform random actions
        if (frames >= reaction_frames) {
            // Randomly fire
            setFiringBuster(Math.random() > 0.5);

            // Randomly fire cannon
            boolean fireCannon = Math.random() > 0.5;
            if (fireCannon) setFiringCannon(fireCannon);
                
            // Move randomly between -1 or 1 in both directions
            boolean up = Math.random() > 0.5;
            boolean left = Math.random() > 0.5;
            move(up ? 1 : -1, left ? 1 : -1);

            // Reset the reaction time and frame counter
            frames = 0;
            reaction_frames = 0;
        } else {
            // Increment the frame counter
            frames++;
        }

        // Tick the player
        super.Update();
    }
}
