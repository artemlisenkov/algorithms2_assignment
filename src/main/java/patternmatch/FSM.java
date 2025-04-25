package patternmatch;

import java.util.HashMap;
import java.util.Map;

public class FSM implements PatternMatcher {
    @Override
    public int search(String text, String pattern) {
        Map<Integer, Map<Character, Integer>> fsm = buildFSM(pattern);
        int state = 0;

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            state = fsm.getOrDefault(state, new HashMap<>()).getOrDefault(ch, 0);
            if (state == pattern.length()) return i - pattern.length() + 1;
        }

        return -1;
    }

    private Map<Integer, Map<Character, Integer>> buildFSM(String pattern) {
        Map<Integer, Map<Character, Integer>> fsm = new HashMap<>();
        int m = pattern.length();

        for (int state = 0; state <= m; state++) {
            for (char ch = 0; ch < 128; ch++) {
                String prefix = pattern.substring(0, state) + ch;
                int nextState = Math.min(m, prefix.length());

                while (!pattern.startsWith(prefix.substring(prefix.length() - nextState))) {
                    nextState--;
                }

                fsm.computeIfAbsent(state, k -> new HashMap<>()).put(ch, nextState);
            }
        }

        return fsm;
    }
}
