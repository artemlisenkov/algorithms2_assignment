package spellcheck;

import utils.CSVWriter;

import java.io.IOException;
import java.util.*;

public class SpellBenchmarkMain {
    private static final int REPEATS = 5;

    private static final List<Integer> TEXT_SIZES = List.of(1000, 5000, 10000, 20000);
    private static final List<SpellChecker> CHECKERS = List.of(
            new NaiveListChecker(),
            new TreeSetChecker(),
            new HashSetChecker(),
            new TrieChecker()
    );

    private static final List<String> NAMES = List.of("NaiveList", "TreeSet", "HashSet", "Trie");

    public static void main(String[] args) throws IOException {
        List<String> dictionary = SpellUtils.loadDictionary("data/english.txt");
        List<String> fullText = SpellUtils.loadWordsFromText("data/spell_text.txt");
        List<List<String>> subsets = SpellUtils.splitByWordCount(fullText, TEXT_SIZES);

        List<String[]> buildResults = new ArrayList<>();
        List<String[]> checkResults = new ArrayList<>();

        buildResults.add(makeHeader(TEXT_SIZES));
        checkResults.add(makeHeader(TEXT_SIZES));

        for (int i = 0; i < CHECKERS.size(); i++) {
            SpellChecker checker = CHECKERS.get(i);
            String name = NAMES.get(i);

            buildResults.add(measureBuildTime(checker, name, dictionary, TEXT_SIZES));
            checkResults.add(measureCheckTime(checker, name, dictionary, subsets));
        }

        CSVWriter.saveCSV("spell_build.csv", buildResults);
        CSVWriter.saveCSV("spell_check.csv", checkResults);
        System.out.println("Spell benchmarking complete.");
    }

    private static String[] makeHeader(List<Integer> sizes) {
        String[] row = new String[sizes.size() + 1];
        row[0] = "Structure";
        for (int i = 0; i < sizes.size(); i++) {
            row[i + 1] = String.valueOf(sizes.get(i));
        }
        return row;
    }

    private static String[] measureBuildTime(SpellChecker checker, String name, List<String> dict, List<Integer> sizes) {
        String[] row = new String[sizes.size() + 1];
        row[0] = name;

        for (int i = 0; i < sizes.size(); i++) {
            List<String> subDict = dict.subList(0, Math.min(sizes.get(i), dict.size()));
            long total = 0;
            for (int r = 0; r < REPEATS; r++) {
                long start = System.nanoTime();
                checker.buildDictionary(subDict);
                long end = System.nanoTime();
                total += (end - start);
            }
            row[i + 1] = String.format(Locale.US, "%.3f", total / REPEATS / 1_000_000.0);
        }

        return row;
    }

    private static String[] measureCheckTime(SpellChecker checker, String name, List<String> dict, List<List<String>> inputs) {
        String[] row = new String[inputs.size() + 1];
        row[0] = name;
        checker.buildDictionary(dict);

        for (int i = 0; i < inputs.size(); i++) {
            List<String> input = inputs.get(i);
            long total = 0;
            for (int r = 0; r < REPEATS; r++) {
                long start = System.nanoTime();
                for (String word : input) {
                    checker.isCorrect(word);
                }
                long end = System.nanoTime();
                total += (end - start);
            }
            row[i + 1] = String.format(Locale.US, "%.3f", total / REPEATS / 1_000_000.0);
        }

        return row;
    }
}
