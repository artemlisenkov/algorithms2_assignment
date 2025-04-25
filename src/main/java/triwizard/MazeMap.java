package triwizard;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class MazeMap {
    private final char[][] grid;
    private final int rows;
    private final int cols;

    private Position exit;
    private final Map<Character, Wizard> wizards = new HashMap<>();

    public MazeMap(String mazePath, String speedPath) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(mazePath));
        rows = lines.size();
        cols = lines.getFirst().length();
        grid = new char[rows][cols];

        for (int r = 0; r < rows; r++) {
            String line = lines.get(r);
            for (int c = 0; c < cols; c++) {
                char ch = line.charAt(c);

                if (ch == 'E') {
                    exit = new Position(r, c);
                    grid[r][c] = '.';
                } else if (Character.isUpperCase(ch) && ch != '#') {
                    wizards.put(ch, new Wizard(ch, new Position(r, c), 0));
                    grid[r][c] = '.';
                } else {
                    grid[r][c] = ch;
                }

            }
        }

        for (String line : Files.readAllLines(Path.of(speedPath))) {
            String[] parts = line.split("\\s+");
            char id = parts[0].charAt(0);
            int speed = Integer.parseInt(parts[1]);

            Wizard w = wizards.get(id);
            if (w != null) {
                wizards.put(id, new Wizard(id, w.start(), speed));
            }
        }
    }

    public char[][] grid() {
        return grid;
    }

    public Position exit() {
        return exit;
    }

    public Collection<Wizard> wizards() {
        return wizards.values();
    }

    public boolean isWalkable(int r, int c) {
        return r >= 0 && r < rows && c >= 0 && c < cols && grid[r][c] != '#';
    }
}
