package pl.cache;

import java.util.HashMap;
import java.util.Map;

// Klasa odpowiadająca za przechowywanie haszy słów
// Przechowywane są one w mapie, gdzie kluczem jest słowo, a wartością hasz
public class WordHashCache {

    private static Map<String, Long> wordHashMap = new HashMap<>();

    // Pobranie wartości hasza z mapy
    public static Long getHash(String word) {
        return wordHashMap.get(word);
    }

    // Dodanie nowej pary słowo/hasz do mapy
    public static void putHash(String word, Long hash) {
        wordHashMap.putIfAbsent(word, hash);
    }
}
