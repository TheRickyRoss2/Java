// Ric Rodriguez
// CMPS 101-01 pa1
// rirrodri@ucsc.edu
// List.java

//  ************************************************************************************************************************
// 	List ADT Specifications
//	Your list module for this project will be a bi-directional queue that includes a “cursor” to be used for iteration.
//	Think of the cursor as highlighting or underscoring a distinguished element in the list. Note that it is a valid state
//	for this ADT to have no distinguished element, i.e. the cursor may be undefined or “off”, which is in fact its
//	default state. Thus the set of “mathematical structures” for this ADT consists of all finite sequences of integers
//	in which at most one element is underscored. A list has two ends referred to as “front” and “back” respectively.
//	The cursor will be used by the client to traverse the list in either direction. Each list element is associated with
//	an index ranging from 0 (front) to n-1 (back), where n is the length of the list. Your list module will define the
//	following operations
//  ************************************************************************************************************************

public class List {

	private class Node {

		int data;
		Node prev;
		Node next;
		
		// Node constructor
		Node(int data, Node next, Node prev) {
			this.data = data;
			this.next = next;
			this.prev = prev;
		}

		@Override
		public String toString() {
			return this.data + " ";
		}

	}

	Node front;
	Node back;
	Node cursor;
	int index = -1;

	// List Constructor
	// List() creates an empty list
	public List() {
		front = null;
		back = null;
		cursor = null;
		index = -1;
	}

	/**
	 * Calculates the number of elements in a given list
	 * @return number of elements
	 */
	int length() {
		if (this.front == null)
			return 0;
		int len = 0;
		Node tmp = this.front;
		while (tmp != null) {
			len++;
			tmp = tmp.next;
		}
		return len;

	}

	/**
	 * Find index of cursor's current position.
	 * @return index of cursor or -1 if cursor is undefined
	 */
	int index() {
		return index;
	}

	// Returns front element
	// Pre: list must not be empty
	int front() {
		if (this.length() <= 0)
			throw new RuntimeException("List.java: front(): Empty List");
		return front.data;

	}

	// Returns back element
	// Pre: list must not empty
	int back() {
		if (this.length() <= 0)
			throw new RuntimeException("List.java: back(): Empty List");
		return back.data;
	}

	// Returns cursor element
	// Pre: cursor must be defined and list not empty
	int get() {
		if (this.index() < 0) throw new RuntimeException("List.java: get(): Index undefined");
		if (this.length() <= 0) throw new RuntimeException("List.java: get(): Empty List");
		return this.cursor.data;
	}

	/**
	 * Returns true if this List and list L have the same integer sequence
	 * @param L List to compare
	 * @return true if list and L are equal
	 */
	boolean equals(List L) {

		if (this.length() != L.length())
			return false;

		this.cursor = this.front;
		L.cursor = L.front;

		for (int i = 0; i < this.length(); i++) {

			if ((int)this.cursor.data !=(int) L.cursor.data) {
				return false;
			}
			this.cursor = this.cursor.next;
			L.cursor = L.cursor.next;
		}
		return true;
	}

	// Resets list to original empty state
	void clear() {
		this.front = null;
		this.back = null;
		this.cursor = null;
		this.index = -1;
	}

	// If cursor is defined and not at front, moves cursor one step toward
	// front of this List, if cursor is defined and at front, cursor becomes
	// undefined, if cursor is undefined does nothing.
	void movePrev() {
		if(this.cursor == null) return;
		if(this.cursor!=null && this.index()>0){
			this.cursor = this.cursor.prev;
			this.index--;
			return;
		}
		if(this.cursor!=null && this.index()==0){
			this.cursor = null;
			this.index = -1;
			return;
		}
	}

	// If cursor is defined and not at back, moves cursor one step toward
	// back of this List, if cursor is defined and at back, cursor becomes
	// undefined, if cursor is undefined does nothing.
	void moveNext() {
		if(this.cursor == null) return;
		if(this.cursor!=null && this.index()!=this.length()-1){
			this.cursor = this.cursor.next;
			this.index++;
			return;
		}
		if(this.cursor!=null && this.index()==this.length()-1){
			this.cursor = null;
			this.index=-1;
			return;
		}
	}

	// If List is non-empty, places the cursor under the front element,
	// otherwise does nothing.
	void moveFront(){
		if(this.length()<=0) return;
		this.cursor = this.front;
		this.index = 0;
	}
	
	// If List is non-empty, places the cursor under the back element,
	// otherwise does nothing.
	void moveBack(){
		if(this.length()<=0) return;
		this.cursor = this.back;
		this.index = this.length()-1;
	}
	
	// Insert new element into this List. If List is non-empty,
	// insertion takes place before front element.
	void prepend(int data) {
		Node tmp = new Node(data, this.front, null);
		if (this.front == null) {
			this.back = tmp;
		} else {
			this.front.prev = tmp;
		}
		this.front = tmp;
	}

	// Insert new element into this List. If List is non-empty,
	// insertion takes place after back element.
	void append(int data) {
		Node tmp = new Node(data, null, this.back);
		if (this.front == null) {
			this.front = tmp;
		} else {
			this.back.next = tmp;
		}
		this.back = tmp;
	}

	// Insert new element before cursor.
	// Pre: length()>0, index()>=0
	void insertBefore(int data) {
		if (this.length() <= 0 || this.index() < 0) {
			return;
		}
		Node insertCursor = new Node(data, this.cursor, this.cursor.prev);
		if(this.cursor.prev!=null){
			this.cursor.prev.next = insertCursor;
		}else{
			this.front = insertCursor;
		}
		this.cursor.prev = insertCursor;
	}

	// Insert new element after cursor.
	// Pre: length()>0, index()>=0
	void insertAfter(int data) {
		if (this.length() < 1 || this.index() < 0) {
			return;
		}
		Node insertCursor = new Node(data, this.cursor.next, this.cursor);
		if(this.cursor.next != null){
			this.cursor.next.prev = insertCursor;
		}else{
			this.back =insertCursor;
		}
		
		this.cursor.next = insertCursor;
	}

	// Deletes the front element. 
	// Pre: length()>0
	void deleteFront() {
		if (this.length() <= 0)
			return;
		if (this.cursor == this.front) {
			this.cursor = null;
			this.index = -1;
		}
		this.front = this.front.next;
	}

	// Deletes the back element. 
	// Pre: length()>0
	void deleteBack() {
		if (this.length() <= 0)
			return;
		if (this.cursor == this.back) {
			this.cursor = null;
			this.index = -1;
		}
		this.back = this.back.prev;
	}
	
	// Deletes cursor element, making cursor undefined.
	// Pre: length()>0, index()>=0
	void delete() {
		if (this.length() <= 0 || this.index() < 0)
			return;
		this.cursor.prev.next = this.cursor.next;
		this.cursor.next.prev = this.cursor.prev;
		this.cursor = null;
		this.index = -1;
	}

	// Overrides Object's toString method. Returns a String
	// representation of this List consisting of a space
	// separated sequence of integers, with front on left.
	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		this.cursor = this.front;
		while (this.cursor != null) {
			buffer.append(this.cursor.toString());
			this.cursor = this.cursor.next;
		}
		return buffer.toString();
	}
	
	// Returns a new List representing the same integer sequence as this
	// List. The cursor in the new list is undefined, regardless of the
	// state of the cursor in this List. This List is unchanged.
	List copy() {
		List newList = new List();
		Node curr = this.front;
		while (curr != null) {
			newList.append(curr.data);
			curr = curr.next;
		}
		return newList;
	}

	// List concat(List L){}

}
