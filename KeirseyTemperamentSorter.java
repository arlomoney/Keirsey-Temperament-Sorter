import java.io.*;
import java.util.*;

public class KeirseyTemperamentSorterQuiz {
	
	public static int DIMENSION = 4;
	
	public static void main(String[] args) throws FileNotFoundException {
		
		intro();
		
		Scanner scan = new Scanner(System.in);
		
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
	
	public static void personInfo(Scanner inputFile, PrintStream outputFile) {
		
		while (inputFile.hasNextLine()) {
			
			String name = inputFile.nextLine();
			String data = (inputFile.nextLine()).toLowerCase();
			
			char[] Data = data.toCharArray();
			int scores[] = calculateScores(categories(Data));
			
			outputFile.println(name + ": " + Arrays.toString(scores) + " = " + calculateType(scores)); 
			
		}
	}

	public static int[][] categories(char[] data) {
		int[][] counter = new int[2][DIMENSION];
		
		for (int i = 0; i < data.length; i++) {
			
			if (data[i] == 'a') {
				counter[0][(i % 7 + 1) / 2] += 1; 
				
			} else if (data[i] == 'b') {
				counter[1][(i % 7 + 1) / 2] += 1;
			}
		}
		return counter;
	}
	
	public static int[] calculateScores(int counter[][]) {
		
		int[] scores = new int[DIMENSION];
		
		for (int i = 0; i < DIMENSION; i++) {
			scores[i] = (int) Math.round( ((double)counter[1][i]) / (counter[0][i] + counter[1][i]) * 100);
		}
		
		return scores;
	}
	
	public static String calculateType(int[] scores) {
		
		String[][] dimensions ={{"E", "S", "T", "J"}, {"I", "N", "F", "P"}}; 
		String type = "";
		
		for (int i = 0; i < DIMENSION; i++) {
			
			if (scores[i] > 50) {
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
