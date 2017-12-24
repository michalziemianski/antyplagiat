package pl.helper;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import pl.exception.DocumentProcessingException;
import pl.model.Document;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

public class DocumentReaderImpl implements DocumentReader {
    private FileChooser fileChooser = new FileChooser();

    public DocumentReaderImpl() {
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Dokument tekstowy", "*.txt", "*.docx"));
    }

    @Override
    public Document readDocument() throws DocumentProcessingException {
        File documentFile = fileChooser.showOpenDialog(new Stage());

        Document document = new Document();
        switch(FilenameUtils.getExtension(documentFile.getPath())) {
            case "txt":
                document.setText(readTxtDocumentFromFile(documentFile));
                break;
            case "docx":
                document.setText(readDocxDocumentFromFile(documentFile));
                break;
            default:
                throw new DocumentProcessingException("extension not supported");
        }

        document.setWords(extractWordsFromDocuments(document.getText()));
        return document;
    }

    private String readTxtDocumentFromFile(File documentFile) throws DocumentProcessingException {
        String documentText;
        try {
            documentText = FileUtils.readFileToString(documentFile, Charset.forName("UTF8"));
        } catch (Exception e) {
            throw new DocumentProcessingException(e.getMessage(), e);
        }

        return documentText;
    }

    private String readDocxDocumentFromFile(File documentFile) throws DocumentProcessingException {
        throw new DocumentProcessingException("Docx format in not supported.");
    }

    private List<String> extractWordsFromDocuments(String documentText) throws DocumentProcessingException {
        if(documentText != null) {
            return Arrays.asList(documentText.split("\\W+"));
        }
        else {
            throw new DocumentProcessingException("Document text can't be null");
        }
    }
}
