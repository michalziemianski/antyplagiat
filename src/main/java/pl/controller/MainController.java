package pl.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import pl.exception.DocumentProcessingException;
import pl.helper.DocumentReader;
import pl.helper.DocumentReaderImpl;
import pl.model.Document;

public class MainController {
    private DocumentReader documentReader = new DocumentReaderImpl();

    Document document = null;

    @FXML
    private TextArea documentTextArea;

    @FXML
    private TitledPane documentPane;

    @FXML
    public void initialize() {

    }

    @FXML
    public void openFileButtonClick() {
        try {
            document = documentReader.readDocument();
            documentTextArea.setText(document.getText());
        } catch (DocumentProcessingException e) {
            System.out.println(e.getMessage());
        }
    }
}
