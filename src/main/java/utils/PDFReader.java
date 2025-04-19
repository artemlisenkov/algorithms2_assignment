package utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

public class PDFReader {
    public static String extractText(String pathToPdf) throws IOException {
        try (PDDocument document = PDDocument.load(new File(pathToPdf))) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }
}