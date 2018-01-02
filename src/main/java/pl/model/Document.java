package pl.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Document {
    private String text;
    private List<String> words;
    private Map<String, Long> wordHashMap;

    public Document() {
        this.text = "";
        this.words = new ArrayList<>();
        this.wordHashMap = new HashMap<>();
    }
}
