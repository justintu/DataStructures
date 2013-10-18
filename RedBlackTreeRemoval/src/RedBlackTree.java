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
    private BooleanWrapper removed;
    
    
    /**
     * Create a new RedBlackTree
     */
    public RedBlackTree(){
        this.rootNode = null;
        this.rotationCount = 0;
        this.version = 0;
        this.size = 0;
        this.removed = new BooleanWrapper();
    }
    
    /**
     * Checks to see if this tree is empty
     * @return false if empty, true if otherwise
     */
    public boolean isEmpty(){
        return this.rootNode == null;
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
    
    public boolean remove(T e){
    	this.removed.value = false;
    	if(e == null)
    		throw new IllegalArgumentException();
    	else{
    		if(this.rootNode == null){
    			//pass
	    	}else{
	    		if(this.rootNode.hasNoChildren()){
	    			if(this.rootNode.compareTo(e) == 0){
	    				this.rootNode = null;
	    				this.removed.value = true;
	    			}
	    		}else if(this.rootNode.hasTwoBlackChildren() || this.rootNode.hasOneBlackChild()){
	    			this.rootNode.color = Color.RED;
	    			int cmp = this.rootNode.compareTo(e);
	    			if(cmp == 0){
	    				this.rootNode.step3(e);
	    			}else if(cmp > 0){
	    				if(this.rootNode.rightChild == null)
	    					return false;
	    				this.rootNode.rightChild.step2(e);
	    			}else if(cmp < 0){
	    				if(this.rootNode.leftChild == null)
	    					return false;
	    				this.rootNode.leftChild.step2(e);
	    			}
	    		}else{
	    			this.rootNode.step2B(e);
	    		}
	    	}
    	}
    	if(this.rootNode != null)
    		this.rootNode.color = Color.BLACK;
    	return this.removed.value;
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
     * Gives the amount of rotations this tree made after x amount of inserts.
     * @return an int representation of rotation count.
     */
    public int getRotationCount(){
    		return this.rotationCount;
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
         * Inserts the element into the proper place of this binary node or it's children.
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
        
        private void swapWithNoBalance(){
        /*	this.colorFlip();
        	this.leftChild.colorFlip();
        	this.rightChild.colorFlip();
        */
        	this.color = Color.BLACK;
        	if(this.leftChild != null)
        		this.leftChild.color = Color.RED;
        	if(this.rightChild != null)
        		this.rightChild.color = Color.RED;
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
	        			this.colorFlip();
	        		}
        		}else{
        			if(this.rightChild != null && this.rightChild.color == Color.RED){
        				this.parent.rotateLeft();
	        			this.colorFlip();
        			}else if(this.leftChild != null && this.leftChild.color == Color.RED){
	        			this.parent.doubleLeftRotation();
	        			this.colorFlip();
        			}
        		}
        	}
        		
 
        }
        	
        private boolean isLeftChild(){
        	if(this.parent == null)
        		return false;
        	return this.parent.leftChild == this;
        }
        
        //steps in removal
        
        private void step2(T e){
        //	System.out.println("Step 2. X= " + this);
        	if(this.hasTwoBlackChildren() || this.hasNoChildren() || this.hasOneBlackChild())
        		this.step2A(e);
        	else if(this.hasAtLeastOneRedChild())
        		this.step2B(e);
        }
        
        private void step2A(T e){
        //	System.out.println("Step 2A. X= " + this);
        	BinaryNode sibling;
        	if(this.isLeftChild()){
        		sibling = this.parent.rightChild;
        		//case 2A1
            	if(sibling.hasTwoBlackChildren() || sibling.hasNoChildren() || sibling.hasOneBlackChild())
            		this.step2A1(e);
            	//case2A2
            	else if((sibling.leftChild != null && sibling.leftChild.color == Color.RED) && (sibling.rightChild == null || sibling.rightChild.color != Color.RED))
    	        	this.step2A2(e);
            	//case 2A3
            	else if((sibling.rightChild != null && sibling.rightChild.color == Color.RED) && (sibling.leftChild == null || sibling.leftChild.color != Color.RED))
    	        	this.step2A3(e);
            	//case 2A4
            	else if(sibling.leftChild.color == Color.RED && sibling.rightChild.color == Color.RED)
    	        	this.step2A3(e);
        	}else{
        		sibling = this.parent.leftChild;
        		//case 2A1
            	if(sibling.hasTwoBlackChildren() || sibling.hasNoChildren() || sibling.hasOneBlackChild())
            		this.step2A1(e);
            	//case2A2
            	else if((sibling.rightChild != null && sibling.rightChild.color == Color.RED) && (sibling.leftChild == null || sibling.leftChild.color != Color.RED))
    	        	this.step2A2(e);
            	//case 2A3
            	else if((sibling.leftChild != null && sibling.leftChild.color == Color.RED) && (sibling.rightChild == null || sibling.rightChild.color != Color.RED))
    	        	this.step2A3(e);
            	//case 2A4
            	else if(sibling.leftChild.color == Color.RED && sibling.rightChild.color == Color.RED)
    	        	this.step2A3(e);
        	}
        	
        	
        }
        
        private void step2A1(T e){
        //	System.out.println("Step 2A1. X= " + this);
        	this.parent.swapWithNoBalance();
        	int compare = this.element.compareTo(e);
        	if(compare == 0){
        		this.step3(e);
        	}else if(compare > 0){
        		if(this.leftChild != null)
        			this.leftChild.step2(e);
        	}else{
        		if(this.rightChild != null)
        			this.rightChild.step2(e);
        	}
        }
        
        private void step2A2(T e){
        //	System.out.println("Step 2A2. X= " + this);
        	if(this.isLeftChild()){
        		this.parent.doubleLeftRotation();
        	/*	this.color = Color.RED;
        		this.parent.color = Color.BLACK;
        		this.parent.parent.rightChild.color = Color.BLACK;
        	*/
        		this.colorFlip();
        		this.parent.colorFlip();
        		this.parent.parent.rightChild.colorFlip();
        	}else{
        		this.parent.doubleRightRotation();
        	/*	this.color = Color.RED;
        		this.parent.color = Color.BLACK;
        		this.parent.parent.leftChild.color = Color.BLACK;
        	*/
        		this.colorFlip();
        		this.parent.colorFlip();
        		this.parent.parent.leftChild.colorFlip();
        	}
        	int compare = this.element.compareTo(e);
        	if(compare == 0){
        		this.step3(e);
        	}else if(compare > 0){
        		if(this.leftChild != null)
        			this.leftChild.step2(e);
        	}else{
        		if(this.rightChild != null)
        			this.rightChild.step2(e);
        	}
        }
        
        private void step2A3(T e){
        //	System.out.println("Step 2A3. X= " + this);
        	if(this.isLeftChild()){
        		this.parent.rotateLeft();
        		this.color = Color.RED;
        		this.parent.color = Color.BLACK;
        		this.parent.parent.color = Color.RED;
        		this.parent.parent.rightChild.color = Color.BLACK;
        	}else{
        		this.parent.rotateRight();
        		this.color = Color.RED;
        		this.parent.color = Color.BLACK;
        		this.parent.parent.color = Color.RED;
        		this.parent.parent.leftChild.color = Color.BLACK;
        	}
        	int compare = this.element.compareTo(e);
        	if(compare == 0){
        		this.step3(e);
        	}else if(compare > 0){
        		if(this.leftChild != null)
        			this.leftChild.step2(e);
        	}else{
        		if(this.rightChild != null)
        			this.rightChild.step2(e);
        	}
        }
        
        private void step2B(T e){
        //	System.out.println("Step 2B. X= " + this);
        	int compare = this.element.compareTo(e);
        	if(compare == 0){
        		this.step3(e);
        	}else if(compare > 0){
        		if(this.leftChild != null)
        			if(this.leftChild.color == Color.RED)
        				this.leftChild.step2B1(e);
        			else{
        				this.leftChild.step2B2(e);
        			}
        	}else{
        		if(this.rightChild != null)
        			if(this.rightChild.color == Color.RED)
        				this.rightChild.step2B1(e);
        			else
        				this.rightChild.step2B2(e);
        	}
        }
        
        private void step2B1(T e){
        //	System.out.println("Step 2B1. X= " + this);
        	int compare = this.element.compareTo(e);
        	if(compare == 0){
        		this.step3(e);
        	}else if(compare > 0){
        		if(this.leftChild != null)
        			this.leftChild.step2(e);
        	}else{
        		if(this.rightChild != null)
        			this.rightChild.step2(e);
        	}
        }
        
        private void step2B2(T e){
        //	System.out.println("Step 2B2. X= " + this);
        	if(this.isLeftChild()){
        		this.parent.rotateLeft();
        	}else{
        		this.parent.rotateRight();
        	}
        	this.step2(e);
        }
        
        private void step3(T e){
        //	System.out.println("Step 3. X= " + this);
        	boolean isLeft = this.isLeftChild();
        	//step 3A
        	if(this.hasTwoChildren()){
        		BinaryNode leftMax = this.findMax();
        	//	System.out.println("Step 3A. Left Max= " + leftMax.element);
        		T old = this.element;
        		
        		if(this.hasTwoBlackChildren())
        			this.leftChild.step2(leftMax.element);
        		else
        			this.step2(leftMax.element);
        		
        		if(this.element == old)
        			this.element = leftMax.element;
        		else if(this.leftChild.element == old)
        			this.leftChild.element = leftMax.element;
        		else if(this.rightChild.element == old)
        			this.rightChild.element = leftMax.element;
        		
        		removed.value = true;
        	//step 3B
        	}else if(this.hasNoChildren()){
        	//	System.out.println("Step 3B.");
        		if(isLeft){
        			this.parent.leftChild = null;
        			this.parent = null;
        		}else{
        			this.parent.rightChild = null;
        			this.parent = null;
        		}
        		removed.value = true;
        	//Step 3C
        	}else if(this.hasOneChild()){
        	//	System.out.println("Step 3C.");
        		//left Child
        		if(this.leftChild != null){
        			this.element = this.leftChild.element;
        			this.color = Color.BLACK;
        			this.leftChild = this.leftChild.leftChild;
        			if(this.leftChild != null)
        				this.leftChild.parent = this;
        		}
        		//right Child
        		if(this.rightChild != null){
        			this.element = this.rightChild.element;
        			this.color = Color.BLACK;
        			this.rightChild = this.rightChild.rightChild;
        			if(this.rightChild != null)
        				this.rightChild.parent = this;
        		}
        		removed.value = true;
        	}
        }
        
        private boolean hasNoChildren(){
        	return this.leftChild == null && this.rightChild == null;
        }
        
        private boolean hasOneChild(){
        	return this.leftChild != null || this.rightChild != null;
        }
        
        private boolean hasTwoChildren(){
        	return this.leftChild != null && this.rightChild != null;
        }
        
        private boolean hasTwoBlackChildren(){
        	if(this.hasTwoChildren()){
        		return this.leftChild.color == Color.BLACK && this.rightChild.color == Color.BLACK;
        	}
        	return false;
        }
        
        private boolean hasOneBlackChild(){
        	return (this.hasOneChild() && ((this.leftChild != null && this.leftChild.color == Color.BLACK && this.rightChild == null) || (this.rightChild != null && this.rightChild.color == Color.BLACK && this.leftChild == null)));
        }
        
        private boolean hasAtLeastOneRedChild(){
        	return ((this.leftChild != null && this.leftChild.color == Color.RED) || (this.rightChild != null && this.rightChild.color == Color.RED)) || (this.leftChild != null && this.leftChild.color == Color.RED && this.rightChild != null && this.rightChild.color == Color.RED);
        }
        
        private void rotateRight(){
        //	System.out.println("Right Rotation");
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
        //	System.out.println("Left Rotation");
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
        //	System.out.println("Double Left");
        	this.rightChild.rotateRight();
            this.rotateLeft();
        }

        private void doubleRightRotation(){
        //	System.out.println("Double Right");
            this.leftChild.rotateLeft();
            this.rotateRight();
        }
        
        private BinaryNode findMax(){
        	BinaryNode RC = this.leftChild;
            while(RC.rightChild != null){
                RC = RC.rightChild;
            }
            return RC;
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