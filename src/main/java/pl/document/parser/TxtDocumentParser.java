package pl.document.parser;

import org.apache.commons.io.FileUtils;
import pl.document.parser.DocumentParser;
import pl.exception.DocumentProcessingException;

import java.io.File;
import java.nio.charset.Charset;

public class TxtDocumentParser implements DocumentParser {
    @Override
    public String parseDocument(File documentFile) throws DocumentProcessingException {
        String documentText;
        try {
            documentText = FileUtils.readFileToString(documentFile, Charset.forName("UTF8"));
        } catch (Exception e) {
            throw new DocumentProcessingException(e.getMessage(), e);
        }

        return documentText;
    }
}
