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

public class AATree<T extends Comparable<? super T>> implements Iterable<T>{

    private BinaryNode rootNode;
    private int version = 0;
    private int size = 0;
    private int rotationCount = 0;
    
    /**
     * Creates a new BinarySearchTree with a <code>null</code> root.
     */
    public AATree(){
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
     * Inserts an element <code>T</code> into the BinarySearchTree, keeping the tree intact.
     * @param e a generic element to be inserted.
     * @return <code>true</code> for a successful insertion, <code>false</code> for a failure.
     * @throws <code>IllegalArgumentException</code>
     */
    public boolean insert(T e){
        if(e == null)
            throw new IllegalArgumentException();
        else{
            if(this.rootNode == null){
                this.rootNode = new BinaryNode(e);
                this.size++;
                this.version++;
                return true;
            }else{
                if(this.rootNode.insert(e)){
                    this.size++;
                    this.version++;
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Removes a given element from the tree.
     * @param e The element that is to be removed.
     * @return true if the element was found and removed, false if otherwise.
     */
    public boolean remove(T e){
    	BooleanWrapper mod = new BooleanWrapper();
    	if(e == null)
    		throw new IllegalArgumentException();
    	if(this.isEmpty()){
    		return mod.value;
    	}else{
    		this.rootNode = this.rootNode.remove(e, mod);
    		this.version++;
    		this.size--;
    		//special case
    		if(this.size() == 2){
            	this.rootNode.level = 1;
            	this.rootNode.skew();
            	this.rootNode.level = 1;
            }
    	}
    	return mod.value;
    }
    
    /**
     * Gives a <code>String</code> representation of the BinarySearchTree.
     * @return A <code>String</code> representation of the the tree in the fashion of an in-order traversal.
     */
    public String toString(){
        if(this.isEmpty())
            return "";
        String s = "";
        Iterator i = this.iterator();
        while(i.hasNext()){
            s += "[" + i.next() + "], ";
        }
        s = s.trim();
        s = s.substring(0, s.length()-1);
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
     * @return a generic <code>Iterator</code> over this BinarySearchTree that iterates over in an pre-order traversal fashion.
     */
    public Iterator<T> iterator(){
        return new PreOrderTreeIterator(this);
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
        private int level;
        
        /**
         * Creates a new BinaryNode with its vakue being <code>elem</code>.
         * @param elem the generic data that will be set as the BinaryNode's value.
         */
        public BinaryNode(T elem){
            this.element = elem;
            this.level = 1;
        }
        
        /**
         * Sets either the left or right child of this BinaryNode with the value <code>e</code>.
         * If <code>e</code> is less that this node's value, it is sent to the left.
         * If <code>e</code> is greater than this node's value, it is sent to the right.
         * If <code>e</code> is equal, it is not added.
         * @param e the value to be set as the left or right child.
         */
        public boolean insert(T e){
            if(this.compareTo(e) < 0){
                if(this.leftChild != null){
                	boolean temp = this.leftChild.insert(e);
                	this.skew();
                    this.split();
                    return temp;
                }else{
                    this.leftChild = new BinaryNode(e);
                    return true;
                }
            }else if(this.compareTo(e) > 0){
                if(this.rightChild != null){
                	boolean temp = this.rightChild.insert(e);
                	this.skew();
                    this.split();
                    return temp;
                }else{
                    this.rightChild = new BinaryNode(e);
                    this.skew();
                    this.split();
                    return true;
                }
            }
            //if same, just return false.
            return false;
        }
        
        /**
         * Removed a given element from the tree.
         * @param e The element you wish to remove.
         * @param mod A BooleanWrapper for keeping track of removal.
         * @return
         */
        public BinaryNode remove(T e, BooleanWrapper mod){
        	
        	if(e.compareTo(this.element) > 0){
        		//Check if it's in right tree.
        		if(this.rightChild != null)
        			this.rightChild = this.rightChild.remove(e, mod);
        	}else if(e.compareTo(this.element) < 0){
        		//Check if it's in left tree.
        		if(this.leftChild != null)
        			this.leftChild = this.leftChild.remove(e ,mod);
        	}else{
        		//Must be somewhere here. (If in it at all.)
        		mod.value = true;
        		if(this.hasNoChildren()){
        			return null;
        		}else if(this.hasOneChild()){
        			if(this.leftChild != null){
        				return this.leftChild;
        			}else{
        				return this.rightChild;
        			}
        		}else{
        			//Must have two children.
        			T max = this.findMax(this.leftChild).element;
        			this.remove(max, new BooleanWrapper());
        			this.element = max;
        		}
        		
        	}
        	
        	return this;
        }
        
        private BinaryNode findMax(BinaryNode n){
        	if(n != null){
        		while(n.rightChild != null)
        			n = n.rightChild;
        	}
        	return n;
        }
        
        private boolean hasNoChildren(){
        	return (this.leftChild == null && this.rightChild == null); 
        }
        
        private boolean hasOneChild(){
        	return ((this.leftChild == null && this.rightChild != null) || (this.leftChild != null && this.rightChild == null));
        }
        
        private int compareTo(T e){
            if(this.element.compareTo(e) < 0){
                return 1;
            }else if(this.element.compareTo(e) > 0){
                return -1;
            }
            //equal
            return 0;
        }
        
        public T getElement(){
        	return this.element;
        }
        
        public int getLevel(){
        	return this.level;
        }
        
        private void skew(){
        	if(this.leftChild != null && this.leftChild.level == this.level){
        		int oldLevel = this.level;
        		this.rotateRight();
                this.level++;
                this.rightChild.level = oldLevel;
            }
        }
        
        private void split(){
        	if(this.rightChild != null && this.rightChild.rightChild != null && this.rightChild.rightChild.level == this.level){
        		int oldLevel = this.level;
        		this.rotateLeft();
                this.level++;
                this.leftChild.level = oldLevel;
            }
        }
        
        private void rotateLeft(){
        	rotationCount++;
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
        	rotationCount++;
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
         * Represents this BinaryNode as a String.
         * @return the value of the BinaryNode.
         */
        public String toString(){
            String s = "";
            if (leftChild != null) {
                s += leftChild.toString();
            }
            s += element + " ";
            if (rightChild != null) {
                s += rightChild.toString();
            }   
            return s;
        }
        
        public ArrayList<Object> toArrayList() {
            ArrayList<Object> temp = new ArrayList<Object>();
            temp.add(this);
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
    private class PreOrderTreeIterator implements Iterator<T>{
        
        private Stack<BinaryNode> container;
        private BinaryNode currentNode;
        private AATree<T> tree;
        private int version = 0;
        
        /**
         * Creates a new PreOrderTreeIterator with an empty Stack.
         */
        public PreOrderTreeIterator(){
            this.container = new Stack<BinaryNode>();
        }
        
        /**
         * Creates a new PreOrderIterator on a BinarySearchTree.
         * @param t a BinarySearchTree to perform calculations on.
         */
        public PreOrderTreeIterator(AATree<T> t){
            this.container = new Stack<BinaryNode>();
            this.tree = t;
            if(this.tree != null && this.tree.rootNode != null){
                this.container.add(this.tree.rootNode);
                this.version = this.tree.version;
            }
            this.currentNode = this.tree.rootNode;
        }
        
        /**
         * Represents this Iterator as a String.
         * @return a String representation of the Iterator.
         */
        public String toString(){
            return this.container.toString();
        }
        
        /**
         * Determines if the tree has a next element, in pre-order fashion.
         * @return true or false if it has or does not have another element.
         */
        public boolean hasNext(){
            return !this.container.isEmpty();
        }
        
        /**
         * Gets the next element in the tree, in pre-order fashion.
         * @return a generic value of the next BinaryNode in the iteration.
         */
        public T next(){
            if(!this.hasNext())
                throw new NoSuchElementException();
            if(this.version != this.tree.version)
                throw new ConcurrentModificationException();
            this.currentNode = this.container.pop();
            if(this.currentNode.rightChild != null)
                this.container.add(this.currentNode.rightChild);
            if(this.currentNode.leftChild != null)
                this.container.add(this.currentNode.leftChild);
            return this.currentNode.element;
        }
        
        public void remove(){
            //not implemented
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
}