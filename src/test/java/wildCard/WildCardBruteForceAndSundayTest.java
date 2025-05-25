package wildCard;

import org.junit.jupiter.api.Test;
import patternmatch.BruteForce;
import patternmatch.PatternMatcher;
import patternmatch.Sunday;

import static org.junit.jupiter.api.Assertions.*;

public class WildCardBruteForceAndSundayTest {
    private final PatternMatcher bf = new BruteForce();
    private final PatternMatcher su = new Sunday();

    void assertMatch(PatternMatcher m, String text, String pat, boolean expected) {
        int idx = m.search(text, pat);
        assertEquals(expected, idx >= 0,
                () -> m.getClass().getSimpleName() +
                        " failed for text=\"" + text + "\" pat=\"" + pat + "\" → idx=" + idx);
    }

    @Test void literalsOnly() {
        assertMatch(bf, "hello", "ell", true);
        assertMatch(su, "hello", "world", false);
    }

    @Test void questionWild() {
        assertMatch(bf, "hello", "he?lo", true);
        assertMatch(su, "hello", "?ello", true);
        assertMatch(bf, "hello", "h?zlo", false);
    }

    @Test void starWild() {
        assertMatch(bf, "abcdefg", "a*fg", true);
        assertMatch(su, "abcdefg", "*cde*", true);
        assertMatch(bf, "abc", "a*z", false);
    }

    @Test void escapes() {
        assertMatch(bf, "a?b*c", "a\\?b\\*c", true);
        assertMatch(su, "foo\\bar", "foo\\\\bar", true);
    }
}
