package pl.search;

import pl.cache.WordHashCache;

import java.util.List;

public class KappaRabinSearch {
    public int findSentenceOccurence(List<String> document, List<String> searchSentence) {
        int index = 0;
        int occurences = 0;
        Long sentenceHash = hashSentence(searchSentence);

        while(index < document.size()-searchSentence.size()) {
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

    private Long hashSentence(List<String> sentence) {
        return sentence.stream()
                .map(s -> hashWord(s))
                .reduce(0L, Long::sum);
    }

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
