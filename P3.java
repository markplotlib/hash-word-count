/*
 * Bootcamp 3 Solution
 * CPSC 5003, Seattle University
 * This is free and unencumbered software released into the public domain.
 */
package mchesney_p3;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/**
 * This is a Java class.
 *
 * @author lundeenk (adapted from kamenski r17)
 * @version r19
 */
public class P3 {
    private static final String MSG_HELP =
            "load <hash-table-size> <file-name>   \n" +
                    "           Creates a Hash Table with the given size, reads the words from \n" +
                    "           the file, and counts the occurrences to be analyzed later\n\n" +
                    "analyze text <text-entry>\n" +
                    "           Displays the entered words and shows their frequencies in each\n" +
                    "           passage. Words that are considered too common are skipped\n\n" +
                    "analyze file <file-name>\n" +
                    "           Displays the words loaded from file and shows their frequencies\n" +
                    "           in each passage. Words that are too common are skipped\n\n" +
                    "list       Displays the list of all loaded passages in this format:\n" +
                    "             ID   #Unique   #Total   Load F.   Passage Description  \n" +
                    "           +----+---------+--------+---------+---------------------+\n\n" +
                    "help       Displays this help menu.\n\n" +
                    "quit       Exits the program.";
    private static final String MSG_WELCOME =
            "\nWelcome to the Text Analysis program!\nEnter help to display " +
                    "supported commands.";
    private static final String MSG_BYE =
            "\nThanks for using the Text Analysis program!";
    /**
     * Scanner for the system.in used to read entry from the keyboard.
     */
    private static final Scanner console = new Scanner(System.in);

    /**
     * Entry point of the program
     *
     * @param args not used
     */
    public static void main(String[] args) {
        System.out.println(MSG_WELCOME);

        ArrayList<LoadedPassage> loadedPassages = new ArrayList<>();
        boolean keepAsking = true;
        while (keepAsking) {
            System.out.print("\nanalyzer> ");
            String line = console.nextLine();
            keepAsking = processLine(line, loadedPassages);
        }

        System.out.println(MSG_BYE);
    }

    /**
     * Process the line entered into the eval prompt.
     *
     * @param line           the line of text to process
     * @param loadedPassages the list of currently loaded passages in the
     *                       system
     * @return true if the prompt should ask for another command, false to exit
     */
    private static boolean processLine(String line,
                                       ArrayList<LoadedPassage>
                                               loadedPassages) {
        Scanner lineScanner = new Scanner(line); // Scanner to extract words
        String cmd = lineScanner.next();         // The first is user's command

        // A switch statement could be used on strings, but not all have JDK7
        if (cmd.equals("help")) {
            System.out.println(MSG_HELP);
        } else if (cmd.equals("quit")) {
            return false;
        } else if (cmd.equals("load")) {
            loadFileCmd(lineScanner, loadedPassages);
        } else if (cmd.equals("analyze")) {
            analyzeCmd(lineScanner, loadedPassages);
        } else if (cmd.equals("list")) {
            listCmd(loadedPassages);
        } else if (cmd.equals("debug")) {
            debugCmd(lineScanner, loadedPassages);
        } else {
            System.out.println("Error: unrecognized command: " + line);
        }
        return true;
    }

    /**
     * Loads the given file into the system. The words are processed and
     * counted. The results of the load can be displayed with the list command.
     *
     * @param lineScanner    unscanned characters from the user's input
     * @param loadedPassages currently loaded passages to modify
     */
    private static void loadFileCmd(Scanner lineScanner,
                                    ArrayList<LoadedPassage> loadedPassages) {
        if (!lineScanner.hasNextInt()) {
            System.out.println("Error: Hash Table size was not provided.");
            return;
        }
        final int tableCapacity = lineScanner.nextInt();

        if (!lineScanner.hasNextLine()) {
            System.out.println("Error: No file provided");
            return;
        }
        final String fname = lineScanner.nextLine().trim();

        try {
            Reader reader = new FileReader(fname);
            CharStream charStream = new CharStream(reader);
            WordScanner wordScanner = new WordScanner(charStream);
            WordCounter currCounter = new WordCounter(tableCapacity);
            while (wordScanner.hasNextWord()) {
                final String word = wordScanner.nextWord();
                currCounter.incrementWordCount(word);
            }
            reader.close();
            removeCommonWordsFrom(currCounter);
            loadedPassages.add(new LoadedPassage(fname, currCounter));
            System.out.println("Loaded " + fname);
        } catch (FileNotFoundException e) {
            System.out.println("Error: file not found.");
        } catch (IOException e) {
            System.out.println("Error: file read error.");
        }
    }

    /**
     * Lists the currently loaded passages and their statistics
     *
     * @param loadedPassages the currently loaded passages
     */
    private static void listCmd(ArrayList<LoadedPassage> loadedPassages) {
        if (loadedPassages.size() == 0) {
            System.out.println("There are no loaded passages.");
            return;
        }
        System.out.println(
                "  ID   #Unique   #Total   Load F.   Passage Description  \n" +
                        "+----+---------+--------+---------+---------------------+");
        for (int i = 0; i < loadedPassages.size(); i++) {
            final LoadedPassage p = loadedPassages.get(i);
            System.out.printf("%4d", i + 1);
            System.out.printf("%10d", p.wordCounter.getUniqueWordCount());
            System.out.printf("%9d", p.wordCounter.getTotalWordCount());
            final double lf = (double) p.wordCounter.getUniqueWordCount() /
                    (double) p.wordCounter.getCapacity();
            System.out.printf("%9.3f", lf);
            System.out.println("    " + p.passageTitle);
        }
    }

    /**
     * Analyzes the given text or file contents
     *
     * @param lineScanner unscanned characters from the user's input
     * @param passages    currently loaded passages
     */
    private static void analyzeCmd(Scanner lineScanner,
                                   ArrayList<LoadedPassage> passages) {
        if (passages.size() == 0) {
            System.out.println(
                    "Error: must have at least one passage loaded to analyze");
            return;
        }
        if (!lineScanner.hasNext()) {
            System.out.println("Error: invalid command");
            return;
        }
        String typeAnalysis = lineScanner.next();
        if (!lineScanner.hasNextLine()) {
            System.out.println("Error: nothing was provided to analyze");
            return;
        }

        if (typeAnalysis.equals("text")) {
            final String text = lineScanner.nextLine().trim();
            Reader reader = new StringReader(text);
            analyzeText(reader, passages);
        } else if (typeAnalysis.equals("file")) {
            final String fname = lineScanner.nextLine().trim();
            analyzeFile(fname, passages);
        } else {
            System.out.println("Error: unknown analysis type.");
        }
    }

    /**
     * Analyzes the words that are read from the file instead of user entry
     *
     * @param fname    file name to read words from
     * @param passages currently loaded passages
     */
    private static void analyzeFile(String fname,
                                    ArrayList<LoadedPassage> passages) {
        try {
            Reader reader = new FileReader(fname);
            analyzeText(reader, passages);
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: file not found.");
        } catch (IOException e) {
            System.out.println("Error: unable to close file.");
        }
    }

    /**
     * Analyzes the given text against the loaded passages
     *
     * @param reader   character stream to analyze
     * @param passages loaded passages to analyze against
     */
    private static void analyzeText(Reader reader,
                                    ArrayList<LoadedPassage> passages) {
        CharStream charStream = new CharStream(reader);
        WordScanner wordScanner = new WordScanner(charStream);
        ArrayList<String> wordBag = new ArrayList<>();
        HashSet<String> duplicates = new HashSet<String>();
        while (wordScanner.hasNextWord()) {
            final String word = wordScanner.nextWord();
            // only analyze if this is not a common word and not yet seen
            if (!CommonWordList.contains(word) && !duplicates.contains(word)) {
                duplicates.add(word);
                wordBag.add(word);
            }
        }
        if (wordBag.size() == 0) {
            System.out.println("Error: all words provided were too common");
            return;
        }

        ArrayList<Integer> titleLengths = new ArrayList<>();
        titleLengths.add(14);
        System.out.printf("%16s", "given word");
        for (LoadedPassage passageEntry : passages) {
            titleLengths.add(passageEntry.passageTitle.length());
            System.out.print("   " + passageEntry.passageTitle);
        }
        System.out.println();

        for (int i : titleLengths) {
            String dashes = new String(new char[i + 2]).replace("\0", "-");
            System.out.print("+" + dashes);
        }
        System.out.println("+");

        for (String word : wordBag) {
            System.out.printf("%16s", word);
            for (LoadedPassage pe : passages) {
                final int width = pe.passageTitle.length() + 3;
                final int cnt = pe.wordCounter.getWordCount(word);
                System.out.printf("%" + width + "d", cnt);
            }
            System.out.println();
        }
    }

    /**
     * Iterates through all the known common words and removes them from the
     * given counter.
     *
     * @param wordCounter WordCounter to remove the common words from.
     */
    private static void removeCommonWordsFrom(WordCounter wordCounter) {
        for (String word : CommonWordList.getWords()) {
            wordCounter.removeWord(word);
        }
    }

    /**
     * Displays the contents of the HashTable
     *
     * @param lineScanner    unscanned characters from the user's input
     * @param loadedPassages currently loaded passage entries
     */
    private static void debugCmd(Scanner lineScanner,
                                 ArrayList<LoadedPassage> loadedPassages) {
        if (lineScanner.hasNextInt()) {
            final int id = lineScanner.nextInt() - 1;
            if (0 <= id && id < loadedPassages.size()) {

                final LoadedPassage pe = loadedPassages.get(id);
                System.out.println(pe.passageTitle);
                final int len = pe.passageTitle.length();
                System.out.println(new String(new char[len]).replace('\0',
                        '-'
                ));
                System.out.println(pe.wordCounter.toString());
            } else {
                System.out.println("Error: invalid passage ID");
            }
        } else {
            System.out.println("Error: please provide passage ID");
        }

    }

    /**
     * Abstraction for the loaded passage entry.
     */
    private static class LoadedPassage {
        private final String passageTitle;      // title of the loaded passage
        private final WordCounter wordCounter;  // counter of the words in it

        /**
         * Constructs a loaded passage entry.
         *
         * @param passageTitle title of the loaded passage
         * @param wordCounter  counter of the words in the passage
         */
        private LoadedPassage(String passageTitle, WordCounter wordCounter) {
            this.passageTitle = passageTitle;
            this.wordCounter = wordCounter;
        }

    }
}
