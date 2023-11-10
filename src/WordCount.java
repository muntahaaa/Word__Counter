class WordCount {
    private String text;

    public WordCount(String para) {
        text = para;
    }

    public int countWords() {
        String[] words = text.split("\\s+");
        int wordCount = 0;

        // Iterate through the words and increment the count
        for (String word : words) {
            // Remove punctuation and special characters
            word = word.replaceAll("[^a-zA-Z]", "");

            if (!word.isEmpty()) { // Skip empty strings
                wordCount++;
            }
        }


        return wordCount;
    }
}