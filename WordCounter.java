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
