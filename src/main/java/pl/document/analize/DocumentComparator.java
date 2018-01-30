package pl.document.analize;

import pl.document.Document;
import pl.exception.DocumentProcessingException;
import pl.search.KappaRabinSearch;

import java.util.*;

// Klasa porównująca pojedyńczy dokument z wzorcem
public class DocumentComparator {
    private KappaRabinSearch search = new KappaRabinSearch();

    // Progi przeszukiwania - liczba słów szukanych elementów
    private static List<Integer> gateSizes = new ArrayList<>(Arrays.asList(2));

    // Porównanie dokumentów i sumowanie wyniku dla dokumentu
    public Double compareDocuments(Document document, Document toCompare) throws DocumentProcessingException {
        Integer totalResultWeight = 0;
        Double comparisonResult = 0.0;
        for(Integer gateSize : gateSizes) {
            Double resultForGateSize = analizeForGateSize(document, toCompare, gateSize);
            if(resultForGateSize > 0.0) {
                comparisonResult += resultForGateSize * gateSize;
                totalResultWeight += gateSize;
            }
        }

        return comparisonResult / totalResultWeight;
    }

    // Analiza dla zadanego progu przeszukiwania
    // Dokument jest przeglądany słowo po słowie, konstruowane są zdania o długości progu i wyszukiwanie w porównywanym dokumencie
    private Double analizeForGateSize(Document document, Document toCompare, Integer gateSize) throws DocumentProcessingException {
        int index = 0;
        Double result = 0.0;
        while(index <= document.getWords().size()-gateSize) {
            List<String> currentSentence = document.getWords().subList(index, index + gateSize);
            Double currentSentenceResult = checkSentence(toCompare, currentSentence);
            if (currentSentenceResult > 0.0) {
                result += currentSentenceResult;
                index += gateSize;
            } else {
                index++;
            }
        }

        return result;
    }

    // Sprawdzenie czy w dokumencie występuje dane zdanie, wyznaczana jest liczba wystąpień i obliczany stosunek zdania do liczby słów w dokumencie
    private Double checkSentence(Document document, List<String> sentence) throws DocumentProcessingException {
        Double numberOfOccurrences = Double.valueOf(search.findSentenceOccurence(document.getWords(), sentence));
        if(numberOfOccurrences > 0.0) {
            Double numberOfWordsInDocument = Double.valueOf(document.getWords().size());
            Double termFrequency = (numberOfOccurrences * sentence.size()) / numberOfWordsInDocument;
            return termFrequency;
        }
        return Double.valueOf(0);
    }
}
