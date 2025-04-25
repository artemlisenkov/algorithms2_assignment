package spellcheck;

import java.util.HashSet;
import java.util.List;

public class HashSetChecker implements SpellChecker {
    private final HashSet<String> dictionary = new HashSet<>();

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
