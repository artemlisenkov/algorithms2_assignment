package patternmatch;

import utils.WildCardUtil;
import java.util.List;

public class BruteForce implements PatternMatcher {
    @Override
    public int search(String text, String pattern) {
        List<WildCardUtil.Token> tokens = WildCardUtil.tokenize(pattern);
        int n = text.length();
        int minLen = WildCardUtil.minimalMatchLength(tokens);

        for (int i = 0; i <= n - minLen; i++) {
            if (WildCardUtil.matchesAt(text, i, tokens)) {
                return i;
            }
        }
        return -1;
    }
}