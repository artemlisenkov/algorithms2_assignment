package triwizard;

public class Wizard {
    private final char id;
    private final Position start;
    private final int speed;

    public Wizard(char id, Position start, int speed) {
        this.id = id;
        this.start = start;
        this.speed = speed;
    }

    public char id() {
        return id;
    }

    public Position start() {
        return start;
    }

    public int speed() {
        return speed;
    }
}
