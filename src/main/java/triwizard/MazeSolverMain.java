package triwizard;

import java.io.IOException;
import java.util.*;

public class MazeSolverMain {
    private static final int[][] DIRS = { {0,1}, {1,0}, {0,-1}, {-1,0} };

    public static void main(String[] args) throws IOException {
        MazeMap map = new MazeMap("data/maze.txt", "data/speeds.txt");

        Map<Position, Integer> dist = bfsFromExit(map);

        Wizard winner = null;
        double bestArrivalTime = Double.MAX_VALUE;

        for (Wizard wizard : map.wizards()) {
            int distance = dist.getOrDefault(wizard.start(), Integer.MAX_VALUE);
            double arrivalTime = distance / (double) wizard.speed();

            System.out.printf("Wizard %s:  distance=%-3d  speed=%-2d  arrival=%.2f units%n",
                    wizard.id(), distance, wizard.speed(), arrivalTime);

            if (arrivalTime < bestArrivalTime) {
                bestArrivalTime = arrivalTime;
                winner = wizard;
            }
        }

        if (winner != null) {
            System.out.printf("Winner: Wizard %s (arrival: %.2f units)%n", winner.id(), bestArrivalTime);
        } else {
            System.out.println("No wizard can reach the exit.");
        }
    }

    private static Map<Position, Integer> bfsFromExit(MazeMap map) {
        Map<Position, Integer> distance = new HashMap<>();
        Queue<Position> queue = new ArrayDeque<>();

        Position start = map.exit();
        queue.add(start);
        distance.put(start, 0);

        while (!queue.isEmpty()) {
            Position cur = queue.poll();
            int curDist = distance.get(cur);

            for (int[] d : DIRS) {
                int nr = cur.row() + d[0];
                int nc = cur.col() + d[1];
                Position next = new Position(nr, nc);

                if (map.isWalkable(nr, nc) && !distance.containsKey(next)) {
                    distance.put(next, curDist + 1);
                    queue.add(next);
                }
            }
        }

        return distance;
    }
}
