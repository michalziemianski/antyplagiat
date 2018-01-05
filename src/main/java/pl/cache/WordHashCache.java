package pl.cache;

import java.util.HashMap;
import java.util.Map;

public class WordHashCache {

    private static Map<String, Long> wordHashMap = new HashMap<>();

    public static Long getHash(String word) {
        return wordHashMap.get(word);
    }

    public static void putHash(String word, Long hash) {
        wordHashMap.putIfAbsent(word, hash);
    }
}
