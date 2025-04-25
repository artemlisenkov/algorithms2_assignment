import patternmatch.*;
import utils.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Main {
    private static final int REPEATS = 5;

    public static void main(String[] args) throws IOException {
        runBenchmark();
        runWackyRaces();
    }

    private static void runBenchmark() throws IOException {
        String bookText = PDFReader.extractText("data/TheMartian.pdf");
        List<Integer> textSizes = List.of(1_000, 10_000, 100_000, 500_000);

        String shortPattern = "I don’t even know who’ll read this";
        String longPattern = bookText.substring(500, 1000);

        List<PatternMatcher> matchers = List.of(
                new BruteForce(),
                new Sunday(),
                new KMP(),
                new FSM(),
                new RabinKarp(),
                new ZAlgorithm()
        );

        List<String[]> resultsShort = new ArrayList<>();
        List<String[]> resultsLong = new ArrayList<>();

        resultsShort.add(makeHeader(textSizes));
        resultsLong.add(makeHeader(textSizes));

        for (PatternMatcher matcher : matchers) {
            String name = matcher.getClass().getSimpleName();
            resultsShort.add(measure(matcher, bookText, textSizes, shortPattern, name));
            resultsLong.add(measure(matcher, bookText, textSizes, longPattern, name));
        }

        CSVWriter.saveCSV("benchmark_short.csv", resultsShort);
        CSVWriter.saveCSV("benchmark_long.csv", resultsLong);
        System.out.println("Benchmark complete.");
    }

    private static String[] makeHeader(List<Integer> sizes) {
        String[] header = new String[sizes.size() + 1];
        header[0] = "Algorithm";
        for (int i = 0; i < sizes.size(); i++) {
            header[i + 1] = String.valueOf(sizes.get(i));
        }
        return header;
    }

    private static String[] measure(PatternMatcher matcher, String fullText, List<Integer> sizes, String pattern, String name) {
        String[] row = new String[sizes.size() + 1];
        row[0] = name;

        for (int i = 0; i < sizes.size(); i++) {
            int size = sizes.get(i);
            String text = fullText.substring(0, Math.min(size, fullText.length()));

            long total = 0;
            for (int r = 0; r < REPEATS; r++) {
                long start = System.nanoTime();
                matcher.search(text, pattern);
                long end = System.nanoTime();
                total += (end - start);
            }

            row[i + 1] = String.valueOf((double) total / REPEATS / 1_000_000.0);
        }

        return row;
    }

    private static void runWackyRaces() throws IOException {
        runCase("Case 1: Binary Sunday vs Gusfield Z",
                new Sunday(), new ZAlgorithm(),
                "data/case1_T.txt", "data/case1_P.txt");

        runCase("Case 2: KMP vs Rabin-Karp",
                new KMP(), new RabinKarp(),
                "data/case2_T.txt", "data/case2_P.txt");

        runCase("Case 3: Rabin-Karp vs Sunday",
                new RabinKarp(), new Sunday(),
                "data/case3_T.txt", "data/case3_P.txt");
    }

    private static void runCase(String label,
                                PatternMatcher faster,
                                PatternMatcher slower,
                                String tFile,
                                String pFile) throws IOException {
        String text = Files.readString(Path.of(tFile));
        String pattern = Files.readString(Path.of(pFile));

        double fastTime = avgRuntime(faster, text, pattern);
        double slowTime = avgRuntime(slower, text, pattern);

        System.out.println("=== " + label + " ===");
        System.out.printf("Faster: %s - %.3f ms%n", faster.getClass().getSimpleName(), fastTime);
        System.out.printf("Slower: %s - %.3f ms%n", slower.getClass().getSimpleName(), slowTime);
        System.out.printf("Ratio: %.2fx%n%n", slowTime / fastTime);
    }

    private static double avgRuntime(PatternMatcher matcher, String text, String pattern) {
        long total = 0;
        for (int i = 0; i < REPEATS; i++) {
            long start = System.nanoTime();
            matcher.search(text, pattern);
            long end = System.nanoTime();
            total += (end - start);
        }
        return (double) total / REPEATS / 1_000_000.0;
    }
}
