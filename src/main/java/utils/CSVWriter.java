package utils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CSVWriter {
    public static void saveCSV(String filename, List<String[]> data) throws IOException {
        Path path = Path.of(filename);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (String[] row : data) {
                writer.write(String.join(",", row));
                writer.newLine();
            }
        }
    }
}
