// Queens.java
// Ric Rodriguez
// rirrodri@ucsc.edu
// pa5
// Find solutions to n-Queens problem

class Queens {
	public static void main(String[] args) {
		if (args.length > 2) {
			printUsage();
		}
		int queens = checkArguments(args);
		listQueens(queens, args);
	}
	static void printUsage() {
		System.out.println("Usage: Queens [-v] number\nOption: -v verbose output, print all solutions");
		System.exit(1);
	}
	static int checkArguments(String[] args) {
		int queens = 0;
		if (args.length == 1) {
			try {
				queens = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				printUsage();
			}
		} else if (args.length == 2) {
			if (checkV(args[0])) {
				try {
					queens = Integer.parseInt(args[1]);
				} catch (NumberFormatException e) {
					printUsage();
				}
			} else printUsage();

		} else if (args.length==0){
			printUsage();
		}
		return queens;
	}
	static boolean checkV(String args) {
		if (args.equals("-v")) {
			return true;
		} else {
			return false;
		}
	}
	static long factorial(int x) {
		long total;
		if (x < 0) {
			System.out.println("number must be non-negative integer");
			printUsage();
		}
		if (x == 0 || x == 1) return total = 1;
		total = x * factorial(x - 1);
		return total;
	}
	static void reverseArray(int[] A, int i, int j) {
		while (i < j) {
			swapArray(A, i, j);
			i++;
			j--;
		}
	}
	static void swapArray(int[] A, int i, int j) {
		int hold;
		hold = A[i];
		A[i] = A[j];
		A[j] = hold;
	}
	static boolean isSolution(int[] A) {
		int i, j;
		int n = A.length;
		for (i = 1; i <= n - 1; i++) {
			for (j = i + 1; j <= n - 1; j++) {
				if (j - i == Math.abs(A[i] - A[j])) {
					return false;
				}
			}
		}
		return true;
	}
	static void nextPermutation(int[] A) {
		int i, j, k, pivotPoint = 0, successor = 0;
		boolean isPivot = false;
		for (i = A.length - 2; i > 0; i--) {
			if (A[i] < A[i + 1]) {
				pivotPoint = i;
				isPivot = true;
				break;
			}
		}
		if (isPivot == false) {
			reverseArray(A, 1, A.length - 1);
			return;
		}
		for (k = A.length - 1; k > 0; k--) {
			if (A[k] > A[i]) {
				successor = k;
				break;
			}
		}
		swapArray(A, pivotPoint, successor);
		reverseArray(A, pivotPoint + 1, A.length - 1);
		return;
	}
	static void listQueens(int queens, String[] args) {
		long numQueens = 0;
		long j;
		int k;
		long n = factorial(queens);
		int[] A = new int[queens + 1];
		for (int i = 0; i < queens + 1; ++i) {
			A[i] = i;
		}
		if (checkV(args[0])) {
			for (j = 0; j < n; j++) {
				nextPermutation(A);
				if (isSolution(A)) {
					numQueens++;
					System.out.print("(");
					for (k = 1; k < A.length - 1; k++) {
						System.out.print(A[k] + ", ");
					}
					if (k == A.length - 1) {
						System.out.print(A[k]);
					}
					System.out.println(")");
				}
			}
		} else {
			for (j = 0; j < n; j++) {
				nextPermutation(A);
				if (isSolution(A)) {
					numQueens++;
				}
			}
			System.out.println(queens + "-Queens has " + numQueens + " solutions.");
		}
	}
}