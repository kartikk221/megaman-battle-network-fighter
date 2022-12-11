public abstract class Weapon extends AnimatedObject {
    Player owner;
    Player target;
    int damage = 1;

    // Pass the path to the weapon sprites to the super constructor
    public Weapon(String path) {
        super(path);
    }

    // Configures the owner of the weapon
    public void setTargets(Player owner, Player target) {
        this.owner = owner;
        this.target = target;
    }

    // Configures the damage of the weapon
    public void setDamage(int damage) {
        this.damage = damage;
    }

    // Attempts to perform damage from the owner to the target
    public boolean attemptDamage(boolean heavy) {
        // Ensure the owner and target are not null
        if (owner == null || target == null) return false;

        // Retrieve the positions of the owner and target
        int[] ownerPosition = owner.getPositionManager().getNormalizedPosition();
        int[] targetPosition = target.getPositionManager().getNormalizedPosition();

        // Calculate effectiveness and vulnerability points for the owner and target
        int effectiveness_damage = ownerPosition[0] * damage;
        int vulnerability_damage = effectiveness_damage > 0 ? targetPosition[0] * damage : 0;
        int combined_damage = effectiveness_damage + vulnerability_damage;
        int final_damage = damage > 10 ? Math.min(damage * 2, combined_damage > damage ? combined_damage : damage) : Math.max(damage, combined_damage);

        // Check if the owner and target are in the same vertical position aka. horizontally facing each other
        if (ownerPosition[1] == targetPosition[1]) {
            // Ensure the target is not invincible
            if (!target.isInvicible()) {
                // Inflict damage to the target
                target.inflictDamage(final_damage, heavy);

                // Return true to indicate damage was inflicted
                return true;
            }
        }

        return false;
    }    
}
