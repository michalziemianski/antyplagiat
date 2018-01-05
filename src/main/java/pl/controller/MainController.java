package pl.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apache.commons.lang.StringUtils;
import pl.document.analize.DocumentAnalizer;
import pl.document.analize.DocumentAnalysisRequest;
import pl.exception.DocumentProcessingException;
import pl.document.parser.DocumentReader;
import pl.document.parser.DocumentReaderImpl;
import pl.document.Document;
import pl.search.KappaRabinSearch;

import java.util.ArrayList;
import java.util.List;

public class MainController {
    private DocumentReader documentReader = new DocumentReaderImpl();
    private DocumentAnalizer documentAnalizer = new DocumentAnalizer();

    private Document document = null;
    private List<String> requiredElements = new ArrayList<>();

    @FXML
    private TextArea documentTextArea;

    @FXML
    private TextField documentNameTextField;

    @FXML
    private TextField reqElemTextField;

    @FXML
    private ListView reqElemListView;

    @FXML
    public void initialize() {

    }

    @FXML
    public void openFileButtonClick() {
        try {
            document = documentReader.readDocument();
            documentTextArea.setText(document.getText());
            documentNameTextField.setText(document.getName());
        } catch (DocumentProcessingException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void addReqElemBtnClick() {
        if(StringUtils.isNotBlank(reqElemTextField.getText())) {
            requiredElements.add(reqElemTextField.getText());
            reqElemListView.getItems().clear();
            reqElemListView.getItems().addAll(requiredElements);
        }
    }

    @FXML
    public void analizeDocumentBtnClick() {
        DocumentAnalysisRequest request = new DocumentAnalysisRequest();
        request.setDocument(document);
        request.setRequiredElements(requiredElements);

        try {
            documentAnalizer.analizeDocument(request);
        } catch (DocumentProcessingException e) {
            System.out.println(e.getMessage());
        }
    }
}
