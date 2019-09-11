/*
 * CPSC 5003, Seattle University
 * This is free and unencumbered software released into the public domain.
 * @author Mark Chesney
 * @collaborators: J Limfueco, Y Wang, P Loyd, R Wang, and S Kim.
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
        int key = mapCodeToIndex(hashCode);

        totalWordCount++;   // This is incremented unconditionally

        // if hash table bucket is null,
        // then definitively that word is NOT present.
        // so then CREATE BRAND NEW BUCKET for this word.
        if (hashTable[key] == null) {
            hashTable[key] = new Bucket(word);
            uniqueWordCount++;  // increase count for a new word bucket
            return hashTable[key].count;

        } else {
            // if hash table bucket is NOT null,
            // then possibility #1: that this word can be found within.
            // traverse the chain in search of the word
            // whereby we increment total word count AND that word's count.
            // Does not insert new nodes when word already exists in HashTable.
            while (hashTable[key] != null) {
                if (hashTable[key].word.equals(word)) {  // word IS found!
                    return hashTable[key].count++;
                }
                hashTable[key] = hashTable[key].next;  // examine next bucket
            }

            // otherwise, possibility #2:
            // it's a new word whose hash code index is populated,
            // aka collision occurrence.
            // chaining / open hashing technique to resolve collisions.
            Bucket newBucket = new Bucket(word);
            // (Bucket inserted to the front of the linked list)
            newBucket.next = hashTable[key];
            hashTable[key] = newBucket;
            uniqueWordCount++;  // increase count for a new word bucket

            return hashTable[key].count;
        }
    }

    /**
     * Gets count field of word's bucket
     * @param word  unique string that delineates this bucket
     * @return  count field of word's bucket
     * 			0 when the word entry does not exist in the HashTable.
     */
    public int getWordCount(String word) {
        int hashCode = word.hashCode();
        int key = mapCodeToIndex(hashCode);

        // if hash table bucket is null,
        // then definitively that word is NOT found in hash table.
        // so return 0
        if (hashTable[key] == null) {
            return 0;

        // if hash table bucket is NOT null,
        // then traverse the chain in search of the word

        } else {
            Bucket bucket = hashTable[key];
            while (bucket != null) {
                if (bucket.word.equals(word))   // word IS found!
                    return bucket.count;
                bucket = bucket.next;
            }
            return 0; // when the word does not exist in the HashTable.
        }
    }

    /**
     * completely remove word entry from the hash table.
     * @param word  target word
     */
    public void removeWord(String word) {
        int hashCode = word.hashCode();
        int key = mapCodeToIndex(hashCode);

        // if hash table bucket is null, then that word is NOT present.
        // take no action.
        if (hashTable[key] == null)
            return;

        // if hash table bucket is NOT null,
        // then traverse the chain in search of the word
        Bucket bucket = hashTable[key];
        Bucket prev = bucket;

        while (bucket != null) {

            if (bucket.word.equals(word)) {  // word IS found!
                // decrement total word count AND unique word count
                uniqueWordCount--;
                totalWordCount -= bucket.count;
                // delete the bucket
                prev.next = bucket.next;
                bucket.next = null;
                return;
            }
            // advance bucket
            prev = bucket;
            bucket = bucket.next;
        }
    }

    private int mapCodeToIndex(int code) {
        code = code % capacity;  // asserts: code < capacity
        if (code < 0)
            code += capacity;       // asserts: code > 0
        return code;
    }

    /**
     * Bucket for word counting, one for each unique word.
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
