public class EnemyPlayer extends Player {
    int min_move_frames = 30; // 500ms at 60fps
    int max_move_frames = 90; // 1500ms at 60fps
    int reaction_chance_percent = 50;
    int buster_chance_percent = 60;
    int cannon_chance_percent = 20;

    public EnemyPlayer(int health) {
        // Call the super constructor with the path to the megaman sprites
        super("./assets/enemy", health);

        // Set the buster damage to 2
        getBuster().setDamage(2);

        // Set the cannon damage to 30 (Less than the player)
        getCannon().setDamage(30);
    }

    // Handle when the player dies
    public void OnDeath() {
        // Display game over screen after 3 seconds
        GameObject.scheduleTick(60 * 3, new Runnable() {
            public void run() {
                // Mount the game over screen
                SceneManager.mountScreen(new GameOverScene("Congratulations", "You Beat the Game In Hard Mode!"));
            }
        });
    }

    int frames = 0;
    int reaction_frames = 0;

    // Called on each frame update which drives the enemy AI and underlying player
    public void Update() {
        // Ensure the enemy and player are not dead
        Player enemy = getEnemy();
        if (enemy == null || enemy.isDead() || isDead()) {
            // Cancel pending actions
            cancelPending();

            // Tick the explosions if dead
            if (isDead()) TickExplosions();

            // Halt execution
            return;
        };

        // Generate a random reaction time
        if (reaction_frames == 0)
            reaction_frames = (int) (Math.random() * (max_move_frames - min_move_frames) + min_move_frames);

        // Determine if the enemy is in vision range of the player
        int[] playerPosition = getPositionManager().getPosition();
        int[] enemyPosition = enemy.getPositionManager().getPosition();
        boolean enemyVisible = playerPosition[1] == enemyPosition[1]; // Same vertical space

        // Only react every 60 frames and if the enemy is visible
        if (frames % 30 == 0 && enemyVisible) {
            // Determine if we should react this frame
            boolean react = Math.random() * 100 <= reaction_chance_percent;
            if (react) {
                // Cancel any pending actions
                cancelPending();

                // Determine if we should fire cannon or buster
                boolean fireCannon = Math.random() * 100 <= cannon_chance_percent;
                boolean fireBuster = Math.random() * 100 <= buster_chance_percent;
                if (fireCannon) {
                    setFiringCannon(true);
                } else if (fireBuster) {
                    setFiringBuster(true);
                }
            }
        }

        // If the reaction time has been reached, perform random actions
        if (frames >= reaction_frames) {            
            // Ensure enemy is not visible
            if (!enemyVisible) {
                // Move randomly between -1 or 1 in both directions
                boolean up = Math.random() > 0.5;
                boolean left = Math.random() > 0.5;
                move(up ? 1 : -1, left ? 1 : -1);
            }

            // Reset the reaction time and frame counter
            reaction_frames = 0;
            frames = 0;
        } else {
            // Increment the frame counter
            frames++;
        }

        // Tick the player
        super.Update();
    }
}
