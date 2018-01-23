package pl.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;
import pl.document.analize.DocumentAnalysisResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Kontroler obsługujący ekran wyników
public class ResultsController {
    private DocumentAnalysisResult analysisResult;
    private List<String> requiredElementsResults = new ArrayList<>();
    private List<String> documentsResults = new ArrayList<>();

    @FXML
    private TitledPane resultsTitledPane;

    @FXML
    private ListView reqElemListView;

    @FXML
    private ListView docToCompListView;

    @FXML
    private Button closeBtn;

    @FXML
    public void initialize() {

    }

    // Zamknięcie okna wyników
    @FXML
    private void onCloseBtnClick(){
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }

    // Ustawienie tytułu oraz wyników
    public void setAnalysisResult(DocumentAnalysisResult analysisResult) {
        this.analysisResult = analysisResult;

        resultsTitledPane.setText("Wyniki analizy - " + analysisResult.getDocument().getName());
        setResults(analysisResult);
    }

    // Przetworzenie wyników do czytelnej formy w postaci: element - wynik, przetworzone elementy dodawane są
    // do list na ekranie wyników
    private void setResults(DocumentAnalysisResult analysisResult) {
        requiredElementsResults = analysisResult.getRequiredElementsResult().entrySet().stream()
                .map(stringBooleanEntry -> {
                    String resultText = stringBooleanEntry.getKey() + " - ";
                    resultText += stringBooleanEntry.getValue() ? "TAK" : "NIE";
                    return resultText;
                }).collect(Collectors.toList());

        reqElemListView.getItems().clear();
        reqElemListView.getItems().addAll(requiredElementsResults);

        documentsResults = analysisResult.getDocumentsComparisonResult().entrySet().stream()
                .map(documentDoubleEntry -> {
                    String resultText = documentDoubleEntry.getKey().getName() + " - ";
                    Double multipliedResult = documentDoubleEntry.getValue()*100.0;
                    resultText += multipliedResult.intValue() + "%";
                    return resultText;
                }).collect(Collectors.toList());

        docToCompListView.getItems().clear();
        docToCompListView.getItems().addAll(documentsResults);
    }
}
