import java.util.*;
import java.util.stream.Collectors;

public class TextAnalyzer{
    private String text;

    public TextAnalyzer(String text) {
        this.text = text;
    }

    public Map<String, Integer> countWordOccurrences() {
        String[] words = text.split("\\s+");
        Map<String, Integer> wordOccurrences = new HashMap<>();

        for (String word : words) {
            word = word.replaceAll("[^a-zA-Z]", "");

            if (!word.isEmpty()) {
                wordOccurrences.put(word, wordOccurrences.getOrDefault(word, 0) + 1);
            }
        }

        return wordOccurrences;
    }

    public List<String> getUniqueWords(int limit) {
        String[] words = text.split("\\s+");
        Set<String> uniqueWords = new HashSet<>();
        Set<String> encounteredWords = new HashSet<>();

        for (String word : words) {
            word = word.replaceAll("[^a-zA-Z]", "");

            if (!word.isEmpty()  && encounteredWords.add(word)) {
                uniqueWords.add(word);
            }
            if (uniqueWords.size() >= limit) {
                break;
            }
        }

        return new ArrayList<>(uniqueWords);
    }

    public List<String> getMostFrequentWords(int limit) {
        Map<String, Integer> wordOccurrences = countWordOccurrences();

        return wordOccurrences.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(limit)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
