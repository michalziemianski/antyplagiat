package pl.search;

import pl.cache.WordHashCache;

import java.util.List;

// Klasa stanowiąca implementację algorytmu Kappa-Rabina
public class KappaRabinSearch {
    // Wyszukiwanie występowania zadanego zdania w dokumencie
    // Wyznaczany jest hasz zdania, następnie dokument jest przeszukiwany słowo po słowie
    // W każdej iteracji konstruowane jest słowo o długości słowa wzorcowego, obliczanie jego hasza
    // oraz sprawdzenie czy zdania są identyczne
    public int findSentenceOccurence(List<String> document, List<String> searchSentence) {
        int index = 0;
        int occurences = 0;
        Long sentenceHash = hashSentence(searchSentence);

        while(index <= document.size()-searchSentence.size()) {
            List<String> currentSentence = document.subList(index, index + searchSentence.size());
            Long currentSentenceHash = hashSentence(currentSentence);
            if(sentenceHash.equals(currentSentenceHash)
                    && checkSentenceEquality(searchSentence, currentSentence)) {
                occurences++;
                index += searchSentence.size();
            } else {
                index++;
            }
        }

        return occurences;
    }

    // Sprawdzenie identyczności zdań, najpierw sprawdzenie czy zdania są tej samej długości,
    // następnie sprawdzanie słowo po słowie
    private boolean checkSentenceEquality(List<String> searchSentence, List<String> currentSentence) {
        if(searchSentence.size() != currentSentence.size()) {
            return false;
        }

        for(int i = 0; i < searchSentence.size(); i++) {
            if(!searchSentence.get(i).equals(currentSentence.get(i))) {
                return false;
            }
        }

        return true;
    }

    // Funkcja konstruująca hasz zdania, na który składa się suma haszy słów występujących w zdaniu
    private Long hashSentence(List<String> sentence) {
        return sentence.stream()
                .map(s -> hashWord(s))
                .reduce(0L, Long::sum);
    }

    // Haszowanie słowa polega na sumowaniu wartości liczbowej znaku ASCII
    // Najpierw sprawdzany jest statyczny cache haszy słów, jeśli znaleziono to nie ma potrzeby wykonywania
    // operacji haszowania, jeśli nie, hasz jest obliczany i zapisywany do cache
    private Long hashWord(String word) {
        if(word.length() == 0) {
            return 0L;
        }

        Long hash = WordHashCache.getHash(word);
        if(hash == null) {
            hash = 0L;
            for(char c : word.toCharArray()) {
                hash += (long)c;
            }
            WordHashCache.putHash(word, hash);
        }

        return hash;
    }
}
