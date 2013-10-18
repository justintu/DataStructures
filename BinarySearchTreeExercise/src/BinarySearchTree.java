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

public class BinarySearchTree<T extends Comparable<? super T>> implements Iterable<T>{

    private BinaryNode rootNode;
    private int version = 0;
    private int size = 0;
    
    /**
     * Creates a new BinarySearchTree with a <code>null</code> root.
     */
    public BinarySearchTree(){
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
     * Determines the height of the BinarySearchTree.
     * @return the height of the tree (as an <code>int</code>).
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
    
    /**
     * Helper method to <code>height()</code>
     */
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
     * @return a generic <code>Iterator</code> over this BinarySearchTree that iterates over in an in-order traversal fashion.
     */
    public Iterator<T> iterator(){
        return new InOrderTreeIterator(this);
    }

    /**
     * Returns a <code>TreeIterator</code> that performs a pre-order traversal on this tree.
     * @return a generic <code>Iterator</code> over this BinarySearchTree that iterates over in a pre-order traversal fashion.
     */
    public Iterator<T> preOrderIterator(){
        return new PreOrderTreeIterator(this);
    }
    
    /**
     * Returns a primitve array of the elements in the current tree.
     * @return an array of <code>Object</code>s of the elements in the current tree.
     */
    public Object[] toArray(){
        return this.toArrayList().toArray();
    }
    
    /**
     * Returns an <code>ArrayList</code> of the elements in the current tree.
     * @return an <code>ArrayList<T></code> of the elements in the current tree.
     */
    public ArrayList<T> toArrayList(){
        ArrayList<T> temp = new ArrayList<T>();
        if(this.rootNode == null)
            return temp;
        return this.rootNode.toArrayList(temp);
    }

    
    //BinaryNode class
    /**
     * The BinaryNode class holds the elements in the BinarySearchTree.
     * Each BinaryNode has a left and right BinaryNode child. 
     */
    private class BinaryNode{
        
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
        
        /**
         * Sets either the left or right child of this BinaryNode with the value <code>e</code>.
         * If <code>e</code> is less that this node's value, it is sent to the left.
         * If <code>e</code> is greater than this node's value, it is sent to the right.
         * If <code>e</code> is equal, it is not added.
         * @param e the value to be set as the left or right child.
         */
        public boolean insert(T e){
            if(this.compareTo(e) < 0){
                if(this.leftChild != null)
                    return this.leftChild.insert(e);
                else{
                    this.leftChild = new BinaryNode(e);
                    return true;
                }
            }else if(this.compareTo(e) > 0){
                if(this.rightChild != null)
                    return this.rightChild.insert(e);
                else{
                    this.rightChild = new BinaryNode(e);
                    return true;
                }
            }
            //if same, just return false.
            return false;
        }
        
        /**
         * Compares the value of this node and a given value.
         * @param e the value to be compared to.
         */
        private int compareTo(T e){
            if(this.element.compareTo(e) < 0){
                return 1;
            }else if(this.element.compareTo(e) > 0){
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
        
        /**
         * Returns an ArrayList with this node and all it's children in it.
         * @return an ArrayList containing this node and it's children.
         */
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
    /**
     * A BinarySearchTree Iterator that performs its calculations in a pre-order fashion.
     */
    private class PreOrderTreeIterator implements Iterator<T>{
        
        private Stack<BinaryNode> container;
        private BinaryNode currentNode;
        private BinarySearchTree<T> tree;
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
        public PreOrderTreeIterator(BinarySearchTree<T> t){
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

    
    //InOrder Traversal Iterator
    /**
     * A BinarySearchTree Iterator that performs its calculations in a in-order fashion.
     */
    private class InOrderTreeIterator implements Iterator<T>{

        private Stack<T> container;
        private BinaryNode currentNode;
        private BinarySearchTree<T> tree;
        private Iterator<T> i;
        private int version = 0;
        
        /**
         * Creates a new InOrderTreeIterator with an empty Stack.
         */
        public InOrderTreeIterator(){
            this.container = new Stack<T>();
            
        }
        
        /**
         * Creates a new InOrderIterator on a BinarySearchTree.
         * @param n a BinarySearchTree to perform calculations on.
         */
        public InOrderTreeIterator(BinarySearchTree<T> n){
            this.tree = n;
            this.container = new Stack<T>();
            this.currentNode = this.tree.rootNode;
            if(this.currentNode != null){
                this.inOrderTraversal();
                this.version = this.tree.version;
            }
            this.i = this.container.iterator();
        }
        
        /**
         * Determines if the tree has a next element, in in-order fashion.
         * @return true or false if it has or does not have another element.
         */
        public boolean hasNext(){
            return this.i.hasNext();
        }

        /**
         * Gets the next element in the tree, in in-order fashion.
         * @return a generic value of the next BinaryNode in the iteration.
         */
        public T next(){
            if(!hasNext()){
                    throw new NoSuchElementException();
            }
            if(this.version != tree.version)
                throw new ConcurrentModificationException();
            return this.i.next();
        }
        
        public void inOrderTraversal(){
            this.inOrderTraversal(this.currentNode);
        }

        private void inOrderTraversal(BinaryNode n){
            if(n == null){
                //pass
            }else{
                this.inOrderTraversal(n.leftChild);
                this.container.add(n.element);
                this.inOrderTraversal(n.rightChild);
            }
        }
        
        public void remove(){
            //not implemented
        }

        /**
         * Represents this Iterator as a String.
         * @return a String representation of the Iterator.
         */
        public String toString(){
            return this.container.toString();
        }
    }
    
    
}