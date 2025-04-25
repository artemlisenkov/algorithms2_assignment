package spellcheck;

import java.util.List;
import java.util.TreeSet;

public class TreeSetChecker implements SpellChecker {
    private final TreeSet<String> dictionary = new TreeSet<>();

    @Override
    public void buildDictionary(List<String> words) {
        dictionary.clear();
        dictionary.addAll(words);
    }

    @Override
    public boolean isCorrect(String word) {
        return dictionary.contains(word);
    }
}
