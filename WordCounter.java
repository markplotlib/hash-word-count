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

        // if hash table index is null,
        // then definitively that word is NOT found.
        // so then create new bucket for this word.
        if (hashTable[hashCode] == null) {
            hashTable[hashCode] = new Bucket(word);

        } else {

            // if hash table index is NOT null,
            // then: EITHER this is the same word, whereby we
            // increment total word count AND that word's count.
            // Does not insert new nodes when word already exists in HashTable.
            if (hashTable[hashCode].word.equals(word)) {
                hashTable[hashCode].count++;
                totalWordCount++;

            } else {
                // otherwise, it's a different word with the same hash code,
                // and collision occurs.
                // chaining / open hashing technique to resolve collisions.
                Bucket newBucket = new Bucket(word);
                // (Bucket inserted to the front of the linked list)
                newBucket.next = hashTable[hashCode];
                hashTable[hashCode] = newBucket;

            }
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
