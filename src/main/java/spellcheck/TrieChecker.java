package spellcheck;

import java.util.List;

public class TrieChecker implements SpellChecker {
    private static class Node {
        boolean isWord;
        Node[] children = new Node[26];
    }

    private final Node root = new Node();

    @Override
    public void buildDictionary(List<String> words) {
        for (String word : words) insert(word.toLowerCase());
    }

    private void insert(String word) {
        Node node = root;
        for (char c : word.toCharArray()) {
            if (!Character.isLetter(c)) continue;
            int i = c - 'a';
            if (node.children[i] == null) node.children[i] = new Node();
            node = node.children[i];
        }
        node.isWord = true;
    }

    @Override
    public boolean isCorrect(String word) {
        Node node = root;
        for (char c : word.toLowerCase().toCharArray()) {
            if (!Character.isLetter(c)) continue;
            int i = c - 'a';
            if (node.children[i] == null) return false;
            node = node.children[i];
        }
        return node.isWord;
    }
}
