package pl.document.analize;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.document.Document;

import java.util.HashMap;
import java.util.Map;

// Wynik analizy - wymagane elementy w formie prawda/fałsz, porównania dokumentów w formie liczby 0 - 1
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentAnalysisResult {
    Document document;
    Map<String, Boolean> requiredElementsResult = new HashMap<>();
    Map<Document, Double> documentsComparisonResult = new HashMap<>();
}
