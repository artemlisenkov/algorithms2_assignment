package patternmatch;

public class RabinKarp implements PatternMatcher {
    private static final int BASE = 256;
    private static final int MOD = 101;

    @Override
    public int search(String text, String pattern) {
        int n = text.length(), m = pattern.length();
        if (m > n) return -1;

        int patternHash = 0, textHash = 0, h = 1;

        for (int i = 0; i < m - 1; i++) h = (h * BASE) % MOD;

        for (int i = 0; i < m; i++) {
            patternHash = (BASE * patternHash + pattern.charAt(i)) % MOD;
            textHash = (BASE * textHash + text.charAt(i)) % MOD;
        }

        for (int i = 0; i <= n - m; i++) {
            if (patternHash == textHash) {
                int j = 0;
                while (j < m && text.charAt(i + j) == pattern.charAt(j)) j++;
                if (j == m) return i;
            }
            if (i < n - m) {
                textHash = (BASE * (textHash - text.charAt(i) * h) + text.charAt(i + m)) % MOD;
                if (textHash < 0) textHash += MOD;
            }
        }

        return -1;
    }
}
