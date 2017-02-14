// Ric Rodriguez
// CMPS 101-01 pa1
// rirrodri@ucsc.edu
// Lex.java

// ******************************************************************************************************************
// The main program for this project will be called Lex.java. Your List ADT module will be contained in a file
// called List.java, and will export its services to the client module Lex.java. Each file will define one top level
// class, List and Lex respectively. The required List operations are specified in detail below. Lex.java will take
// two command line arguments giving the names of an input file and an output file. 
// ******************************************************************************************************************

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Lex {
	
	public static void main(String[] args) throws FileNotFoundException{
		
		// Pre: 2 specified input files
		if(!checkArgs(args)){
			System.err.println("Usage: Lex [inFile] [outFile]");
			System.exit(1);
		}
		
		// Create scanner for input file
		File inFile = new File(args[0]);
		Scanner input = new Scanner(inFile);
		
		
		// Count lines in input file
		int lines=0;
		while(input.hasNextLine()){
			lines++;
			input.nextLine();
		}
		
		// Enter the lines into a String array
		String[] inputLines = new String[lines];
		input.close();
		input = new Scanner(inFile);
		int lino = 0;
		while(input.hasNextLine()){
			inputLines[lino++] = input.nextLine();
		}
		
		// Initialize List
		List inputList = new List();
		inputList.append(0);

		// Insertion Sort algorithm for sorting indices
		for(int j =1; j <inputLines.length; j++){
			String prime = inputLines[j];
			int i = j-1;
			inputList.moveBack();			
				
			while(i>=0 && prime.compareTo(inputLines[inputList.get()]) <= 0){
				i--;
				inputList.movePrev();
			}
			if(inputList.index()>=0){
				inputList.insertAfter(j);
			}else{
				inputList.prepend(j);
			}
		}
		
		// Output sorted list to file
		PrintWriter printer = new PrintWriter(args[1]);
		for(inputList.moveFront(); inputList.index()>=0; inputList.moveNext()){
			printer.println(inputLines[inputList.get()]);
		}
		printer.close();
		input.close();
	}
	
	// Checks for appropriate input files
	// Pre: args.length == 2 
	public static boolean checkArgs(String[] args){
		return args.length==2;
	}
}
