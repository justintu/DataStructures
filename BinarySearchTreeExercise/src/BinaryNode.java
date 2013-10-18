/*import java.util.ArrayList;

public class BinaryNode<T>{
	
	private BinaryNode leftChild = null;
	private BinaryNode rightChild = null;
	private T element;
	
	public BinaryNode(T elem){
		this.element = elem;
	}
	
	public BinaryNode(T elem, BinaryNode leftChild, BinaryNode rightChild){
		this.element = elem;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
	}
	
	public T getElement(){
		return this.element;
	}
	
	public void setRightChild(BinaryNode n){
		this.rightChild = n;
	}
	
	public void setLeftChild(BinaryNode n){
		this.leftChild = n;
	}
	
	public BinaryNode getRightChild(){
		return this.rightChild;
	}
	
	public BinaryNode getLeftChild(){
		return this.leftChild;
	}
	
	public String toString(){
		return this.element.toString();
	}
	
	public ArrayList toArrayList(ArrayList list){
		list.add(this.element);
		if(this.getLeftChild() != null)
			this.getLeftChild().toArrayList(list);
		if(this.getRightChild() != null)
			this.getRightChild().toArrayList(list);
		return list;
	}
	
}
*/