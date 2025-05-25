package namesday;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class NamesdaySeatingTest {

    @Test
    public void testSimpleBipartite() {
        List<String> guests = List.of("A","B","C","D");
        List<String[]> dislikes = List.of(
                new String[]{"A","B"},
                new String[]{"B","C"},
                new String[]{"C","D"}
        );
        Map<String,Integer> seating = NamesdaySeating.assignTables(guests, dislikes);
        for (String[] e : dislikes) {
            assertNotEquals(seating.get(e[0]), seating.get(e[1]),
                    "Edge " + e[0] + "-" + e[1] + " seated same table");
        }
    }

    @Test
    public void testDisconnected() {
        List<String> guests = List.of("X","Y","Z","W");
        List<String[]> dislikes = List.of(
                new String[]{"X","Y"},
                new String[]{"Z","W"}
        );
        Map<String,Integer> seating = NamesdaySeating.assignTables(guests, dislikes);
        assertNotEquals(seating.get("X"), seating.get("Y"));
        assertNotEquals(seating.get("Z"), seating.get("W"));
    }

    @Test
    public void testOddCycleThrows() {
        List<String> guests = List.of("P","Q","R");
        List<String[]> dislikes = List.of(
                new String[]{"P","Q"},
                new String[]{"Q","R"},
                new String[]{"R","P"}
        );
        assertThrows(IllegalArgumentException.class, () ->
                NamesdaySeating.assignTables(guests, dislikes)
        );
    }

    @Test
    public void testNoDislikesAllSameTable() {
        List<String> guests = List.of("U","V","W");
        List<String[]> dislikes = List.of();
        Map<String,Integer> seating = NamesdaySeating.assignTables(guests, dislikes);
        for (String g : guests) {
            assertEquals(0, seating.get(g));
        }
    }
}
