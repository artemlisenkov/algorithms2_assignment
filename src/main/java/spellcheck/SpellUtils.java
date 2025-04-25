package spellcheck;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class SpellUtils {

    public static List<String> loadDictionary(String path) throws IOException {
        return Files.readAllLines(Path.of(path))
                .stream()
                .map(String::toLowerCase)
                .filter(w -> !w.isBlank())
                .toList();
    }

    public static List<String> loadWordsFromText(String path) throws IOException {
        String text = Files.readString(Path.of(path)).toLowerCase();
        String[] tokens = text.split("[^a-zA-Z]+");
        return Arrays.stream(tokens)
                .filter(w -> !w.isBlank())
                .toList();
    }

    public static List<List<String>> splitByWordCount(List<String> words, List<Integer> sizes) {
        List<List<String>> result = new ArrayList<>();
        for (int size : sizes) {
            int len = Math.min(size, words.size());
            result.add(words.subList(0, len));
        }
        return result;
    }
}
