import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;



/**
 * 
 * Java implementation of a Binary Search Tree.
 * 
 * @author Nick Crawford
 * @version 1.0
 *
 */
//public class SplayTree<T> implements Comparable<T>, Iterable<T>{
public class SplayTree<T extends Comparable<? super T>> implements Iterable<SplayTree.BinaryNode>{

    private BinaryNode rootNode;
    private int version = 0;
    private int size = 0;
    
    /**
     * Creates a new BinarySearchTree with a <code>null</code> root.
     */
    public SplayTree(){
        this.rootNode = null;
    }
    
    /**
     * Determines if the BinarySearchTree is empty or not.
     * @return <code>true</code> (empty) or <code>false</code> (not empty).
     */
    public boolean isEmpty(){
        return this.rootNode == null;
    }
    
    /**
     * Inserts the given element into the tree, as the root. Splays accordingly.
     * @param e The element to be inserted.
     * @return true if inserted, false if otherwise.
     */
    public boolean insert(T e){
    	BinaryNode x;
    	if(this.rootNode == null){
    		this.rootNode = new BinaryNode(e);
    	    return true;
    	}
    	this.splay(e);
    	int cmp = this.rootNode.compareTo(e);
    	if(cmp == 0){
    		if(e instanceof Insertable){
    			return ((Insertable)this.rootNode.element).insert(e);
    		}else
    			return false;
    	}
    	x = new BinaryNode(e);
    	if(cmp > 0){
    	    x.leftChild = this.rootNode.leftChild;
    	    x.rightChild = this.rootNode;
    	    this.rootNode.leftChild = null;
    	}else{
    	    x.rightChild = this.rootNode.rightChild;
    	    x.leftChild = this.rootNode;
    	    this.rootNode.rightChild = null;
    	}
    	this.rootNode = x;
    	return true;
    }
    
    /**
     * Removes the given element if present in the tree. Splays accordingly.
     * @param e The element to be removed.
     * @return true if removed, false if otherwise.
     */
    public boolean remove(T e){
    	BinaryNode x;
    	if(this.rootNode == null)
    		return false;
    	this.splay(e);
    	if(this.rootNode.compareTo(e) != 0){
    	    return false;
    	}
    	if(this.rootNode.leftChild == null){
    		this.rootNode = this.rootNode.rightChild;
    		return true;
    	}else{
    	    x = this.rootNode.rightChild;
    	    this.rootNode = this.rootNode.leftChild;
    	    this.splay(e);
    	    this.rootNode.rightChild = x;
    	    return true;
    	}
    }

    /**
     * Finds if a given element is in the tree. If it is, it is splayed to be the root. If it isn't, the closest is splayed to the root.
     * @param e the element to be found.
     * @return the element of the BinaryNode to be found.
     */
    public T find(T e){
		if(rootNode == null)
			return null;
		this.splay(e);
	    if(rootNode.element.compareTo(e) != 0)
	    	return null;
	    return rootNode.element;
    }
    
    private void splay(T e){
    	BinaryNode temp = new BinaryNode();
    	BinaryNode leftTempTree;
    	BinaryNode rightTempTree;
    	BinaryNode x;
    	leftTempTree = temp;
    	rightTempTree = temp;
    	x = rootNode;
    	temp.leftChild = null;
    	temp.rightChild = null;
    	SplayTree.BinaryNode[] a = {x, rightTempTree, leftTempTree, temp};
    	a = this.rootNode.splay(a, e);
    	x = a[0];
    	rightTempTree = a[1];
    	leftTempTree = a[2];
    	temp = a[3];
    	rightTempTree.leftChild = x.rightChild;
    	leftTempTree.rightChild = x.leftChild;
    	x.leftChild = temp.rightChild;
    	x.rightChild = temp.leftChild;
    	this.rootNode = x;
    }
    
    /**
     * Gives a <code>String</code> representation of the BinarySearchTree.
     * @return A <code>String</code> representation of the the tree in the fashion of an in-order traversal.
     */
    public String toString(){
        if(this.isEmpty())
            return "";
        String s = "";
        Iterator<SplayTree.BinaryNode> i = this.iterator();
        while(i.hasNext()){
        	BinaryNode t = i.next();
            s += "[" + t.element + ", " + t.leftChild + ", " + t.rightChild + "]\n";
        }
        return s;
    }
    
    /**
     * Gives the size (number of elements) in the tree.
     * @return the number of elements in the tree (as an <code>int</code>).
     */
    public int size(){
        return this.size;
    }
    
    /**
     * Returns a <code>TreeIterator</code> that performs an in-order traversal on this tree.
     * @return a generic <code>Iterator</code> over this BinarySearchTree that iterates over in an in-order traversal fashion.
     */
    public Iterator<SplayTree.BinaryNode> iterator(){
    	return new PreOrderTreeIterator(this.rootNode);
    }
    
    /**
     * Returns a primitive array of the elements in the current tree.
     * @return an array of <code>Object</code>s of the elements in the current tree.
     */
    public Object[] toArray(){
        return this.toArrayList().toArray();
    }
    
    /**
     * Returns an <code>ArrayList</code> of the elements in the current tree.
     * @return an <code>ArrayList<T></code> of the elements in the current tree.
     */
    public ArrayList<Object> toArrayList(){
        ArrayList<Object> temp = new ArrayList<Object>();
        if(this.rootNode == null)
            return temp;
        return this.rootNode.toArrayList();
    }

    
    //BinaryNode class
    /**
     * The BinaryNode class holds the elements in the BinarySearchTree.
     * Each BinaryNode has a left and right BinaryNode child. 
     */
    public class BinaryNode{
        
        private BinaryNode leftChild = null;
        private BinaryNode rightChild = null;
        private T element;
        
        /**
         * Creates a new BinaryNode with its value being <code>null</code>.
         */
        public BinaryNode(){
            this.element = null;
        }
        
        /**
         * Creates a new BinaryNode with its vakue being <code>elem</code>.
         * @param elem the generic data that will be set as the BinaryNode's value.
         */
        public BinaryNode(T elem){
            this.element = elem;
        }
        
        public ArrayList<BinaryNode> getChildren(){
    		ArrayList<BinaryNode> temp = new ArrayList<BinaryNode>();
    		if (leftChild != null) temp.add(leftChild);
    		if (rightChild != null) temp.add(rightChild);
    		return temp;
    	}
        
        /**
         * Createes a new BinaryNode with a value and a left and/or/nor right child.
         * @param elem the generic data that will be set as the BinaryNode's value.
         * @param leftChild a BinaryNode that will be set at the left child of this BinaryNode.
         * @param rightChild a BinaryNode that will be set as the right child of this BinaryNode.
         */
        public BinaryNode(T elem, BinaryNode leftChild, BinaryNode rightChild){
            this.element = elem;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
        }
        
        private SplayTree.BinaryNode[] splay(SplayTree.BinaryNode[] a, T e){
        	BinaryNode x = a[0];
        	BinaryNode rightTempTree = a[1];
        	BinaryNode leftTempTree = a[2];
        	BinaryNode temp = a[3];
        	while(true){
        		if(x.compareTo(e) == 0){
        			SplayTree.BinaryNode[] t = {x, rightTempTree, leftTempTree, temp};
        			return t;
        		}else if(x.compareTo(e) > 0){
        	    	if(x.leftChild == null){
        	    		SplayTree.BinaryNode[] t = {x, rightTempTree, leftTempTree, temp};
            			return t;
        	    	}else if (x.leftChild.compareTo(e) > 0){
        	    		x.rotateRight();
        	    		if(x.leftChild == null){
        	    			SplayTree.BinaryNode[] t = {x, rightTempTree, leftTempTree, temp};
                			return t;
        	    		}
        	    	}
        	    	rightTempTree.leftChild = x;
        	    	rightTempTree = x;
        			x = x.leftChild;
        			SplayTree.BinaryNode[] t = {x, rightTempTree, leftTempTree, temp};
        			this.splay(t,e);
        	    }else if(x.compareTo(e) < 0){
        	    	if (x.rightChild == null){
        	    		SplayTree.BinaryNode[] t = {x, rightTempTree, leftTempTree, temp};
            			return t;
        	    	}else if(x.rightChild.compareTo(e) < 0){
        	    		x.rotateLeft();
        	    		if(x.rightChild == null){
        	    			SplayTree.BinaryNode[] t = {x, rightTempTree, leftTempTree, temp};
                			return t;
        	    		}
        	    	}
        	    	leftTempTree.rightChild = x;
        	    	leftTempTree = x;
        	    	x = x.rightChild;
        	    	SplayTree.BinaryNode[] t = {x, rightTempTree, leftTempTree, temp};
        	    }else{
        	    	SplayTree.BinaryNode[] t = {x, rightTempTree, leftTempTree, temp};
        			return t;
        	    }
        	}
        }
        
        private void rotateLeft(){
        	T rootElem = this.element;
        	BinaryNode pivot = this.rightChild;
        	BinaryNode pivotGC = pivot.rightChild;
        	BinaryNode tempLC = this.leftChild;
        	BinaryNode tempLGC = pivot.leftChild;
        	this.element = pivot.element;
        	this.rightChild = pivotGC;
        	this.leftChild = new BinaryNode(rootElem);
        	this.leftChild.leftChild = tempLC;
        	this.leftChild.rightChild = tempLGC;
        }
        
        private void rotateRight(){
        	T rootElem = this.element;
        	BinaryNode pivot = this.leftChild;
        	BinaryNode pivotGC = pivot.leftChild;
        	BinaryNode tempLC = this.rightChild;
        	BinaryNode tempRGC = pivot.rightChild;
        	this.element = pivot.element;
        	this.leftChild = pivotGC;
        	this.rightChild = new BinaryNode(rootElem);
        	this.rightChild.rightChild = tempLC;
        	this.rightChild.leftChild = tempRGC;
        }
        
        /**
         * Compares the value of this node and a given value.
         * @param e the value to be compared to.
         */
        private int compareTo(T e){
            if(this.element.compareTo(e) > 0){
                return 1;
            }else if(this.element.compareTo(e) < 0){
                return -1;
            }
            //equal
            return 0;
        }
        /**
         * Represents this BinaryNode as a String.
         * @return the value of the BinaryNode.
         */
        public String toString(){
        	return String.valueOf(this.element);
        }
        
        /**
         * Returns an ArrayList with this node and all it's children in it.
         * @return an ArrayList containing this node and it's children.
         */
        public ArrayList<Object> toArrayList() {
            ArrayList<Object> temp = new ArrayList<Object>();
            temp.add(this.element);
            if(this.leftChild != null){
                temp.addAll(this.leftChild.toArrayList());
            }
            if(this.rightChild != null){
                temp.addAll(this.rightChild.toArrayList());
            }
            return temp;
        }
    }
    
    //PreOrder Traversal Iterator
    /**
     * A BinarySearchTree Iterator that performs its calculations in a pre-order fashion.
     */
    public class PreOrderTreeIterator implements Iterator<SplayTree.BinaryNode>{
    	private ArrayList<BinaryNode> nodes;

    	public PreOrderTreeIterator(BinaryNode root){
    		nodes = new ArrayList<BinaryNode>();
    		if (root != null) {
    			nodes.add(root);
    		}
    	}
    	public boolean hasNext() {
    		return !nodes.isEmpty();
    	}
    	
    	public BinaryNode next() {
    		if (!hasNext()) throw new NoSuchElementException();
    		BinaryNode temp = nodes.remove(0);
    		nodes.addAll(0, temp.getChildren());
    		return temp;
    	}

    	public void remove() {
    		// TODO Auto-generated method stub
    	}

    }

    private class BooleanWrapper{
    	
    	boolean value;
    	
    	public BooleanWrapper(){
    		this.value = false;
    	}
    	
    	public BooleanWrapper(boolean bool){
    		this.value = bool;
    	}
    	
    }

	public int compareTo(T o) {
		// TODO Auto-generated method stub
		return 0;
	}
    
}