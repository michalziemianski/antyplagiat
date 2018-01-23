package pl.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.commons.lang.StringUtils;
import pl.document.analize.DocumentAnalizer;
import pl.document.analize.DocumentAnalysisRequest;
import pl.document.analize.DocumentAnalysisResult;
import pl.exception.DocumentProcessingException;
import pl.document.parser.DocumentReader;
import pl.document.parser.DocumentReaderImpl;
import pl.document.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Kontroller odpowiadający za obsługę głównego ekranu aplikacji
public class MainController {
    private DocumentReader documentReader = new DocumentReaderImpl();
    private DocumentAnalizer documentAnalizer = new DocumentAnalizer();

    // Dokument do analizy
    private Document document = null;

    // Lista wymaganych elementów
    private List<String> requiredElements = new ArrayList<>();

    // Lista dokumentów do analizy
    private List<Document> documentsToCompare = new ArrayList<>();


    @FXML
    private TextArea documentTextArea;

    @FXML
    private TextField documentNameTextField;

    @FXML
    private TextField reqElemTextField;

    @FXML
    private ListView reqElemListView;

    @FXML
    private ListView docToCompListView;

    @FXML
    private Button addReqElemBtn;

    @FXML
    private Button deleteReqElemBtn;

    @FXML
    private Button addDocToCompareBtn;

    @FXML
    private Button deleteDocToCompareBtn;

    @FXML
    private Button analizeDocumentBtn;

    @FXML
    public void initialize() {

    }

    // Otwieranie dokumentu, ustawienie podglądu dokumentu oraz odblokowanie możliwości ustawiania opcji analizy
    @FXML
    public void openFileButtonClick() {
        try {
            document = documentReader.readDocument();
            documentTextArea.setText(document.getText());
            documentNameTextField.setText(document.getName());

            enableAnalysisControls();
            clearAnalysisRequest();
        } catch (DocumentProcessingException e) {
            System.out.println(e.getMessage());
        }
    }

    // Dodawanie wymaganych elementów, pobierana jest wartość z pola tekstowego i dodawana do listy elementów
    @FXML
    public void addReqElemBtnClick() {
        if(StringUtils.isNotBlank(reqElemTextField.getText())) {
            requiredElements.add(reqElemTextField.getText());
            invalidateRequiredElements();

            reqElemTextField.setText("");
        }
    }

    // Usuwanie zaznaczonego wymagannego elemenu
    @FXML
    public void deleteReqElemBtnClick() {
        String selectedItem = (String)reqElemListView.getSelectionModel().getSelectedItem();
        if(StringUtils.isNotBlank(selectedItem)) {
            requiredElements.remove(selectedItem);
            invalidateRequiredElements();
        }
    }

    // Dodawanie dokumentu do porównania, otwierane jest okienko wyszukiwania pliku, otwarty dokument dodawany jest do
    // listy dokumentów
    @FXML
    public void addDocToCompareBtnClick() {
        try {
            Document document = documentReader.readDocument();
            documentsToCompare.add(document);
            invalidateDocumentsToCompare();
        } catch (DocumentProcessingException e) {
            System.out.println(e.getMessage());
        }
    }

    // Usuwanie zaznaczonego dokumentu do porównania
    @FXML
    public void deleteDocToCompareBtnClick() {
        String selectedItem = (String)docToCompListView.getSelectionModel().getSelectedItem();
        if(StringUtils.isNotBlank(selectedItem)) {
            Document docToRemove = documentsToCompare.stream()
                    .filter(document -> document.getName().equals(selectedItem))
                    .findFirst().orElse(null);
            if(docToRemove != null) {
                documentsToCompare.remove(docToRemove);
                invalidateDocumentsToCompare();
            }
        }
    }

    // Analiza dokumentu, tworzone jest rządanie analizy, które przekazywane jest do klasy analizującej, po
    // zakończeniu analizy otwierane jest okno z wynikami
    @FXML
    public void analizeDocumentBtnClick() {
        DocumentAnalysisRequest request = new DocumentAnalysisRequest();
        request.setDocument(document);
        request.setRequiredElements(requiredElements);
        request.setDocumentsForComparison(documentsToCompare);

        try {
            DocumentAnalysisResult result = documentAnalizer.analizeDocument(request);
            openResultsWindow(result);
        } catch (DocumentProcessingException e) {
            System.out.println(e.getMessage());
        }
    }

    // Odświerzenie listy dokumentów
    private void invalidateDocumentsToCompare() {
        docToCompListView.getItems().clear();
        List<String> documentNames = documentsToCompare.stream()
                .map(Document::getName)
                .collect(Collectors.toList());
        docToCompListView.getItems().addAll(documentNames);
    }

    // Odświerzenie listy wymaganych elementów
    private void invalidateRequiredElements() {
        reqElemListView.getItems().clear();
        reqElemListView.getItems().addAll(requiredElements);
    }

    // Odblokowanie opcji analizy
    private void enableAnalysisControls() {
        addReqElemBtn.setDisable(false);
        deleteReqElemBtn.setDisable(false);
        addDocToCompareBtn.setDisable(false);
        deleteDocToCompareBtn.setDisable(false);
        analizeDocumentBtn.setDisable(false);
    }

    // Wyczyszczenie list z elementami oraz dokumentami
    private void clearAnalysisRequest() {
        reqElemListView.getItems().clear();
        docToCompListView.getItems().clear();
    }

    // Otwarcie okna z wynikami i przekazanie wyników analizy do nowego okna
    private void openResultsWindow(DocumentAnalysisResult analysisResult) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resultsView.fxml"));
            Parent resultWindowParent = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(resultWindowParent));

            ResultsController controller =
                    fxmlLoader.getController();
            controller.setAnalysisResult(analysisResult);

            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
