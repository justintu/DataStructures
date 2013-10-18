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

public class RedBlackTree<T extends Comparable<? super T>> implements Iterable<RedBlackTree.BinaryNode>{

    private BinaryNode rootNode;
    private int version;
    private int size;
    private int rotationCount;
    
    
    /**
     * Create a new RedBlackTree
     */
    public RedBlackTree(){
        this.rootNode = null;
        this.rotationCount = 0;
        this.version = 0;
        this.size = 0;
    }
    
    /**
     * Checks to see if this tree is empty
     * @return false if empty, true if otherwise
     */
    public boolean isEmpty(){
        return this.rootNode == null;
    }
    
    /**
     * Gives the amount of rotations this tree made after x amount of inserts.
     * @return an int representation of rotation count.
     */
    public int getRotationCount(){
    	return this.rotationCount;
    }
    
    /**
     * Retrieves an element from the tree, without removing it.
     * @param e the element to be searched for
     * @return the element with value e
     * @throws IllegalArgumentException
     */
    public T get(T e){
    	if(e == null)
            throw new IllegalArgumentException();
    	return this.rootNode.get(e);
    }
    
    /**
     * Inserts an element into this tree.
     * @param e the element to be inserted
     * @return true if inserted successfully, false if otherwise.
     */
    public boolean insert(T e){
        return this.insert(e, this.rootNode);
    }
    
    private boolean insert(T e, BinaryNode n){
    	if(e == null)
            throw new IllegalArgumentException();
        else{
            if(this.rootNode == null){
                this.rootNode = new BinaryNode(e);
                this.size++;
                this.version++;
                if(this.rootNode.color == Color.RED)
                	this.rootNode.colorFlip();
                return true;
            }else{
                if(this.rootNode.insert(e)){
                    this.size++;
                    this.version++;
                    if(this.rootNode.color == Color.RED)
                    	this.rootNode.colorFlip();
                    return true;
                }
            }
        }
    	if(this.rootNode.color == Color.RED)
        	this.rootNode.colorFlip();
        return false;
    }
    
    /**
     * Determines the height of this tree.
     * @return an int representation of the height.
     */
    public int height(){
        int height = 0;
        if(this.isEmpty()){
            height = -1;
        }else{
            height = this.climbTree(this.rootNode, 0);
        }
        return height;
    }
    
    private int climbTree(BinaryNode n, int reach){
        int leftHeight = reach;
        int rightHeight = reach;
        if(n.leftChild == null && n.rightChild == null){
            //pass
        }else{
            if(n.leftChild != null){
                leftHeight = this.climbTree(n.leftChild, reach + 1);
            }
            if(n.rightChild != null){
                rightHeight = this.climbTree(n.rightChild, reach + 1);
            }
        }
        return Math.max(leftHeight, rightHeight);
    }
    
    /**
     * Displays this tree in String format
     * @return a String representation of this tree.
     */
    public String toString(){
        if(this.isEmpty())
            return "";
        String s = "";
        Iterator<RedBlackTree.BinaryNode> i = this.iterator();
        while(i.hasNext()){
        	BinaryNode t = i.next();
            s += "[" + t.element + ", " + t.color + ", " + t.parent + ", " + t.leftChild + ", " + t.rightChild + "]\n";
        }
        return s;
    }
    
    /**
     * Gets the size of this tree.
     * @return an int representation of the size of the tree.
     */
    public int size(){
        return this.size;
    }
    
    /**
     * @return returns an iterator over this tree. The iterator gets elements in preorder fashion.
     */
    public Iterator<RedBlackTree.BinaryNode> iterator(){
    	return new PreOrderTreeIterator(this.rootNode);
    }

    //BinaryNode class
    public class BinaryNode{
        
        private BinaryNode leftChild = null;
        private BinaryNode rightChild = null;
        private BinaryNode parent = null;
        private Color color = Color.RED;
        private T element;
        /**
         * Creates a new BinaryNode
         * @param elem the element stored in the biarynode
         */
        public BinaryNode(T elem){
            this.element = elem;
        }
        
        /**
         * @return the color of this node. (Either red or black).
         */
        public Color getColor(){
        	return this.color;
        }
        
        /**
         * 
         * @return the element in this binarynode
         */
        public T getElement(){
        	return this.element;
        }
        
        /**
         * 
         * @return an arraylist representation of the children of this node.
         */
    	public ArrayList<BinaryNode> getChildren(){
    		ArrayList<BinaryNode> temp = new ArrayList<BinaryNode>();
    		if (leftChild != null) temp.add(leftChild);
    		if (rightChild != null) temp.add(rightChild);
    		return temp;
    	}
        
    	/**
    	 * 
    	 * @param e the element you want to get
    	 * @return the element
    	 */
        public T get(T e){
        	if(e.compareTo(this.element) > 0)
        		return this.rightChild.get(e);
        	else if(e.compareTo(this.element) < 0)
        		return this.leftChild.get(e);
        	else{
        		return this.element;
        	}
        }
        
        /**
         * Inserts the element into the proper place of this binarynode or it's children.
         * @param e the element to be inserted
         * @return true if inserted, false if otherwise.
         */
        public boolean insert(T e){
        	if(this.shouldColorSwap()){
        		this.swapColors();
        		this.balanced();
        	}
        	Boolean isInserted = false;
            if(this.compareTo(e) < 0){
                if(this.leftChild != null){
                    isInserted = this.leftChild.insert(e);
                }else{
                    this.leftChild = new BinaryNode(e);
                    this.leftChild.parent = this;
                    isInserted = true;
                	this.balanced();
                }
            }else if(this.compareTo(e) > 0){
                if(this.rightChild != null){
                    isInserted = this.rightChild.insert(e);
                }else{
                    this.rightChild = new BinaryNode(e);
                    this.rightChild.parent = this;
                    isInserted = true;
                    this.balanced();
                }
            }
            return isInserted;
        }
        
        public void colorFlip(){
        	if(this.color == Color.RED)
        		this.color = Color.BLACK;
        	else
        		this.color = Color.RED;
        }
        
        private void swapColors(){
        	this.colorFlip();
        	this.leftChild.colorFlip();
        	this.rightChild.colorFlip();
        	if(this.parent != null)
        		this.parent.balanced();
        }
        
        private boolean shouldColorSwap(){
        	if(this.leftChild != null && this.rightChild != null)
        		if(this.leftChild.color == Color.RED && this.rightChild.color == Color.RED)
        			return true;
        	return false;
        }
        
        private void balanced(){
        	if(this.color == Color.RED){
        		if(this.isLeftChild()){
	        		if(this.leftChild != null && this.leftChild.color == Color.RED){
	        			this.parent.rotateRight();
	        			this.colorFlip();
	        		}else if(this.rightChild != null && this.rightChild.color == Color.RED){
	        			this.parent.doubleRightRotation();
	        		//	this.doubleLeftRotation();
	        			this.colorFlip();
	        		//	this.rightChild.colorFlip();
	        		}
        		}else{
        			if(this.rightChild != null && this.rightChild.color == Color.RED){
        				this.parent.rotateLeft();
	        			this.colorFlip();
        			}else if(this.leftChild != null && this.leftChild.color == Color.RED){
	        			this.parent.doubleLeftRotation();
        			//	this.doubleRightRotation();
	        			this.colorFlip();
	        		//	this.rightChild.colorFlip();
        			}
        		}
        	}
        		
 
        }
        	
        private boolean isLeftChild(){
        	if(this.parent == null)
        		return false;
        	return this.parent.leftChild == this;
        }
        
        private boolean hasNoChildren(){
        	return (this.leftChild == null && this.rightChild == null); 
        }
        
        private boolean hasOneChild(){
        	return ((this.leftChild == null && this.rightChild != null) || (this.leftChild != null && this.rightChild == null));
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
        	//update parent pointers
        	if(this.leftChild != null)
        		this.leftChild.parent = this;
        	this.rightChild.parent = this;
        	if(this.rightChild.rightChild != null)
        		this.rightChild.rightChild.parent = this.rightChild;
        	if(this.rightChild.leftChild != null)
        		this.rightChild.leftChild.parent = this.rightChild;
        	
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
        	//update parent pointers
        	if(this.rightChild != null)
        		this.rightChild.parent = this;
        	this.leftChild.parent = this;
        	if(this.leftChild.leftChild != null)
        		this.leftChild.leftChild.parent = this.leftChild;
        	if(this.leftChild.rightChild != null)
        		this.leftChild.rightChild.parent = this.leftChild;
        }

        private void doubleLeftRotation(){
        	this.rightChild.rotateRight();
            this.rotateLeft();
        }

        private void doubleRightRotation(){
            this.leftChild.rotateLeft();
            this.rotateRight();
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

        public String toString(){
        	return String.valueOf(this.element);
        }
        
        public ArrayList<T> toArrayList(ArrayList<T> list){
            list.add(this.element);
            if(this.leftChild != null)
                this.leftChild.toArrayList(list);
            if(this.rightChild != null)
                this.rightChild.toArrayList(list);
            return list;
        }
    }
    
    //PreOrder Traversal Iterator
    public class PreOrderTreeIterator implements Iterator<RedBlackTree.BinaryNode>{
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

    public enum Color{
    	RED, BLACK
    }
    
}