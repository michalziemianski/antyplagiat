package pl.helper;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import pl.exception.DocumentProcessingException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DocxDocumentParser implements DocumentParser {
    @Override
    public String parseDocument(File documentFile) throws DocumentProcessingException {
        try {
            InputStream fileStream = new FileInputStream(documentFile);
            XWPFDocument document = new XWPFDocument(fileStream);

            XWPFWordExtractor wordExtractor = new XWPFWordExtractor(document);

            return wordExtractor.getText();
        } catch (IOException ex) {
            throw new DocumentProcessingException("Wystąpił problem podczas wczytywania dokumentu WORD.");
        }
    }
}
