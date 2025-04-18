package algorithms;

import java.util.HashMap;
import java.util.Map;

public class Sunday implements PatternMatcher {
    @Override
    public int search(String text, String pattern) {
        int n = text.length();
        int m = pattern.length();
        Map<Character, Integer> shift = new HashMap<>();

        for (int i = 0; i < m; i++) {
            shift.put(pattern.charAt(i), m - i);
        }

        int i = 0;
        while (i <= n - m) {
            int j = 0;
            while (j < m && text.charAt(i + j) == pattern.charAt(j)) {
                j++;
            }
            if (j == m) return i;

            if (i + m >= n) break;
            char nextChar = text.charAt(i + m);
            i += shift.getOrDefault(nextChar, m + 1);
        }

        return -1;
    }
}
