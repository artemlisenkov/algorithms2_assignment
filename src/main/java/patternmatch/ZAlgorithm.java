package patternmatch;

public class ZAlgorithm implements PatternMatcher {
    @Override
    public int search(String text, String pattern) {
        String combined = pattern + "$" + text;
        int[] z = computeZ(combined);
        int m = pattern.length();

        for (int i = 0; i < z.length; i++) {
            if (z[i] == m) return i - m - 1;
        }
        return -1;
    }

    private int[] computeZ(String s) {
        int n = s.length();
        int[] z = new int[n];
        int l = 0, r = 0;

        for (int i = 1; i < n; i++) {
            if (i <= r) z[i] = Math.min(r - i + 1, z[i - l]);
            while (i + z[i] < n && s.charAt(z[i]) == s.charAt(i + z[i])) z[i]++;
            if (i + z[i] - 1 > r) {
                l = i;
                r = i + z[i] - 1;
            }
        }
        return z;
    }
}
