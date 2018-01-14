package pl.document;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@EqualsAndHashCode
public class Document {
    private String name;
    private String text;
    private List<String> words;

    public Document() {
        this.name = "";
        this.text = "";
        this.words = new ArrayList<>();
    }
}
