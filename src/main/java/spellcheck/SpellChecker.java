package spellcheck;

import java.util.List;

public interface SpellChecker {
    void buildDictionary(List<String> words);
    boolean isCorrect(String word);
}
