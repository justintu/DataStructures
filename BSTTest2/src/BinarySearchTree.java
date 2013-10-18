import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * This class implements a Binary Search Tree. 
 *
 * @author wollowsk.
 *         Created January, 11 2010.
 */

public class BinarySearchTree <T extends Comparable<? super T>>{

	private BinaryNode root;
	private int size;
	
	public BinarySearchTree() {
		root = null;	
		size = 0;
	}


	/** 
	 * This method returns the nthLargest element in the BST. It throws 
	 * an IllegalArgumentException if n is smaller than 1 or larger than 
	 * the number of elements in the structure. This method runs in 
	 * linear time.
	 * 
	 * @return This method returns the nth largest element.
	 */
	public T nthLargest(int n){
		if(n > this.size || n < 1)
			throw new IllegalArgumentException();
		if(this.size == 1)
			return this.root.element;
		//return root.findN(n, 0);
		Object[] a = this.toArray();
		return (T)a[this.size - n];
	}
	
	/** 
	 * This method returns a LevelOrderIterator.
	 * The constructor, as well as the hasNext() and next() methods  
	 * operate in constant time. The remove() method is not implemented.
	 * 
	 * @return This method returns a level-order iterator.
	 */
	public Iterator<T> iterator(){
		return new LevelOrderIterator(this.root);
	}

	/** 
	 * This method returns a HashTable containing the elements of this tree.
	 * Building the Hashtable takes linear time.
	 * This method assumes that the objects in the tree are of type String
	 * and throws a ClassCastException otherwise. 
	 * The order in which the elements appear in the individual buckets does
	 * not matter.
	 * 
	 * @return This method returns a HashTable with the objects of this tree 
	 * properly hashed into the table.
	 */	
	public HashTable toHashTable(){
/*		if(this.root.element.getClass().equals(java.lang.String))
			throw new ClassCastException();
			Not sure how to call the above correctly, but there it is in pseudocode
*/

		HashTable electricBoogaloo = new HashTable();
		Iterator i = this.iterator();
		while(i.hasNext()){
			electricBoogaloo.insert((String)i.next());
		}
		return electricBoogaloo;
	}
	
	public boolean insert(T element) {
		if (element == null) {
			throw new NullPointerException("Cannot insert null element into binary search tree");
		} else if (root == null) {
			root = new BinaryNode(element);
			size++;
			return true;
		} else {
			return root.insert(element);
		}
	}

	public String toString() {
		if (root == null) return "";
		return root.toString();
	}

	public Object[] toArray(){
		Object[] a = new Object[size];
		if (root != null) root.toArray(a, 0);
		return a;
	}

	
private class BinaryNode {
	    private T element;  
	    private BinaryNode leftChild;
	    private BinaryNode rightChild;

	    public BinaryNode(T e) {  
			element = e;
			leftChild = rightChild = null;
	    }
	    
	    private boolean insert(T element2) {
	 		if (element.compareTo(element2) > 0) {
				if (leftChild == null) {
					leftChild = new BinaryNode(element2);
					size++;
					return true;
				} else {
					return leftChild.insert(element2);
				}
			} else if (element.compareTo(element2) < 0) {
				if (rightChild == null) {
					rightChild = new BinaryNode(element2);
					size++;
					return true;
				} else {
					return rightChild.insert(element2);
				}
			} else {
				return false;
			}
		}
    
	    public String toString() {
	    	String s = "[" + element + " " +
	    	 			((leftChild == null)? null : leftChild.element)+ " " +
	    	 			((rightChild == null)? null : rightChild.element)+	
	    	 			"]\n";
	    	if (leftChild != null) {
	    		s += leftChild.toString();
	    	}
	       	if (rightChild != null) {
	       		s += rightChild.toString();
	    	}	
	       	return s;
	    }
	    
	    public int toArray(Object[] a, int index) {
	    	if (leftChild != null) {
	    		index = leftChild.toArray(a, index);
	    	}
	    	a[index++] = element;
	       	if (rightChild != null) {
	       		index = rightChild.toArray(a, index);
	    	}	
	       	return index;
	    }
	    
	}

	private class LevelOrderIterator implements Iterator<T>{
		
		private ArrayList<T> q;
		
		public LevelOrderIterator(BinaryNode n){
			q = new ArrayList<T>();
			q.add(n.element);
			this.preProcess(n);
		}
		
		private void preProcess(BinaryNode n){
			if(n.leftChild != null)
				q.add(n.leftChild.element);
			if(n.rightChild != null)
				q.add(n.rightChild.element);
			if(n.leftChild != null)
				this.preProcess(n.leftChild);
			if(n.rightChild != null)
				this.preProcess(n.rightChild);
			
		}

		public boolean hasNext() {
			return !q.isEmpty();
		}

		public T next() {
			return q.remove(0);
		}

		public void remove() {
			// TODO Auto-generated method stub
		}
	}


}

