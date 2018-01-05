package pl.document.analize;

import pl.document.Document;
import pl.exception.DocumentProcessingException;
import pl.search.KappaRabinSearch;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DocumentAnalizer {
    private KappaRabinSearch search = new KappaRabinSearch();

    public DocumentAnalysisResult analizeDocument(DocumentAnalysisRequest request) throws DocumentProcessingException {
        DocumentAnalysisResult result = new DocumentAnalysisResult();
        checkRequiredElements(request, result);

        return result;
    }

    private void checkRequiredElements(DocumentAnalysisRequest request, DocumentAnalysisResult result) throws DocumentProcessingException {
        for(String requiredElement : request.getRequiredElements()) {
            Boolean checkResult = checkRequiredElement(request.getDocument(), requiredElement);
            result.getRequiredElementsResult().putIfAbsent(requiredElement, checkResult);
        }
    }

    private Boolean checkRequiredElement(Document document, String requiredElement) throws DocumentProcessingException {
        List<String> requiredElementWords = getWordsFromText(requiredElement);

        return search.findSentenceOccurence(document.getWords(), requiredElementWords) > 0;
    }

    private List<String> getWordsFromText(String text) throws DocumentProcessingException {
        if(text != null) {
            return Arrays.asList(text.split("\\W+")).stream().map(String::toLowerCase).collect(Collectors.toList());
        }
        else {
            throw new DocumentProcessingException("Dokument nie może być pusty.");
        }
    }
}
