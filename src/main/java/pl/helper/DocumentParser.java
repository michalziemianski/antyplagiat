package pl.helper;

import pl.exception.DocumentProcessingException;

import java.io.File;

public interface DocumentParser {
    String parseDocument(File documentFile) throws DocumentProcessingException;
}
