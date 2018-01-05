package pl.document.analize;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.document.Document;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentAnalysisRequest {
    Document document;
    List<String> requiredElements;
    List<Document> documentsForComparison;
}
