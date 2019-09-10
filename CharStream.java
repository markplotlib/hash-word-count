/*
 * Bootcamp 3 Solution
 * CPSC 5003, Seattle University
 * This is free and unencumbered software released into the public domain.
 */
package mchesney_p3;

import java.io.IOException;
import java.io.Reader;

/**
 * Uses a Reader to provide a stream of characters interface to the user.
 *
 * @author lundeenk
 * @version r19
 */
public class CharStream {
    public static final char EOF = (char) -1;   // End of File marker
    private final Reader reader;                // reader to get chars from
    private char curr;                          // current position's character
    private char next;                          // next char in reader stream

    /**
     * Constructor for CharQueue class.
     */
    public CharStream(Reader reader) {
        this.reader = reader;
        next();
        next();
    }

    /**
     * Wrap read in a try/catch and return EOF sentinel in case of exception.
     *
     * @return next character or EOF
     */
    private char read() {
        try {
            return (char) reader.read();
        } catch (IOException e) {
            return EOF;
        }
    }

    /**
     * Peek at the next character in the stream.
     *
     * @return upcoming character (may be EOF)
     */
    public char getPeek() {
        return this.next;
    }

    /**
     * Look at the current character in the stream.
     * (Won't change until the next() method is called.)
     *
     * @return last read character (may be EOF)
     */
    public char getChar() {
        return this.curr;
    }

    /**
     * Advance the current character in the character stream.
     */
    public void next() {
        this.curr = this.next;
        this.next = read();
    }
}
