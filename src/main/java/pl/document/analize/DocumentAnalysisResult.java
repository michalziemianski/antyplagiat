package pl.document.analize;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.document.Document;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentAnalysisResult {
    Map<String, Boolean> requiredElementsResult = new HashMap<>();
    Map<Document, Double> documentsComparisonResult = new HashMap<>();
}
