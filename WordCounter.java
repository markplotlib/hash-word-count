/*
 * CPSC 5003, Seattle University
 * This is free and unencumbered software released into the public domain.
 */
package mchesney_p3;

public class WordCounter {

	// experimenting
	private Bucket[] hashTable;
    private int capacity;
	private int uniqueWordCount;
	private int totalWordCount;

    public WordCounter(int capacity) {
        this.capacity = capacity;
        hashTable = new Bucket[capacity];
        uniqueWordCount = 0;
        totalWordCount = 0;
    }

    public int getCapacity() {
        return capacity;
    }
    public int getUniqueWordCount() {
        return 0; // FIXME
    }
    public int getTotalWordCount() {
        return 0; // FIXME
    }
    public boolean isEmpty() {
        return totalWordCount == 0;
    }
    public int incrementWordCount(String word) {
        int hashCode = word.hashCode();

// if
        hashTable[hashCode] = new Bucket(word);
// else
        hashTable[hashCode].count++;
        return hashTable[hashCode].count;
        // FIXME - return the updated word count for this word
    }
    public int getWordCount(String word) {
        int hashCode = word.hashCode();
        if (hashTable.contains(key)) {
            // When the word already exists in the hash table, increment the word count.

        } else {
            // Adds a word to the hash table if it was not previously seen (initial count of 1)

        }

        	// Returns word count (will always be greater than or equal to 1)

        hashTable[hashCode];

        return 0; // FIXME - return the current word count for this word
    }
    public void removeWord(String word) {
        int hashCode = word.hashCode();
        // FIXME - remove word from hash table
    }

    private static class Bucket {
        public String word;
        public int count;
        public Bucket next;

        public Bucket(String word) {
            this.word = word;
            this.count = 1;
            this.next = null;
        }
    }
}
