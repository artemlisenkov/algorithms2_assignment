//import algorithms.*;
//import utils.PDFReader;
//
//import java.io.*;
//import java.nio.file.*;
//import java.util.*;
//
//public class Benchmark {
//    private static final int REPEATS = 5;
//
//    private static final List<PatternMatcher> matchers = List.of(
//            new BruteForce(),
//            new Sunday(),
//            new KMP(),
//            new FSM(),
//            new RabinKarp(),
//            new ZAlgorithm()
//    );
//
//    private static final List<String> matcherNames = List.of(
//            "BruteForce",
//            "Sunday",
//            "KMP",
//            "FSM",
//            "RabinKarp",
//            "ZAlgorithm"
//    );
//
//    public static void main(String[] args) throws IOException {
//        String bookText = PDFReader.extractText("TheMartian.pdf");
//        List<Integer> textSizes = List.of(1_000, 10_000, 100_000, 500_000);
//
//        String shortPattern = "I don’t even know who’ll read this";
//        String longPattern = bookText.substring(500, 1000);
//
//        List<String[]> resultsShort = new ArrayList<>();
//        List<String[]> resultsLong = new ArrayList<>();
//
//        resultsShort.add(makeHeader(textSizes));
//        resultsLong.add(makeHeader(textSizes));
//
//        for (int i = 0; i < matchers.size(); i++) {
//            PatternMatcher matcher = matchers.get(i);
//            String name = matcherNames.get(i);
//
//            resultsShort.add(measure(matcher, bookText, textSizes, shortPattern, name));
//            resultsLong.add(measure(matcher, bookText, textSizes, longPattern, name));
//        }
//
//        saveCSV("benchmark_short.csv", resultsShort);
//        saveCSV("benchmark_long.csv", resultsLong);
//        System.out.println("Benchmark complete.");
//    }
//
//    private static String[] makeHeader(List<Integer> sizes) {
//        String[] header = new String[sizes.size() + 1];
//        header[0] = "Algorithm";
//        for (int i = 0; i < sizes.size(); i++) {
//            header[i + 1] = String.valueOf(sizes.get(i));
//        }
//        return header;
//    }
//
//    private static String[] measure(PatternMatcher matcher, String fullText, List<Integer> sizes, String pattern, String name) {
//        String[] row = new String[sizes.size() + 1];
//        row[0] = name;
//
//        for (int i = 0; i < sizes.size(); i++) {
//            int size = sizes.get(i);
//            String text = fullText.substring(0, Math.min(size, fullText.length()));
//
//            long total = 0;
//            for (int r = 0; r < REPEATS; r++) {
//                long start = System.nanoTime();
//                matcher.search(text, pattern);
//                long end = System.nanoTime();
//                total += (end - start);
//            }
//
//            row[i + 1] = String.valueOf((double) total / REPEATS / 1_000_000.0);
//        }
//
//        return row;
//    }
//
//    private static void saveCSV(String filename, List<String[]> data) throws IOException {
//        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(filename))) {
//            for (String[] row : data) {
//                writer.write(String.join(",", row));
//                writer.newLine();
//            }
//        }
//    }
//}
