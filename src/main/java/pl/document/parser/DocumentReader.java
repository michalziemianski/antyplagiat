package pl.document.parser;

import pl.exception.DocumentProcessingException;
import pl.document.Document;

public interface DocumentReader {
    Document readDocument() throws DocumentProcessingException;
}
