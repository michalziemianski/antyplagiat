package pl.helper;

import pl.exception.DocumentProcessingException;
import pl.model.Document;

public interface DocumentReader {
    Document readDocument() throws DocumentProcessingException;
}
