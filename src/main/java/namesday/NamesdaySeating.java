package namesday;

import java.util.*;


public class NamesdaySeating {

    /**
     * Assign each guest to table 0 or 1 so that no two guests
     * who dislike each other end up at the same table.
     *
     * @param guests   List of unique guest names.
     * @param dislikes List of [a, b] pairs: a and b dislike each other.
     * @return Map guest -> table (0 or 1).
     * @throws IllegalArgumentException if the dislike graph is not bipartite.
     */
    public static Map<String, Integer> assignTables(
            List<String> guests,
            List<String[]> dislikes
    ) {
        Map<String, List<String>> graph = new HashMap<>();
        for (String g : guests) {
            graph.put(g, new ArrayList<>());
        }
        for (String[] pair : dislikes) {
            String u = pair[0], v = pair[1];
            if (!graph.containsKey(u) || !graph.containsKey(v)) {
                throw new IllegalArgumentException("Unknown guest in dislikes: " + Arrays.toString(pair));
            }
            graph.get(u).add(v);
            graph.get(v).add(u);
        }

        Map<String, Integer> color = new HashMap<>();
        for (String g : guests) {
            color.put(g, -1);
        }

        Deque<String> stack = new ArrayDeque<>();

        for (String start : guests) {
            if (color.get(start) != -1) {
                continue;
            }
            color.put(start, 0);
            stack.push(start);

            while (!stack.isEmpty()) {
                String u = stack.pop();
                int uColor = color.get(u);
                int nextColor = 1 - uColor;

                for (String v : graph.get(u)) {
                    int vColor = color.get(v);
                    if (vColor == -1) {
                        color.put(v, nextColor);
                        stack.push(v);
                    } else if (vColor == uColor) {
                        throw new IllegalArgumentException(
                                "Seating conflict: \"" + u +
                                        "\" and \"" + v +
                                        "\" both at table " + uColor
                        );
                    }
                }
            }
        }

        return color;
    }


    // temporary *main* just to test (for example)
    public static void main(String[] args) {
        List<String> guests = List.of("Alice", "Bob", "Cathy", "Dave", "Eve");
        List<String[]> dislikes = List.of(
                new String[]{"Alice","Bob"},
                new String[]{"Bob","Cathy"},
                new String[]{"Cathy","Alice"},
                new String[]{"Dave","Eve"}
        );

        try {
            Map<String,Integer> seating = assignTables(guests, dislikes);
            Map<Integer,List<String>> tables = new HashMap<>();
            tables.put(0, new ArrayList<>());
            tables.put(1, new ArrayList<>());
            seating.forEach((guest, table) -> tables.get(table).add(guest));

            System.out.println("Table 0: " + tables.get(0));
            System.out.println("Table 1: " + tables.get(1));
        } catch (IllegalArgumentException ex) {
            System.err.println("Seating impossible: " + ex.getMessage());
        }
    }
}
