package algorithms;

public class KMP implements PatternMatcher {
    @Override
    public int search(String text, String pattern) {
        int[] lps = buildLPS(pattern);
        int i = 0, j = 0;
        int n = text.length(), m = pattern.length();

        while (i < n) {
            if (text.charAt(i) == pattern.charAt(j)) {
                i++; j++;
                if (j == m) return i - j;
            } else {
                if (j != 0) j = lps[j - 1];
                else i++;
            }
        }
        return -1;
    }

    private int[] buildLPS(String pattern) {
        int m = pattern.length();
        int[] lps = new int[m];
        int len = 0, i = 1;

        while (i < m) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                lps[i++] = ++len;
            } else {
                if (len != 0) len = lps[len - 1];
                else lps[i++] = 0;
            }
        }
        return lps;
    }
}
