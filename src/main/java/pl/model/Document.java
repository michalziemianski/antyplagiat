package pl.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Document {
    private String text;
    private List<String> words;
    private Map<String, Long> wordHashMap;

    public Document() {
        this.text = "";
        this.words = new ArrayList<>();
        this.wordHashMap = new HashMap<>();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }

    public Map<String, Long> getWordHashMap() {
        return wordHashMap;
    }

    public void setWordHashMap(Map<String, Long> wordHashMap) {
        this.wordHashMap = wordHashMap;
    }
}
