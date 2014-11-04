package com.doktuhparadox.liblib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, /* <- Won't happen*/ ParseException /* <- Could happen*/ {
        Scanner scanner = new Scanner(System.in); //The input scanner

        System.out.print("Enter the lib to read.\n> "); //Prompts the user for a file path

        while (true) { //This loop exists purely to get another file path if the given one does not work
            File f = new File(scanner.nextLine()); //The file object

            if (f.exists()) { //Make sure the file exists
                String[] rawLibWords = new BufferedReader(new FileReader(f)).lines().reduce((s, s2) -> s + "\n" + s2).get().split("\\s+"); //Stream all of the lines of the file into a string, then split it
                Map<String, String> previousWords = new HashMap<>(); //The map that stores the words inputted by the user for referencing

                for (int i = 0; i < rawLibWords.length; i++) {
                    String word = rawLibWords[i];

                    if (word.matches("%\\w*(\\-\\d)?%(\\p{Punct}+)?")) { //It's a placeholder asking for a replacement (%adjective%)
                        String sanitizedPlaceholder = word.replace("_", " ").replaceAll("[^\\p{Alpha}&&[^\\s]]", ""); //Remove all non-alphabetic characters except spaces from the string
                        System.out.printf("Enter a%s %s: ", "AEIOUaeiou".indexOf(sanitizedPlaceholder.charAt(0)) != -1 ? 'n' : "", sanitizedPlaceholder); //Prints the request for input, checking if it starts with a vowel
                        String newWord = word.replaceAll("%\\w*(\\-\\d)?%", scanner.next()); //Replaces the word but preserves punctuation
                        rawLibWords[i] = newWord; //Put it in the array
                        previousWords.put(cleanedForMapKey(word), newWord); //Record this in the map in case a reference is made later
                    } else if (word.matches("%\\$\\w*(\\-\\d)?%(\\p{Punct}+)?")) { //It's a placeholder asking for a word from the map (%$adjective%)
                        if (previousWords.containsKey(cleanedForMapKey(word))) {
                            rawLibWords[i] = previousWords.get(cleanedForMapKey(word)); //Get the word reference from the map if it exists
                        } else {
                            throw new ParseException("Unresolved reference: " + word, i); //Except if an unresolved reference exists
                        }
                    }
                }

                Arrays.asList(rawLibWords).forEach(s -> System.out.print(s.equals("$n") ? "\n" : s + " ")); //Print the new array of words
                break; //Break the loop
            } else {
                System.out.print("That file does not exist.\n> ");
            }
        }
    }

    private static String cleanedForMapKey(String s) {
        return s.replaceAll("[\\p{Punct}&&[^\\-]]", "");
    }
}
