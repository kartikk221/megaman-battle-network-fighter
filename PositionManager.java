public class PositionManager {
    int x = 1;
    int y = 1;
    double offsetX = 0;
    double offsetY = 0;
    double distance;
    boolean inverseAbsolute = false;
    
    // Constructor
    public PositionManager(double distance, double offsetX, double offsetY) {
        this.distance = distance;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    // Sets the position based on index
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Sets the inverse absolute flag
    public void setInverseAbsolute(boolean inverseAbsolute) {
        this.inverseAbsolute = inverseAbsolute;
    }

    // Returns the [x, y] position in index form
    int[] getPosition() {
        return new int[]{ x, y };
    }

    // Returns the normalized left to right grid position
    int[] getNormalizedPosition() {
        return new int[]{ inverseAbsolute ? x : Math.abs(x - 2), y };
    }

    // Returns the screen x translation
    double getTranslationX() {
        return offsetX + ((3 - x) * (distance * 0.65));
    }

    // Returns the screen y translation
    double getTranslationY() {
        return offsetY + ((3 - y) * (distance * 0.4));
    }
}
