public class Position {
    private final int x;
    private final int y;

    private final int food;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
        food = 0;
    }

    public Position(int x, int y, int food) {
        this.x = x;
        this.y = y;
        this.food = food;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getFood() {
        return food;
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
