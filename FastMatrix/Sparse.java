//Ric Rodriguez
//CMPS 101-pa3
//rirrodri@ucsc.edu
//Sparse.java

//************************************************************************************************************************
// The top level client module for this project will be called Sparse.java. It will take two command line
// arguments giving the names of the input and output files, respectively. The input file will begin with a
// single line containing three integers n, a, and b, separated by spaces. The second line will be blank, and the
// following a lines will specify the non-zero entries of an n x n
// matrix A. Each of these lines will contain a
// space separated list of three numbers: two integers and a double, giving the row, column, and value of the
// corresponding matrix entry. After another blank line, will follow b lines specifying the non-zero entries of
// an n x n matrix B. 
//************************************************************************************************************************

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintWriter;

public class Sparse {

	public static void main(String[] args) throws FileNotFoundException {
		
		// make sure we got 2 args
		checkArgs(args);
		Scanner sc = new Scanner(new File(args[0]));

		// store first 3 int inputs, n, a, b
		int[] nab = new int[3];
		for (int i = 0; i < 3; i++) {
			nab[i] = sc.nextInt();
		}
		Matrix A = new Matrix(nab[0]);
		for (int i = 0; i < nab[1]; i++) {
			int row = sc.nextInt();
			int col = sc.nextInt();
			double value = sc.nextDouble();
			A.changeEntry(row, col, value);
		}
		Matrix B = new Matrix(nab[0]);
		for (int i = 0; i < nab[2]; i++) {
			int row = sc.nextInt();
			int col = sc.nextInt();
			double value = sc.nextDouble();
			B.changeEntry(row, col, value);
		}

		PrintWriter writer = new PrintWriter(args[1]);

		writer.println("A has " + A.getNNZ() + " non-zero entries:");
		writer.println(A);
		writer.println();

		writer.println("B has " + B.getNNZ() + " non-zero entries:");
		writer.println(B);
		writer.println();

		writer.println("(1.5)*A =");
		Matrix C = A.scalarMult(1.5);
		writer.println(C);
		writer.println();

		writer.println("A+B =");
		Matrix D = A.add(B);
		writer.println(D);
		writer.println();

		writer.println("A+A =");
		Matrix E = A.add(A);
		writer.println(E);
		writer.println();

		writer.println("B-A =");
		Matrix F = B.sub(A);
		writer.println(F);
		writer.println();

		writer.println("A-A =");
		Matrix G = A.sub(A);
		writer.println(G);
		writer.println();

		writer.println("Transpose(A) =");
		Matrix H = A.transpose();
		writer.println(H);
		writer.println();

		writer.println("A*B =");
		Matrix I = A.mult(B);
		writer.println(I);
		writer.println();

		writer.println("B*B =");
		Matrix J = B.mult(B);
		writer.println(J);
		writer.println();
		writer.close();

		sc.close();
	}

	static void checkArgs(String[] args) {
		if (args.length != 2) {
			System.out.println("Usage: Sparse [input.txt] [output.txt]");
			System.exit(1);
		}
	}
}
