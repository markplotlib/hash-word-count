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
        return uniqueWordCount;
    }

    public int getTotalWordCount() {
        return totalWordCount;
    }

    public boolean isEmpty() {
        return totalWordCount == 0;
    }
    
    /**
     * Creates a new bucket for new (unique) words, 
     * otherwise increases word count if it's already in hash table.
     * @param word  label of bucket
     * @return  the updated word count for this word
     */
    public int incrementWordCount(String word) {
        int hashCode = word.hashCode();
        hashCode = hashCode % capacity;  // asserts: hashCode < capacity
        if (hashCode < 0)
            hashCode += capacity;       // asserts: hashCode > 0
        if (hashTable[hashCode] != null) {
            // if the word IS found in hash table
            hashTable[hashCode].count++;        	
        } else {
            // if NOT found, then create new bucket for this word.
            Bucket bucket = new Bucket(word);
            hashTable[hashCode] = bucket;        	
        }
        return hashTable[hashCode].count;
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
