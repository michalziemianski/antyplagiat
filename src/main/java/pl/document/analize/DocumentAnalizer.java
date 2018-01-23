package pl.document.analize;

import pl.document.Document;
import pl.exception.DocumentProcessingException;
import pl.search.KappaRabinSearch;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// Klasa odpowiadająca za analizę dokumentu
public class DocumentAnalizer {
    private KappaRabinSearch search = new KappaRabinSearch();

    // Punkt wejściowy analizy, inicjuje wyszukiwanie wymaganych elementów oraz porównywanie dokumentów
    public DocumentAnalysisResult analizeDocument(DocumentAnalysisRequest request) throws DocumentProcessingException {
        DocumentAnalysisResult result = new DocumentAnalysisResult();
        result.setDocument(request.getDocument());
        checkRequiredElements(request, result);
        compareDocuments(request, result);
        return result;
    }

    // Porównanie dokumentów i dodanie wyników
    private void compareDocuments(DocumentAnalysisRequest request, DocumentAnalysisResult result) throws DocumentProcessingException {
        for(Document toCompare : request.getDocumentsForComparison()) {
            DocumentComparator comparator = new DocumentComparator();
            Double comparisonResult = comparator.compareDocuments(request.getDocument(), toCompare);

            result.getDocumentsComparisonResult().putIfAbsent(toCompare, comparisonResult);
        }
    }

    // Sprawdzanie wymaganych elementów
    private void checkRequiredElements(DocumentAnalysisRequest request, DocumentAnalysisResult result) throws DocumentProcessingException {
        for(String requiredElement : request.getRequiredElements()) {
            Boolean checkResult = checkRequiredElement(request.getDocument(), requiredElement);
            result.getRequiredElementsResult().putIfAbsent(requiredElement, checkResult);
        }
    }

    // Sprawdzanie wymaganego elementu - wyłuskanie słów z danego ciągu znaków i przeszukanie dokumentu pod kątem wystąpień
    // Wynik pozytywny jeśli liczba wystąpień jest większa od 0
    private Boolean checkRequiredElement(Document document, String requiredElement) throws DocumentProcessingException {
        List<String> requiredElementWords = getWordsFromText(requiredElement);

        return search.findSentenceOccurence(document.getWords(), requiredElementWords) > 0;
    }

    // Wyłuskanie słów z danego ciągu znaków - dzielenie po znakach specjalnyc
    private List<String> getWordsFromText(String text) throws DocumentProcessingException {
        if(text != null) {
            return Arrays.asList(text.split("\\W+")).stream().map(String::toLowerCase).collect(Collectors.toList());
        }
        else {
            throw new DocumentProcessingException("Dokument nie może być pusty.");
        }
    }
}
