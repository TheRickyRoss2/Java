//Ric Rodriguez
//CMPS 101-pa3
//rirrodri@ucsc.edu
//Matrix.java

//************************************************************************************************************************
// Matrix ADT Specifications
// In addition to the main program Sparse.java and the altered List.java from pa1, you will implement a Matrix
// ADT in a file called Matrix.java, which defines the Matrix class. This class will contain a private inner 
// class (similar to Node in your List ADT) that encapsulates the column and value information corresponding
// to a matrix entry. You may give this inner class any name you wish, but I will refer to it here as Entry.
// Thus Entry will have two fields that store types int and double respectively. Entry must also contain its 
// own equals() and toString() methods which override the corresponding methods in the Object superclass.
// Your Matrix class will represent a matrix as an array of Lists of Entry Objects. It is required that these Lists
// be maintained in column sorted order. Your Matrix ADT will export the following operations.
//************************************************************************************************************************

public class Matrix {
	
	// Fields
	List[] row;
	
	/**
	 * Private class Entry
	 * @author Ric
	 *
	 */
	private class Entry {
		// Fields
		int index;
		double value;
		
		/**
		 * Constructor for Entry
		 * @param index Column index for matrix
		 * @param value Double value for matrix
		 */
		Entry(int index, double value) {
			this.index = index;
			this.value = value;
		}
		
		public boolean equals(Object x) {
			if(x instanceof Entry){
				Entry E = (Entry) x;
				if(E.index!=this.index || E.value!=this.value)
					return false;
			}
			return true;
		}

		public String toString() {
			return "(" + this.index + ", " + this.value + ")";
		}
	}
	
	/**
	 * Constructor for class Matrix
	 * @param n The number of rows in matrix. Produces an n x n matrix
	 */
	public Matrix(int n) {
		
		// Prerequisite: n>=1
		if (n < 1) {
			throw new RuntimeException("Matrix must be greater than 1x1");
		}
		
		// Since indexing begins at 1
		this.row = new List[n + 1];
		for (int i = 1; i <= n; i++) {
			this.row[i] = new List();
		}
		
	}
	
	/**
	 * Calculates the size of matrix
	 * @return the number of rows and columns of this Matrix

	 */
	int getSize() {
		
		// check if matrix is initialized
		if (this.row.length == 0) {
			throw new RuntimeException("Uninitialized Matrix");
		}
		
		return this.row.length - 1;
	}

	/**
	 * Cacluates how many numbers are in matrix
	 * @return The number of non-zero entries in this Matrix
	 */
	int getNNZ() {
		
		// check if matrix is initialized
		if (this.row.length == 0) {
			throw new RuntimeException("Uninitialized Matrix");
		}
		
		int sum = 0;
		// iterate through rows, summing their lengths
		for (int i = 1; i < this.row.length; ++i) {
			sum += this.row[i].length();
		}
		return sum;
	}

	public boolean equals(Object x) {
		
		// make sure object is matrix
		if (x instanceof Matrix) {
			
			// satisfy compiler by casting to guarantee matrix class
			Matrix M = (Matrix) x;
			
			// early prediction
			if (this.row.length != M.row.length)
				return false;
			
			// iterate through rows
			for (int i = 1; i < this.row.length; i++) {
				
				// iterate through column lists
				for (this.row[i].moveFront(), M.row[i].moveFront(); this.row[i].index >= 0; this.row[i]
						.moveNext(), M.row[i].moveNext()) {
					if (!this.row[i].equals(M.row[i]))
						return false;
				}
			}
		}
		return true;
	}

	/**
	 * Sets matrix to zero by deleting associated lists
	 */
	void makeZero() {
		
		// check if matrix is initialized
		if (this.row.length == 0) {
			throw new RuntimeException("Uninitialized Matrix");
		}
		
		// delete lists from rows
		for (int i = 1; i < this.row.length; i++) {
			for (this.row[i].moveFront(); this.row[i].index >= 0; this.row[i].moveNext())
				this.row[i].clear();
		}
	}

	/**
	 * Copies matrix
	 * @return copy of matrix
	 */
	Matrix copy() {
		
		// check if matrix is initialized

		if (this.row.length == 0) {
			throw new RuntimeException("Uninitialized Matrix");
		}
		
		// allocate new matrix
		Matrix M = new Matrix(this.getSize());
		for (int i = 1; i < this.row.length; i++) {
			
			// only copy rows with lists
			if (this.row[i].front != null) {
				for (this.row[i].moveFront(); this.row[i].index >= 0; this.row[i].moveNext()) {
					
					// new entry
					Entry E = new Entry(((Entry) this.row[i].get()).index, ((Entry) this.row[i].get()).value);
					M.row[i].append(E);
				}
			}
		}
		return M;
	}

	/**
	 * Changes value of matrix[i][j]=x
	 * @param i row number
	 * @param j column number
	 * @param x new value
	 */
	void changeEntry(int i, int j, double x) {
		
		// Prerequisites: 1<=i<=getSize(), 1<=j<=getSize()
		if (i > this.getSize() || j > this.getSize() || j < 1 || i < 1) {
			throw new RuntimeException("Out of bounds");
		}
		
		// locate row and column
		boolean foundCol = false;
		
		for (this.row[i].moveFront(); this.row[i].index >= 0; this.row[i].moveNext()) {
			
			if (((Entry) this.row[i].get()).index == j) {
				foundCol = true;
			}
			if (foundCol) {
				
				// replace with new val if not 0
				if (x == 0) {
					this.row[i].delete();
				} else {
					((Entry) this.row[i].get()).value = x;
				}
			}
		}
		
		// allocate new place on list for x
		if (x != 0 && !foundCol) {
			
			Entry E = new Entry(j, x);
			
			if (this.row[i].length() == 0) {
				this.row[i].append(E);
				return;
			}
			
			// move cursor to proper index
			for (this.row[i].moveFront(); this.row[i].index >= 0 && ((Entry) this.row[i].get()).index < j; this.row[i]
					.moveNext());
			
			// if fell off list append
			if (this.row[i].index == -1) {
				this.row[i].append(E);
				return;
			} else {
				this.row[i].insertBefore(E);
				return;
			}
		}
	}

	/**
	 * Multiply matrix by x
	 * @param x value to multiply by
	 * @return new matrix * x
	 */
	Matrix scalarMult(double x) {

		// check if matrix is initialized
		if (this.row.length == 0) {
			throw new RuntimeException("Uninitialized Matrix");
		}
		
		// allocate new matrix
		Matrix M = new Matrix(this.getSize());
		
		// iterate through each row
		for (int i = 1; i < this.row.length; i++) {
			
			// multiply each entry by x
			for (this.row[i].moveFront(); this.row[i].index >= 0; this.row[i].moveNext()) {
				M.changeEntry(i, ((Entry) this.row[i].get()).index, ((Entry) this.row[i].get()).value * x);
			}
		}

		return M;
	}

	/**
	 * Adds matrices A+B
	 * @param M second matrix
	 * @return A+B
	 */
	Matrix add(Matrix M) {
		
		// check if matrix is initialized
		if (this.row.length == 0 || M.row.length == 0) {
			throw new RuntimeException("Uninitialized Matrix");
		}
		
		// Prerequisites: matrices must be square
		if (this.getSize() != M.getSize()) {
			throw new RuntimeException("Matrices must be of equal size");
		}
		
		// special case to prevent double list advancing
		if (M == this) {
			return this.copy().scalarMult(2);
		}
		
		// allocate new matrix
		Matrix sum = new Matrix(this.getSize());
		
		// iterate through rows
		for (int i = 1; i <= this.getSize(); ++i) {
			
			// list to hold added values
			List sumList = new List();
			
			// iterate though both lists entirely, even if one falls off
			for (this.row[i].moveFront(), M.row[i].moveFront(); this.row[i].index >= 0 || M.row[i].index >= 0;) {
				
				// case both lists are active
				if (this.row[i].index >= 0 && M.row[i].index >= 0) {
					
					// if column of A is greater than B, advance B
					if (((Entry) this.row[i].get()).index > ((Entry) M.row[i].get()).index) {
						sumList.append(new Entry(((Entry) M.row[i].get()).index, ((Entry) M.row[i].get()).value));
						M.row[i].moveNext();
					} 
					
					// if column of B is greater than A, advance A
					else if (((Entry) this.row[i].get()).index < ((Entry) M.row[i].get()).index) {
						sumList.append(new Entry(((Entry) this.row[i].get()).index, ((Entry) this.row[i].get()).value));
						this.row[i].moveNext();
					} 
					
					// if the columns match, check if their sum is 0 then append to list
					else if (((Entry) this.row[i].get()).index == ((Entry) M.row[i].get()).index) {
						if (((Entry) this.row[i].get()).value + ((Entry) M.row[i].get()).value == 0) {
							M.row[i].moveNext();
							this.row[i].moveNext();
							continue;
						} else {
							sumList.append(new Entry(((Entry) this.row[i].get()).index,
									((Entry) this.row[i].get()).value + ((Entry) M.row[i].get()).value));
						}
						M.row[i].moveNext();
						this.row[i].moveNext();
					}
				} 
				
				// if lists fall off, simply append whatever is left
				else if (this.row[i].index >= 0) {
					sumList.append(new Entry(((Entry) this.row[i].get()).index, ((Entry) this.row[i].get()).value));
					this.row[i].moveNext();
				} else if (M.row[i].index >= 0) {
					sumList.append(new Entry(((Entry) M.row[i].get()).index, ((Entry) M.row[i].get()).value));
					M.row[i].moveNext();
				}
			}
			
			// set new matrix row to list
			sum.row[i] = sumList;
		}
		return sum;
	}

	/**
	 * Subtracts A-B
	 * @param M right matrix
	 * @return A-B
	 */
	Matrix sub(Matrix M) {
		
		// check if matrix is initialized
		if (this.row.length == 0 || M.row.length == 0) {
			throw new RuntimeException("Uninitialized Matrix");
		}
		
		// Prerequisites: matrices must be square
		if (this.getSize() != M.getSize()) {
			throw new RuntimeException("Matrices must be of equal size");
		}
		
		// special case where matrix is subtracted from itself
		if (M == this)
			return this.copy().scalarMult(0);
		
		// new matrix
		Matrix sum = new Matrix(this.getSize());
		
		// iterate through rows
		for (int i = 1; i <= this.getSize(); ++i) {
			
			// same as add
			List sumList = new List();
			for (this.row[i].moveFront(), M.row[i].moveFront(); this.row[i].index >= 0 || M.row[i].index >= 0;) {
				
				if (this.row[i].index >= 0 && M.row[i].index >= 0) {
				
					if (((Entry) this.row[i].get()).index > ((Entry) M.row[i].get()).index) {
					
						// since subtracting, right arg is negative
						sumList.append(new Entry(((Entry) M.row[i].get()).index, ((Entry) M.row[i].get()).value * -1));
						M.row[i].moveNext();
					} else if (((Entry) this.row[i].get()).index < ((Entry) M.row[i].get()).index) {
						sumList.append(new Entry(((Entry) this.row[i].get()).index, ((Entry) this.row[i].get()).value));
						this.row[i].moveNext();
					} else if (((Entry) this.row[i].get()).index == ((Entry) M.row[i].get()).index) {
					
						if (((Entry) this.row[i].get()).value - ((Entry) M.row[i].get()).value == 0) {
							M.row[i].moveNext();
							this.row[i].moveNext();
							continue;
						} else {
							
							sumList.append(new Entry(((Entry) this.row[i].get()).index,
									((Entry) this.row[i].get()).value - ((Entry) M.row[i].get()).value));
							
						}
						
						M.row[i].moveNext();
						this.row[i].moveNext();
					}
				} else if (this.row[i].index >= 0) {
					
					sumList.append(new Entry(((Entry) this.row[i].get()).index, ((Entry) this.row[i].get()).value));
					this.row[i].moveNext();
					
				} else if (M.row[i].index >= 0) {
					
					// since subtracting, right arg is negative
					sumList.append(new Entry(((Entry) M.row[i].get()).index, ((Entry) M.row[i].get()).value * -1));
					M.row[i].moveNext();
				}
			}
			sum.row[i] = sumList;
		}
		return sum;
	}

	/**
	 * swaps rows and columns
	 * @return A^T
	 */
	Matrix transpose() {
		
		// new matrix alloc
		Matrix M = new Matrix(this.getSize());
		
		for (int i = 1; i <=this.getSize(); i++) {
			for (this.row[i].moveFront(); this.row[i].index >= 0; this.row[i].moveNext()) {
				// swap col and row
				M.changeEntry(((Entry) this.row[i].get()).index, i, ((Entry) this.row[i].get()).value);
			}
		}
		return M;
	}

	/**
	 * calculates A*B
	 * @param M right matrix
	 * @return A*B
	 */
	Matrix mult(Matrix M) {
		
		// must be square matrices
		if (this.row.length != M.row.length) {
			throw new RuntimeException("Matrices of unequal size");
		}

		// new matrix alloc
		Matrix result = new Matrix(this.getSize());

		// transpose to calculate dot product
		Matrix transpose = M.transpose();
		
		for (int i = 1; i <= this.getSize(); i++) {
			
			// we dont care about empty vectors
			if (this.row[i].length() == 0) {
				continue;
			}
			
			for (int j = 1; j <= this.getSize(); j++) {
				
				// we dont care about empty vectors
				if (transpose.row[j].length() == 0) {
					continue;
				} else {
					result.changeEntry(i, j, dot(this.row[i], transpose.row[j]));
				}
			}
		}

		return result;
	}

	public String toString() {
		StringBuilder buffer = new StringBuilder();
		for (int i = 1; i <= this.getSize(); i++) {
			if (this.row[i].length() == 0) {
				continue;
			} else {
				buffer.append(i + ": " + this.row[i] + "\n");
			}
		}
		return buffer.toString();
	}

	/**
	 * Dot product of '<'P'>' and '<'Q'>'
	 * @param P row vector
	 * @param Q column vector
	 * @return P*Q
	 */
	private static double dot(List P, List Q) {
		
		double result = 0;
		
		for (P.moveFront(), Q.moveFront(); P.index >= 0 && Q.index >= 0;) {
			
			// if index doest match advance
			if(((Entry) P.get()).index > ((Entry) Q.get()).index){
				Q.moveNext();
			}else if(((Entry) P.get()).index < ((Entry) Q.get()).index){
				P.moveNext();
			}else{
				result += ((Entry) P.get()).value * ((Entry) Q.get()).value;
				P.moveNext();
				Q.moveNext();
			}
		}
		return result;
	}

}
