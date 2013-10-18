/*import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

public class PreOrderTreeIterator implements Iterator{
	
	private Stack<BinaryNode> container;
	private BinaryNode currentNode;
	
	public PreOrderTreeIterator(){
		this.container = new Stack();
	}
	
	public PreOrderTreeIterator(BinaryNode n){
		this.container = new Stack();
		this.currentNode = n;
		if(n != null)
			this.preProcess(n);
	}
	
	public void preProcess(BinaryNode n){
		if(n.getLeftChild() != null){
			this.preProcess(n.getLeftChild());
		}
		this.container.add(n);
		
	}
	
	public String toString(){
		return this.container.toString();
	}
	
	public boolean hasNext(){
		return !this.container.empty() && this.currentNode.getLeftChild() == null && this.currentNode.getRightChild() == null;
	}
	
	public Object next(){
		if(!this.hasNext()){
			throw new NoSuchElementException();
		}
		BinaryNode rVal = this.container.pop();
		if(this.container.size() == 1 && rVal.getRightChild() != null){
			BinaryNode temp = this.container.pop();
			this.container.add(rVal.getRightChild());
			this.container.add(temp);
		}
		
		return rVal.getElement();
				
	}
	
	public void remove(){
		//not implemented
	}
	
}
*/