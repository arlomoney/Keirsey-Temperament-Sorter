// Arlene Shergill
// January 19th, 2024
// AP CSA
// Mr. Thompson
// Project #6 - Personality Project

import java.io.*;
import java.util.*;

public class Personality {
	
	public static int DIMENSION = 4;
	
	public static void main(String[] args) throws FileNotFoundException {
		
		intro();
		
		Scanner scan = new Scanner(System.in); //gets the name of the input/output files
		
		System.out.print("input file name? ");
		Scanner inputFile = new Scanner(new File(scan.next()));
		
		System.out.print("output file name? ");
		String outputFileName = scan.nextLine();
		PrintStream outputFile = new PrintStream(new File(scan.next()));
		
		personInfo(inputFile, outputFile); //reports the scores and the personality type at the end
		
	}
	
	public static void intro() { //the intro to the program
		
		System.out.println("This program processes a file of answers to the");
		System.out.println("Keirsey Temperament Sorter.  It converts the");
		System.out.println("various A and B answers for each person into");
	    System.out.println("a sequence of B-percentages and then into a");
		System.out.println("four-letter personality type.");
		System.out.println();
	}
	
   //method that reports the final score and the personality type in the output file using the data from the input file
   //precond: the input file name and the output file name
   //postcond: prints the name, scores, and the personality type to the output file
	public static void personInfo(Scanner inputFile, PrintStream outputFile) {
		
		while (inputFile.hasNextLine()) { //loops through all the lines in th efile
			
			String name = inputFile.nextLine();
			String data = (inputFile.nextLine()).toLowerCase();
			
			char[] Data = data.toCharArray(); //turned it into a character array so it is easier to sort/loop through
			int scores[] = calculateScores(categories(Data));
			
			outputFile.println(name + ": " + Arrays.toString(scores) + " = " + calculateType(scores)); //prints what we see in the output file
			
		}
	}
   //seperates the array into its appropriate categories
   //precond: char array of the data
   //postcond: returns an array of data which was sorted by a and b
	public static int[][] categories(char[] data) {
		int[][] counter = new int[2][DIMENSION];
		
		for (int i = 0; i < data.length; i++) {
			
			if (data[i] == 'a') {
				counter[0][(i % 7 + 1) / 2] += 1; //enters the data in the first dimension of the array
				
			} else if (data[i] == 'b') {
				counter[1][(i % 7 + 1) / 2] += 1; //enters the data into the second dimension of the array
			}
		}
		return counter;
	}
	
   //method that calculates the b percentage of each category
   //precond: 2 dimension array of values
   //postcond: the percentages of each category
	public static int[] calculateScores(int counter[][]) {
		
		int[] scores = new int[DIMENSION];
		
		for (int i = 0; i < DIMENSION; i++) {
			scores[i] = (int) Math.round( ((double)counter[1][i]) / (counter[0][i] + counter[1][i]) * 100);
		}
		
		return scores;
	}
	
   //assigns the personality type
   //precond: array of percentages of categories
   //postcond: the personality type
	public static String calculateType(int[] scores) {
		
		String[][] dimensions ={{"E", "S", "T", "J"}, {"I", "N", "F", "P"}}; //two indexes, seperates the oppsite types
		String type = "";
		
		for (int i = 0; i < DIMENSION; i++) {
			
			if (scores[i] > 50) { //assigns the type based off if b was over or under 50%
				type += dimensions[1][i];
				
			} else if (scores[i] < 50) { 
				type += dimensions[0][i];
				
			} else { //returns X if the percentage is 50
				type += "X";
			}
		}
		
		return type;
	}
}
