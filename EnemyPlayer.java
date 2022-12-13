public class EnemyPlayer extends Player {
    int original_health;
    int last_hundreds_lost = 0;
    int min_move_frames = 30; // 500ms at 60fps
    int max_move_frames = 90; // 1500ms at 60fps
    int buster_chance_percent = 80;
    int cannon_chance_percent = 20;

    public EnemyPlayer(int health) {
        // Call the super constructor with the path to the megaman sprites
        super("./assets/enemy", health);

        // Set the buster damage to 2
        getBuster().setDamage(2);

        // Set the cannon damage to 30 (Less than the player)
        getCannon().setDamage(30);
    }

    // Handles health change events
    public void OnHealthChange(int health) {
        // Store the original health
        if (original_health == 0) {
            original_health = health;
        } else {
            // Increase the difficulty every 100 health lost
            int num_of_hundred_lost = (original_health - health) / 100;
            if (num_of_hundred_lost > last_hundreds_lost) {
                // Increase the difficulty
                increaseDifficulty();

                // Update the last hundreds lost
                last_hundreds_lost = num_of_hundred_lost;
            }
        }
    }

    // Handle when the player dies
    public void OnDeath() {
        // Display game over screen after 3 seconds
        GameObject.scheduleTick(60 * 3, new Runnable() {
            public void run() {
                // Mount the game over screen
                String difficulty = MainMenuScene.settings.getDifficulty();
                SceneManager.mountScreen(new GameOverScene("Congratulations", "You Beat the Game In " + difficulty + " Mode!"));
            }
        });
    }

    // Increases the difficulty of the enemy player AI
    public void increaseDifficulty() {
        // Decrease the move frame range
        min_move_frames = Math.max(15, min_move_frames - 5); // At minimum 250ms
        max_move_frames = Math.max(45, max_move_frames - 5); // At minimum 750ms

        // Increase the buster chance
        buster_chance_percent = Math.min(100, buster_chance_percent + 5);

        // Increase the cannon chance up to 40%
        cannon_chance_percent = Math.min(40, cannon_chance_percent + 5);
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

        // If the reaction time has been reached, perform random actions
        if (frames >= reaction_frames) {
            // Determine if enemy is in view
            int[] playerPosition = getPositionManager().getPosition();
            int[] enemyPosition = enemy.getPositionManager().getPosition();
            boolean inView = playerPosition[1] == enemyPosition[1];

            // Randomly fire cannon at any time
            boolean fireCannon = Math.round(Math.random() * 100) <= cannon_chance_percent;
            if (fireCannon) setFiringCannon(fireCannon);

            // Randomly fire buster if in view
            boolean fireBuster = inView && Math.round(Math.random() * 100) <= buster_chance_percent;
            setFiringBuster(fireBuster);

            // Move randomly between -1 or 1 in both directions
            boolean up = Math.random() > 0.5;
            boolean left = Math.random() > 0.5;
            move(up ? 1 : -1, left ? 1 : -1);

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
