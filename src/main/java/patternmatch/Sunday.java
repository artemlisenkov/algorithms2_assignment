package patternmatch;

import utils.WildCardUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sunday implements PatternMatcher {
    @Override
    public int search(String text, String pattern) {
        List<WildCardUtil.Token> tokens = WildCardUtil.tokenize(pattern);
        int n  = text.length();
        int tM = tokens.size();
        int minLen = WildCardUtil.minimalMatchLength(tokens);

        Map<Character,Integer> shift = new HashMap<>();
        for (int i = 0; i < tM; i++) {
            WildCardUtil.Token tok = tokens.get(i);
            if (tok.getType() == WildCardUtil.TokenType.LITERAL) {
                shift.put(tok.getChar(), tM - i);
            }
        }

        int i = 0;
        while (i <= n - minLen) {
            if (WildCardUtil.matchesAt(text, i, tokens)) {
                return i;
            }
            if (i + minLen >= n) break;
            char next = text.charAt(i + minLen);
            i += shift.getOrDefault(next, tM + 1);
        }
        return -1;
    }
}
