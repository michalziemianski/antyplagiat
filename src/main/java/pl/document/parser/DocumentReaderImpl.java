package pl.document.parser;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;
import pl.exception.DocumentProcessingException;
import pl.document.Document;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DocumentReaderImpl implements DocumentReader {
    private FileChooser fileChooser = new FileChooser();

    public DocumentReaderImpl() {
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Dokument tekstowy", "*.txt", "*.docx"));
    }

    @Override
    public Document readDocument() throws DocumentProcessingException {
        File documentFile = openDocumentFile();

        Document document = new Document();
        DocumentParser parser = getParserByExtension(FilenameUtils.getExtension(documentFile.getPath()));

        document.setName(documentFile.getName());
        document.setText(parser.parseDocument(documentFile));
        document.setWords(extractWordsFromDocuments(document.getText()));

        return document;
    }

    private File openDocumentFile() throws DocumentProcessingException {
        File documentFile = fileChooser.showOpenDialog(new Stage());
        if(documentFile == null) {
            throw new DocumentProcessingException("Nie można otworzyć wybranego pliku.");
        }

        return documentFile;
    }

    private DocumentParser getParserByExtension(String extension) throws DocumentProcessingException {
        DocumentParser parser;
        switch(extension) {
            case "txt":
                parser = new TxtDocumentParser();
                break;
            case "docx":
                parser = new DocxDocumentParser();
                break;
            default:
                throw new DocumentProcessingException("Rozszerzenie nie jest wspierane.");
        }

        return parser;
    }

    private List<String> extractWordsFromDocuments(String documentText) throws DocumentProcessingException {
        if(documentText != null) {
            return Arrays.asList(documentText.split("\\W+")).stream().map(String::toLowerCase).collect(Collectors.toList());
        }
        else {
            throw new DocumentProcessingException("Dokument nie może być pusty.");
        }
    }
}
