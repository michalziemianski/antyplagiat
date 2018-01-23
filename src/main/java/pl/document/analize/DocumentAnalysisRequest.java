package pl.document.analize;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.document.Document;

import java.util.List;

// Zlecenie analizy, zawiera parametry (dokument, wymagane elementy, oraz dokumenty do porównania)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentAnalysisRequest {
    Document document;
    List<String> requiredElements;
    List<Document> documentsForComparison;
}
