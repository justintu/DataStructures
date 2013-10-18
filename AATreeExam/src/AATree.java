import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;
	
public class AATree <T extends Comparable<? super T>> {
	private BinaryNode root;

	public AATree(){
		root = null;
	}
	

	/** This method creates and returns an iterator. 
	 *  When calling next() repeatedly, the elements will be 
	 *  returned in reverse order, i.e. from largest to smallest.
	 *  
	 *  The constructor may run in linear time.
	 *  The hasNext() and next() methods run in constant time. 
	 * 
	 * @return Parameterized Iterator.
	 */
	
	public Iterator<T> reverseIterator(){
		return new ReverseIterator(this.toArrayList());
	}
	
	/** This method verifies whether the given tree follows the 
	 * specification of an AA Tree. Recall that a horizontal link 
	 * is a link between nodes of equal level.
	 * 
	 *  A BST is an AA Tree, if the following conditions hold:
	 *  1) There are only horizontal right links.
	 *  2) There may not be two consecutive horizontal links.
	 *  3) Nodes at level 2 or higher must have two children.
	 *  4) If a node does not have a right horizontal link, 
	 *     its two children are at the same level.
	 *  
	 *  An empty tree is trivially an AA Tree.
	 * 
	 * @return This method returns true if the tree is an AA Tree and
	 * false otherwise
	 */
	
	public boolean isAATree(){
		if(this.root == null)
			return true;
		return this.root.isAA();
	}
	
	
	public void setRoot(BinaryNode n){
		root = n;
	}
	
	public BinaryNode getRoot(){
		return root;
	}
	
	public String toString(){
		if (root != null) return root.toStringTree();
		return "";
	}

	public ArrayList<Object> toArrayList(){
		ArrayList<Object> elements = new ArrayList<Object>();
		if (root != null) root.toArrayList(elements);
		return elements;
	}
	
	private class ReverseIterator implements Iterator<T>{
		private Stack<T> s;

		public ReverseIterator(ArrayList<Object> nodes){
			s = new Stack<T>();
			for(Object o : nodes){
				s.add((T)o);
			}
		}
		
		public boolean hasNext() {
			return !s.isEmpty();
		}

		public T next() {
			if(!this.hasNext())
				throw new NoSuchElementException();
			return s.pop();
		}

		public void remove() {
			// TODO Auto-generated method stub	
		}
	}
	

	public class BinaryNode{
		private T element;
		private BinaryNode leftChild;
		private BinaryNode rightChild;
		private int level;
		
		public BinaryNode(T element){
			this.element = element;
			this.leftChild = null;
			this.rightChild = null;	
			this.level = 1;
		}
		
		public boolean isAA(){
			if(this.leftChild != null && this.level == this.leftChild.level){
				return false;
			}else if(this.rightChild != null && this.rightChild.rightChild != null){
				if(this.level == this.rightChild.level && this.level == this.rightChild.rightChild.level){
					return false;
				}
			}else if(this.level > 1){
				if(this.leftChild == null || this.rightChild == null)
					return false;
			}else{
				if(this.leftChild != null && this.rightChild != null)
					if(this.rightChild.level != this.leftChild.level)
						return false;
			}
			boolean rc = true;
			boolean lc = true;
			if(this.rightChild != null)
				rc = this.rightChild.isAA();
			if(this.leftChild != null)
				lc = this.leftChild.isAA();
			
			return rc && lc;
			
		}
		
		public void setLevel(int level){
			this.level = level;
		}
		
		public int getLevel(){
			return level;
		}
		
		public T getElement(){
			return element;
		}
		
		public void setLeftChild(BinaryNode n){
			leftChild = n;
		}
		
		public void setRightChild(BinaryNode n){
			rightChild = n;
		}
			
		public String toString(){
			return element.toString();
		}
		
		public String toStringTree(){
			String s = "[";
			String l = "";
			String r = "";
			s += element + " ";
			if (leftChild != null){
				s += leftChild.element + " ";
				l = leftChild.toStringTree();
			} else {
				s += "[]";
			}
			if (rightChild != null){
				s += rightChild.element + " ";
				r = rightChild.toStringTree();
			} else {
				s += "[]";
			}
			return s + "]" + this.level + "\n" + l + r;
		}
		
		public void toArrayList(ArrayList<Object> elements){
			if (leftChild != null) leftChild.toArrayList(elements);
			elements.add(this.element);
			if (rightChild != null) rightChild.toArrayList(elements);	
		}
	}
}
