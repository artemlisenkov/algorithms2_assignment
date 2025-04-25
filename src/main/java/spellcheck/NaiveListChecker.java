package spellcheck;

import java.util.ArrayList;
import java.util.List;

public class NaiveListChecker implements SpellChecker {
    private final List<String> dictionary = new ArrayList<>();

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
