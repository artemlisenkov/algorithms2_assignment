package utils;

import java.util.ArrayList;
import java.util.List;

public class WildCardUtil {

    public enum TokenType { LITERAL, ANY, STAR }

    public static class Token {
        private final TokenType type;
        private final char c;
        public Token(TokenType type, char c) {
            this.type = type;
            this.c = c;
        }
        public TokenType getType() { return type; }
        public char getChar()    { return c;    }
    }

    /**
     * My body is a Machine that turns
     * \?, \*, \\, ?, *, anythingElse
     * into
     * LITERAL('?'), LITERAL('*'), LITERAL('\'), ANY, STAR, LITERAL(c)
     */
    public static List<Token> tokenize(String pat) {
        List<Token> tokens = new ArrayList<>();
        for (int i = 0; i < pat.length(); i++) {
            char c = pat.charAt(i);
            if (c == '\\' && i + 1 < pat.length()) {
                tokens.add(new Token(TokenType.LITERAL, pat.charAt(++i)));
            } else if (c == '?') {
                tokens.add(new Token(TokenType.ANY, '\0'));
            } else if (c == '*') {
                tokens.add(new Token(TokenType.STAR, '\0'));
            } else {
                tokens.add(new Token(TokenType.LITERAL, c));
            }
        }
        return tokens;
    }

    public static int minimalMatchLength(List<Token> tokens) {
        int count = 0;
        for (Token t : tokens) {
            if (t.getType() != TokenType.STAR) {
                count++;
            }
        }
        return count;
    }

    //true if tokens match text starting at index ti.
    public static boolean matchesAt(String text, int ti, List<Token> tokens) {
        int n = text.length();
        int t = ti;
        int pi = 0, lastStar = -1, starMatchPos = -1;

        // loop until we either run out of text or read the full pattern
        while (t < n && pi < tokens.size()) {
            Token tok = tokens.get(pi);
            switch (tok.getType()) {
                case LITERAL:
                    if (text.charAt(t) == tok.getChar()) {
                        t++; pi++;
                        continue;
                    }
                    break;
                case ANY:
                    t++; pi++;
                    continue;
                case STAR:
                    lastStar = pi;
                    starMatchPos = t;
                    pi++;
                    continue;
            }
            // mismatch goto last * if was
            if (lastStar != -1) {
                pi = lastStar + 1;
                starMatchPos++;
                t = starMatchPos;
            } else {
                return false;
            }
        }

        // skip any remaining *
        while (pi < tokens.size() && tokens.get(pi).getType() == TokenType.STAR) {
            pi++;
        }

        // sukces
        return pi == tokens.size();
    }
}
