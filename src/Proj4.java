import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

/***********************************************************************************************************************
 * @file: Proj4.java
 * @description: This program implements the main method of Project 4.
 * @author: Olivia Sturges
 * @date: December 1, 2024
 * Note on changing the data: Took out a comma from the titles in rows 69 and 111.
 **********************************************************************************************************************/

public class Proj4 {
    public static void main(String[] args) throws IOException {
        // Use command line arguments to specify the input file
        if (args.length != 2) {
            System.err.println("Usage: java TestAvl <input file> <number of lines>");
            System.exit(1);
        }

        String inputFileName = args[0];
        int numLines = Integer.parseInt(args[1]);

        // For file input
        FileInputStream inputFileNameStream = null;
        Scanner inputFileNameScanner = null;

        // Open the input file
        inputFileNameStream = new FileInputStream(inputFileName);
        inputFileNameScanner = new Scanner(inputFileNameStream);

        // ignore first line
        inputFileNameScanner.nextLine();

        // FINISH ME
        // Create ArrayList to store PARKSANDRECData
        ArrayList<PARKSANDRECData> parksList = new ArrayList<PARKSANDRECData>();

        // Read the file line by line
        for (int i = 0; i < numLines; i++) { //
            String line = inputFileNameScanner.nextLine();
            String[] parts = line.split(","); // split the string into multiple parts
            // Create a new PARKS&RECDATA object
            // season, episode_num_in_season, episode_num_overall, title, directed_by, written_by, original_air_date, us_viewers
            PARKSANDRECData data = new PARKSANDRECData(
                    Integer.parseInt(parts[0]), // Season
                    Integer.parseInt(parts[1]), // Episode number in season
                    Integer.parseInt(parts[2]), // Overall episode number
                    parts[3], // Episode
                    parts[4], // Directed By
                    parts[5], // Written By
                    parts[6], // Original Air Date
                    Double.parseDouble(parts[7]) // Number of US viewers
            );
            parksList.add(data); // add the data onto the ArrayList
        }
        inputFileNameStream.close();

        // Making sorted, shuffled, and reversed copies  of the ArrayList
        // sort based off of number of us_viewers
        ArrayList<PARKSANDRECData> sorted = new ArrayList<PARKSANDRECData>(parksList);
        Collections.sort(sorted);

        //shuffled
        ArrayList<PARKSANDRECData> rand = new ArrayList<PARKSANDRECData>(parksList);
        Collections.shuffle(rand);

        // reversed
        ArrayList<PARKSANDRECData> rev = new ArrayList<PARKSANDRECData>(parksList);
        Collections.sort(rev, Collections.reverseOrder());

        // Create the hash table
        SeparateChainingHashTable<PARKSANDRECData> H = new SeparateChainingHashTable<>( );

        // Initializing start time and end time
        long startTime = 0;
        long endTime = 0;

        System.out.print("N:" + numLines + ", ");

        // Sorted array operations
        // insert
        startTime = System.nanoTime();
        for (int i = 0; i < sorted.size(); i++) {
            H.insert(sorted.get(i));
        }
        endTime = System.nanoTime();
        double sortedInsertTime = (endTime - startTime) / 1e9;
        System.out.print("Sorted insertion time (sec): " + sortedInsertTime + ", ");

        // search
        startTime = System.nanoTime();
        for (int i = 0; i < sorted.size(); i++) {
            H.contains(sorted.get(i));
        }
        endTime = System.nanoTime();
        double sortedSearchTime = (endTime - startTime) / 1e9;
        System.out.print("Sorted search time (sec): " + sortedSearchTime + ", ");

        // delete
        startTime = System.nanoTime();
        for (int i = 0; i < sorted.size(); i++) {
            H.remove(sorted.get(i));
        }
        endTime = System.nanoTime();
        double sortedDeleteTime = (endTime - startTime) / 1e9;
        System.out.print("Sorted delete time (sec): " + sortedDeleteTime + ", ");

        // Shuffled array operations
        // insert
        startTime = System.nanoTime();
        for (int i = 0; i < rand.size(); i++) {
            H.insert(rand.get(i));
        }
        endTime = System.nanoTime();
        double shuffledInsertTime = (endTime - startTime) / 1e9;
        System.out.print("Shuffled insertion time (sec): " + shuffledInsertTime + ", ");

        // search
        startTime = System.nanoTime();
        for (int i = 0; i < rand.size(); i++) {
            H.contains(rand.get(i));
        }
        endTime = System.nanoTime();
        double shuffledSearchTime = (endTime - startTime) / 1e9;
        System.out.print("Shuffled search time (sec): " + shuffledSearchTime + ", ");

        // delete
        startTime = System.nanoTime();
        for (int i = 0; i < rand.size(); i++) {
            H.remove(rand.get(i));
        }
        endTime = System.nanoTime();
        double shuffledDeleteTime = (endTime - startTime) / 1e9;
        System.out.print("Shuffled delete time (sec): " + shuffledDeleteTime + ", ");

        // Reversed array operations
        // insert
        startTime = System.nanoTime();
        for (int i = 0; i < rev.size(); i++) {
            H.insert(rev.get(i));
        }
        endTime = System.nanoTime();
        double reversedInsertTime = (endTime - startTime) / 1e9;
        System.out.print("Reversed insertion time (sec): " + reversedInsertTime + ", ");

        // search
        startTime = System.nanoTime();
        for (int i = 0; i < rev.size(); i++) {
            H.contains(rev.get(i));
        }
        endTime = System.nanoTime();
        double reversedSearchTime = (endTime - startTime) / 1e9;
        System.out.print("Reversed search time (sec): " + reversedSearchTime + ", ");

        // delete
        startTime = System.nanoTime();
        for (int i = 0; i < rev.size(); i++) {
            H.remove(rev.get(i));
        }
        endTime = System.nanoTime();
        double reversedDeleteTime = (endTime - startTime) / 1e9;
        System.out.println("Reversed delete time (sec): " + reversedDeleteTime);

        // Make empty
        H.makeEmpty();

        // Writing to analysis.txt in CSV format
        // N,sortedInsertTime,sortedSearchTime,sortedDeleteTime,shuffledInsertTime,shuffledSearchTime,shuffledDeleteTime,reversedInsertTime,reversedSearchTime,reversedDeleteTime
        writeToFile(numLines + "," + sortedInsertTime + "," + sortedSearchTime + "," + sortedDeleteTime + "," + shuffledInsertTime + "," + shuffledSearchTime + "," + shuffledDeleteTime + "," + reversedInsertTime + "," + reversedSearchTime + "," + reversedDeleteTime, "analysis.txt");
    }

    // Method that generates analysis file
    public static void writeToFile(String content, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))){
            writer.write(content);
            writer.newLine();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
