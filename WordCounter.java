/*
 * CPSC 5003, Seattle University
 * This is free and unencumbered software released into the public domain.
 * @author mark chesney
 * @version 1.0
 */
package mchesney_p3;

public class WordCounter {

	private Bucket[] hashTable;
    private int capacity;
	private int uniqueWordCount;
	private int totalWordCount;

    /**
     * constructor
     * @param capacity of array, entered by user
     */
    public WordCounter(int capacity) {
        this.capacity = capacity;
        hashTable = new Bucket[capacity];
        uniqueWordCount = 0;
        totalWordCount = 0;
    }

    /**
     * returns capacity of array, entered by user
     * @return full capacity of hash table array
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * returns number of non-duplicated words
     * @return  number of non-duplicated words
     */
    public int getUniqueWordCount() {
        return uniqueWordCount;
    }

    /**
     * returns number of ALL words
     * @return  number of words, whether duplicates or not
     */
    public int getTotalWordCount() {
        return totalWordCount;
    }

    /**
     * indicates whether words exist in hash table
     * @return true if no word buckets have been added, false otherwise
     */
    public boolean isEmpty() {
        return totalWordCount == 0;
    }

    /**
     *
     * @param word
     * @return
     */
    public int incrementWordCount(String word) {
        int hashCode = word.hashCode();
        return 0; // FIXME - return the updated word count for this word
    }
    /**
     * Gets count field of word's bucket
     * @param word
     * @return  count field of word's bucket
     * 			0 when the word entry does not exist in the HashTable.
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
        // FIXME - remove word from hash table
    }

    /**
     *
     * @author mark chesney
     * @version 1.0
     */
    private static class Bucket {
        public String word;
        public int count;
        public Bucket next;

        /**
         * Bucket constructor, one for each word
         */
        public Bucket(String word) {
            this.word = word;
            this.count = 1;
            this.next = null;
        }
    }
}
