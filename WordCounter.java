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
    /**
     * return the current word count for this word
     * @param word string entered by user
     * @return   the current word count for this word (0 if empty)
     */
    public int getWordCount(String word) {
        int hashCode = word.hashCode();
        hashCode = hashCode % capacity;  // asserts: hashCode < capacity
        if (hashCode < 0)
            hashCode += capacity;       // asserts: hashCode > 0
        return hashTable[hashCode].count;
    }
    public void removeWord(String word) {
        int hashCode = word.hashCode();
        hashCode = hashCode % capacity;  // asserts: hashCode < capacity
        if (hashCode < 0)
            hashCode += capacity;       // asserts: hashCode > 0
//        hashTable[hashCode];
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
